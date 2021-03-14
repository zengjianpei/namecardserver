package com.devsjk.namecardserver.model;

import com.leshang.framework3.model.BaseModel;
import java.math.BigDecimal;

public class Production extends BaseModel {
    private Long id;

    private String name;

    private Long categoryId;

    private Long companyId;

    private Double price;

    private Integer onsale;

    private Integer seq;

    private String detailUrl;

    private String description;

    private String coverUrl;

    private String categoryName;

    private String coverIndexUrl;

    public Production(){

    }

    public Production(Long categoryId){
        this.categoryId=categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
        String[] c=coverUrl.split(",");
        if(c.length>0){
            this.coverIndexUrl=c[0];
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getOnsale() {
        return onsale;
    }

    public void setOnsale(Integer onsale) {
        this.onsale = onsale;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCoverIndexUrl() {
        return coverIndexUrl;
    }

    public void setCoverIndexUrl(String coverIndexUrl) {
        this.coverIndexUrl = coverIndexUrl;
    }
}