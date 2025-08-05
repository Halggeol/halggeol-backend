package com.halggeol.backend.products.unified.elasticsearch.document;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

@Getter
@Setter
@Data
@NoArgsConstructor
@Setting(settingPath = "elasticsearch/settings/product-index-settings.json") // 애플리케이션 재시작 → 인덱스 생성됨
@Document(indexName = "products_index")
public class ProductDocument { // Elasticsearch의 인덱스

    @Id // Elasticsearch의 _id 필드와 매핑
    private String id;

    @Field(name = "productid", type = FieldType.Keyword) // name 지정은 Elasticsearch 필드명과 맞추기 위함
    private String productId;

    @Field(type = FieldType.Text, analyzer = "korean") // name 필드에 korean analyzer 적용
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

    @Field(name = "fsector", type = FieldType.Integer)
    private Integer fSector;

    @Field(name = "saveterm", type = FieldType.Integer)
    private Integer saveTerm;

    @Field(name = "min_save_term", type = FieldType.Integer)
    private Integer minSaveTerm;

    @Field(name = "max_save_term", type = FieldType.Integer)
    private Integer maxSaveTerm;

    @Field(name = "minamount", type = FieldType.Integer)
    private Integer minAmount;

    @Field(name = "view_cnt", type = FieldType.Integer)
    private Integer viewCnt;

    @Field(name = "scrap_cnt", type = FieldType.Integer)
    private Integer scrapCnt;

    @Field(type = FieldType.Double)
    private Double title;

    @Field(name = "subtitle", type = FieldType.Keyword)
    private String subTitle;

    @Field(type = FieldType.Date)
    private LocalDateTime timestamp;

}
