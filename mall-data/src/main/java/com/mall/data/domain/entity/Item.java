package com.mall.data.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "mall_item",shards = 3,replicas = 1)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Item {
    @Id
    private Long id;
    @Field(type= FieldType.Text,analyzer = "ik_max_word")
    private String title; //标题
    @Field(type= FieldType.Keyword)
    private String category;//分类
    @Field(type= FieldType.Keyword)
    private String brand;//
    @Field(type= FieldType.Double)
    private Double price;//价格

}
