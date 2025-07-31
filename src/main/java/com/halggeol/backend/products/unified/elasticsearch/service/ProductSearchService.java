package com.halggeol.backend.products.unified.elasticsearch.service;

import com.halggeol.backend.products.unified.elasticsearch.document.ProductDocument;
import com.halggeol.backend.products.unified.elasticsearch.dto.ProductSearchResponseDTO;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.ScriptSortBuilder.ScriptSortType;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductSearchService {

    private final ElasticsearchOperations elasticsearchOperations;

    public List<ProductSearchResponseDTO> searchProducts(
        String sort,
        String keyword,
        Integer fSector,
        String type,
        String minAmount,
        Integer saveTerm
    ) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        // 검색어 (상품명 부분 매칭)
        if(keyword!=null && !keyword.isBlank()){
            boolQuery.must(QueryBuilders.matchQuery("name", keyword));
        }

        // 상품 유형 필터링
        if(type!=null && !type.isBlank()){
            boolQuery.filter(QueryBuilders.termQuery("type.keyword", type));
        }

        // 금융권 필터링
        if(fSector!=null){
            boolQuery.filter(QueryBuilders.termQuery("fsector", fSector));
        }

        // 가입 기간 필터링
        if(saveTerm!=null){
            boolQuery.filter(QueryBuilders.termQuery("saveterm", saveTerm));
        }

        // 최소 가입 금액 필터링
        if(minAmount!=null&&!minAmount.isBlank()){
            try{
                long minAmt = Long.parseLong(minAmount.trim());

                // Raw JSON으로 range 쿼리 작성
                String rangeQueryJson = String.format(
                    "{\"range\":{\"minamount\":{\"gte\":%d}}}", minAmt
                );

                boolQuery.filter(QueryBuilders.wrapperQuery(rangeQueryJson));
            } catch (NumberFormatException e){
                log.error("Invalid minAmount input: {}", minAmount, e);
            }
        }

        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder()
            .withQuery(boolQuery)
            .withMaxResults(10000); // 검색결과는 최대 10000건 불러옴

        // 복합 정렬 (viewCnt + scrapCnt × 2)
        Script script = new Script("doc['view_cnt'].value + doc['scrap_cnt'].value * 2");
        SortBuilder<?> sortBuilder = SortBuilders
            .scriptSort(script, ScriptSortType.NUMBER)
            .order(SortOrder.DESC);

        // 정렬 조건
        if ("rateDesc".equals(sort)) {
            queryBuilder.withSort(new FieldSortBuilder("title").order(SortOrder.DESC));
        }
        else if ("popularDesc".equals(sort)) {
            queryBuilder.withSort(sortBuilder);
        }
        else { // 기본 정렬은 인기순
            queryBuilder.withSort(new FieldSortBuilder("view_cnt").order(SortOrder.DESC));
        }

        NativeSearchQuery searchQuery = queryBuilder.build();

        SearchHits<ProductDocument> searchHits = elasticsearchOperations.search(searchQuery, ProductDocument.class);

        return searchHits.getSearchHits().stream()
            .map(SearchHit::getContent)
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    // doc->DTO 변환 메서드
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
            .minAmount(doc.getMinAmount())
            .viewCnt(doc.getViewCnt())
            .scrapCnt(doc.getScrapCnt())
            .build();
    }
}
