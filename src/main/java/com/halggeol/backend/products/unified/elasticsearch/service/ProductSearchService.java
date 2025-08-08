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
import io.micrometer.core.instrument.search.Search;
import java.util.stream.IntStream;
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
            // BoolQuery 빌더 생성
            BoolQuery.Builder boolQueryBuilder = new BoolQuery.Builder();
            
            // 각 조건에 맞는 쿼리를 빌더에 추가
            addKeywordQuery(boolQueryBuilder, keyword);
            addTypeFilter(boolQueryBuilder, types);
            addFSectorFilter(boolQueryBuilder, fSectors);
            addMinAmountFilter(boolQueryBuilder, minAmount);
            addSaveTermFilter(boolQueryBuilder, saveTerm);
            
            // 최종 쿼리 및 정렬 옵션 생성
            Query finalQuery = Query.of(q->q.bool(boolQueryBuilder.build()));
            SortOptions sortOptions = getSortOptions(sort);
            
            // 검색 요청 생성 및 실행
            SearchRequest request = SearchRequest.of(s->s
                .index("products_index")
                .query(finalQuery)
                .sort(sortOptions)
                .size(MAX_RESULTS_LIMIT)
            );

            log.info("Executing search request with query");
            SearchResponse<ProductDocument> response = esClient.search(request, ProductDocument.class);
            log.info("Search response: {}", response);
            log.info("Search completed. Total hits: {}",
                response.hits().total() != null ? response.hits().total().value() : 0);

            // 결과 처리 및 반환
            List<ProductSearchResponseDTO> results = response.hits().hits().stream()
                .map(Hit::source)
                .filter(Objects::nonNull)
                .map(this::convertToDTO)
                .collect(Collectors.toList());

            log.info("Search results: {}", results);
            log.info("Returning {} results", results.size());
            return ResponseEntity.ok(results);

        } catch (Exception e) {
            log.error("Elasticsearch query failed - Status: {}, Message: {}",
                e.getClass().getSimpleName(), e.getMessage(), e);
            return ResponseEntity.status(500).body("검색 실패: " + e.getMessage());
        }
    }

    // 키워드 검색 조건
    private void addKeywordQuery(BoolQuery.Builder boolQueryBuilder, String keyword) {
        if (keyword != null && !keyword.isBlank()) {
            log.info("Adding keyword query: {}", keyword);
            boolQueryBuilder.must(Query.of(q->q
                .bool(b->b
                    .should(Query.of(s->s.match(m->m
                        .field("name")
                        .query(FieldValue.of(keyword))
                    )))
                    .minimumShouldMatch("1")
                )
            ));
        }
    }

    // 상품 타입 필터 조건
    private void addTypeFilter(BoolQuery.Builder boolQueryBuilder, List<String> types) {
        if(types != null && !types.isEmpty()){
            log.info("Adding type filter: {}", types);
            List<FieldValue> typeValues = types.stream()
                .map(FieldValue::of)
                .collect(Collectors.toList());

            boolQueryBuilder.filter(Query.of(q->q
                .terms(t->t
                    .field("type.keyword")
                    .terms(tv->tv.value(typeValues))
                )
            ));
        }
    }

    // 금융권 필터 조건
    private void addFSectorFilter(BoolQuery.Builder boolQueryBuilder, List<String> fSectors) {
        if(fSectors != null && !fSectors.isEmpty()){
            log.info("Adding fSector filter: {}", fSectors);
            List<FieldValue> fSectorValues = fSectors.stream()
                .map(v->FieldValue.of(Long.valueOf(v)))
                .collect(Collectors.toList());

            boolQueryBuilder.filter(Query.of(q->q
                .terms(t->t
                    .field("fSector")
                    .terms(tv->tv.value(fSectorValues))
                )
            ));
        }
    }

    // 최소 가입 금액 필터 조건
    private void addMinAmountFilter(BoolQuery.Builder boolQueryBuilder, Integer minAmount) {
        if (minAmount != null && m) {}
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