package cn.itsource.fenggou.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 品牌信息
 * </p>
 *
 * @author fanjunjian
 * @since 2019-05-16
 */
@TableName("t_brand")
public class Brand extends Model<Brand> {

private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("createTime")
    private Date createTime =new Date();

    @TableField("updateTime")
    private Date updateTime;

    /**
     * 品牌名
     */
    private String name;

    /**
     * 英文名
     */
    @TableField("englishName")
    private String englishName;

    /**
     * 首字母
     */
    @TableField("firstLetter")
    private String firstLetter;

    private String description;

    /**
     * 商品分类ID
     */
    private Long productTypeId;

    @TableField(exist = false) //标识数据库中没有这个字段
    private ProductType productType;


    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    @TableField("sortIndex")
    private Integer sortIndex;

    /**
     * 品牌LOGO
     */
    private String logo;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    public Date getCreateTime() {
        return createTime;
    }
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT-8")
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    public Date getUpdateTime() {
        return updateTime;
    }
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT-8")
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(Long productTypeId) {
        this.productTypeId = productTypeId;
    }

    public Integer getSortIndex() {
        return sortIndex;
    }

    public void setSortIndex(Integer sortIndex) {
        this.sortIndex = sortIndex;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Brand{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", name='" + name + '\'' +
                ", englishName='" + englishName + '\'' +
                ", firstLetter='" + firstLetter + '\'' +
                ", description='" + description + '\'' +
                ", productTypeId=" + productTypeId +
                ", productType=" + productType +
                ", sortIndex=" + sortIndex +
                ", logo='" + logo + '\'' +
                '}';
    }
}
