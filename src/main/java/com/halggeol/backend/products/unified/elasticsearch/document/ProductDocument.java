package com.halggeol.backend.products.unified.elasticsearch.document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.InnerField;
import org.springframework.data.elasticsearch.annotations.MultiField;

@Getter
@Setter
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
//@Setting(settingPath = "elasticsearch/settings/product-index-settings.json") // 애플리케이션 재시작 → 인덱스 생성됨
@Document(indexName = "products_index")
public class ProductDocument { // Elasticsearch의 인덱스

    @Id // Elasticsearch의 _id 필드와 매핑
    private String id;

    @JsonProperty("productid")
    @Field(name = "productid", type = FieldType.Keyword) // name 지정은 Elasticsearch 필드명과 맞추기 위함
    private String productId;

    @MultiField(
        mainField = @Field(type = FieldType.Text, analyzer = "korean"),
        otherFields = {
            @InnerField(suffix = "ngram", type = FieldType.Text, analyzer = "korean_ngram")
        }
    )
    private String name;

    @Field(type = FieldType.Keyword)
    private String company;

    @Field(type = FieldType.Keyword)
    private String tag1;

    @Field(type = FieldType.Keyword)
    private String tag2;

    @Field(type = FieldType.Keyword)
    private String tag3;

    @Field(type = FieldType.Keyword)
    private String type;

    @JsonProperty("fsector")
    @Field(name = "fsector", type = FieldType.Integer)
    private Integer fSector;

    @JsonProperty("saveterm")
    @Field(name = "saveterm", type = FieldType.Integer)
    private Integer saveTerm;

    @JsonProperty("min_save_term")
    @Field(name = "min_save_term", type = FieldType.Integer)
    private Integer minSaveTerm;

    @JsonProperty("max_save_term")
    @Field(name = "max_save_term", type = FieldType.Integer)
    private Integer maxSaveTerm;

    @JsonProperty("minamount")
    @Field(name = "minamount", type = FieldType.Integer)
    private Integer minAmount;

    @JsonProperty("view_cnt")
    @Field(name = "view_cnt", type = FieldType.Integer)
    private Integer viewCnt;

    @JsonProperty("scrap_cnt")
    @Field(name = "scrap_cnt", type = FieldType.Integer)
    private Integer scrapCnt;

    @Field(type = FieldType.Double)
    private Double title;

    @JsonProperty("subtitle")
    @Field(name = "subtitle", type = FieldType.Keyword)
    private String subTitle;

    @Field(type = FieldType.Date)
    private LocalDateTime timestamp;

}
