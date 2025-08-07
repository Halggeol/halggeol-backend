package com.halggeol.backend.products.unified.elasticsearch.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.InlineScript;
import co.elastic.clients.elasticsearch._types.Script;
import co.elastic.clients.elasticsearch._types.ScriptSortType;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import com.halggeol.backend.products.unified.elasticsearch.document.ProductDocument;
import com.halggeol.backend.products.unified.elasticsearch.dto.ProductSearchResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductSearchService {

    private final ElasticsearchClient esClient;

    private static final int MAX_RESULTS_LIMIT = 10000;

    public ResponseEntity<?> searchProducts(
        String sort,
        String keyword,
        List<Integer> fSectors,
        List<String> types,
        String minAmount,
        Integer saveTerm
    ) {
        try {
            // BoolQuery 빌더를 더 명시적으로 구성
            BoolQuery.Builder boolQueryBuilder = new BoolQuery.Builder();

            // 키워드 검색 - must 조건
            if (keyword != null && !keyword.isBlank()) {
                log.info("Adding keyword search for: {}", keyword);
                boolQueryBuilder.must(Query.of(q -> q
                    .bool(b -> b
                        .should(Query.of(s -> s.match(m -> m
                            .field("name")
                            .query(FieldValue.of(keyword))
                        )))
                        .should(Query.of(s -> s.match(m -> m
                            .field("name.ngram")
                            .query(FieldValue.of(keyword))
                        )))
                        .minimumShouldMatch("1")
                    )
                ));
            }

            // 타입 필터
            if (types != null && !types.isEmpty()) {
                log.info("Adding type filter: {}", types);
                List<FieldValue> typeValues = types.stream()
                    .map(FieldValue::of)
                    .collect(Collectors.toList());

                boolQueryBuilder.filter(Query.of(q -> q
                    .terms(t -> t
                        .field("type.keyword")
                        .terms(tv -> tv.value(typeValues))
                    )
                ));
            }

            // fSector 필터
            if (fSectors != null && !fSectors.isEmpty()) {
                log.info("Adding fSector filter: {}", fSectors);
                List<FieldValue> sectorValues = fSectors.stream()
                    .map(v -> FieldValue.of(Long.valueOf(v)))
                    .collect(Collectors.toList());

                boolQueryBuilder.filter(Query.of(q -> q
                    .terms(t -> t
                        .field("fsector")
                        .terms(tv -> tv.value(sectorValues))
                    )
                ));
            }

            // minAmount 필터
            if (minAmount != null && !minAmount.isBlank()) {
                try {
                    long min = Long.parseLong(minAmount.trim());
                    log.info("Adding minAmount filter: {}", min);
                    boolQueryBuilder.filter(Query.of(q -> q
                        .range(r -> r
                            .field("minamount")
                            .gte(JsonData.of(min))
                        )
                    ));
                } catch (NumberFormatException e) {
                    log.warn("Invalid minAmount: {}", minAmount);
                }
            }

            // saveTerm 필터
            if (saveTerm != null) {
                log.info("Adding saveTerm filter: {}", saveTerm);
                boolQueryBuilder.filter(Query.of(q -> q
                    .bool(b -> b
                        .should(Query.of(s -> s
                            .term(t -> t
                                .field("saveterm")
                                .value(FieldValue.of(saveTerm))
                            )
                        ))
                        .should(Query.of(s -> s
                            .bool(sub -> sub
                                .must(Query.of(q1 -> q1
                                    .range(r -> r
                                        .field("min_save_term")
                                        .lte(JsonData.of(saveTerm))
                                    )
                                ))
                                .must(Query.of(q2 -> q2
                                    .range(r -> r
                                        .field("max_save_term")
                                        .gte(JsonData.of(saveTerm))
                                    )
                                ))
                            )
                        ))
                        .minimumShouldMatch("1")
                    )
                ));
            }

            // 최종 쿼리 생성
            Query finalQuery = Query.of(q -> q.bool(boolQueryBuilder.build()));

            SortOptions sortOptions = getSortOptions(sort);

            SearchRequest request = SearchRequest.of(s -> s
                .index("products_index")
                .query(finalQuery)
                .sort(sortOptions)
                .size(MAX_RESULTS_LIMIT)
            );

            // 디버깅을 위한 로그
            log.info("Executing search request with query");

            SearchResponse<ProductDocument> response = esClient.search(request, ProductDocument.class);

            log.info("Search completed. Total hits: {}",
                response.hits().total() != null ? response.hits().total().value() : 0);

            List<ProductSearchResponseDTO> results = response.hits().hits().stream()
                .map(Hit::source)
                .filter(Objects::nonNull)
                .map(this::convertToDTO)
                .collect(Collectors.toList());

            log.info("Returning {} results", results.size());
            return ResponseEntity.ok(results);

        } catch (Exception e) {
            log.error("Elasticsearch query failed - Status: {}, Message: {}",
                e.getClass().getSimpleName(), e.getMessage(), e);
            return ResponseEntity.status(500).body("검색 실패: " + e.getMessage());
        }
    }

    private SortOptions getSortOptions(String sort) {
        if ("rateDesc".equals(sort)) {
            return SortOptions.of(s -> s
                .field(f -> f.field("title").order(SortOrder.Desc))
            );
        }

        // 기본 정렬: 조회수 + 스크랩수*2 기준
        InlineScript inlineScript = InlineScript.of(i -> i
            .lang("painless")
            .source("doc['view_cnt'].value + doc['scrap_cnt'].value * 2")
        );
        Script script = Script.of(s -> s.inline(inlineScript));

        return SortOptions.of(s -> s
            .script(ss -> ss
                .script(script)
                .type(ScriptSortType.Number)
                .order(SortOrder.Desc)
            )
        );
    }

    private ProductSearchResponseDTO convertToDTO(ProductDocument doc) {
        return ProductSearchResponseDTO.builder()
            .productId(doc.getProductId())
            .name(doc.getName())
            .company(doc.getCompany())
            .tag1(doc.getTag1())
            .tag2(doc.getTag2())
            .tag3(doc.getTag3())
            .title(doc.getTitle())
            .subTitle(doc.getSubTitle())
            .type(doc.getType())
            .fSector(doc.getFSector())
            .saveTerm(doc.getSaveTerm())
            .minSaveTerm(doc.getMinSaveTerm())
            .maxSaveTerm(doc.getMaxSaveTerm())
            .minAmount(doc.getMinAmount())
            .viewCnt(doc.getViewCnt())
            .scrapCnt(doc.getScrapCnt())
            .build();
    }
}