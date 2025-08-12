package com.halggeol.backend.products.unified.elasticsearch.document;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "popular_searches_index")
public class PopularSearchDocument {
    private String keyword;
    private Integer count;

    @Field(type = FieldType.Date)
    private Instant lastSearchTime;
}
