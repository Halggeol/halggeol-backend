package com.halggeol.backend.products.unified.elasticsearch.document;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "recent_searches_index")
public class RecentSearchDocument {
    private String keyword;
    private Instant timestamp;
    private Integer userId;
}
