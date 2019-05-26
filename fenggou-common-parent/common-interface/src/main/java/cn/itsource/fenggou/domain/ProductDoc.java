package cn.itsource.fenggou.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @version: V1.0
 * @author: fan
 * @className: ProductDoc
 * @description: todo
 * @date: 2019/5/23
 **/
@Document(indexName = "fenggou",type = "productdoc")
public class ProductDoc {
    @Id
    @Field(type = FieldType.Long,store = true)
    private Long id;

    @Field(type = FieldType.Text,store = true)
    private String all;//关键字查询字段  标题  副标题  品牌名称  类型名称

    @Field(type = FieldType.Long,store = true)
    private Long productTypeId;

    @Field(type = FieldType.Long,store = true)
    private Long brandId;

    @Field(type = FieldType.Integer,store = true)
    private Integer minPrice;

    @Field(type = FieldType.Integer,store = true)
    private Integer maxPrice;

    @Field(type = FieldType.Integer,store = true)
    private Integer saleCount;//销量

    @Field(type = FieldType.Long,store = true)
    private Long onSaleTime;//上架时间

    @Field(type = FieldType.Integer,store = true)
    private Integer commontCount;//评论量

    @Field(type = FieldType.Integer,store = true)
    private Integer viewCount;//访问量

    @Field(type = FieldType.Text,store = true)
    private String name;//标题

    @Field(type = FieldType.Text,store = true)
    private String subName;//副标题

    @Field(type = FieldType.Text,store = true)
    private String viewProperties;//显示属性

    @Field(type = FieldType.Text,store = true)
    private String skuProperties;//sku属性

    @Field(type = FieldType.Text,store = true)
    private String medias;//媒体属性

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }

    public Long getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(Long productTypeId) {
        this.productTypeId = productTypeId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Integer getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Integer maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Integer getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(Integer saleCount) {
        this.saleCount = saleCount;
    }

    public Long getOnSaleTime() {
        return onSaleTime;
    }

    public void setOnSaleTime(Long onSaleTime) {
        this.onSaleTime = onSaleTime;
    }

    public Integer getCommontCount() {
        return commontCount;
    }

    public void setCommontCount(Integer commontCount) {
        this.commontCount = commontCount;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getViewProperties() {
        return viewProperties;
    }

    public void setViewProperties(String viewProperties) {
        this.viewProperties = viewProperties;
    }

    public String getSkuProperties() {
        return skuProperties;
    }

    public void setSkuProperties(String skuProperties) {
        this.skuProperties = skuProperties;
    }

    public String getMedias() {
        return medias;
    }

    public void setMedias(String medias) {
        this.medias = medias;
    }
}
