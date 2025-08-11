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
@Document(indexName = "recent_searches_index")
public class RecentSearchDocument {
    private String keyword;

    @Field(type = FieldType.Date)
    private Instant timestamp;

    private Integer userId;
}
