package com.devsjk.namecardserver.model;

import com.leshang.framework3.model.BaseModel;
import java.time.LocalDateTime;
import java.util.List;

public class ProductionCategory extends BaseModel {
    private Long id;

    private Long companyId;

    private String name;

    private Integer classifySort;

    private List<Production> productionList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Integer getClassifySort() {
        return classifySort;
    }

    public void setClassifySort(Integer classifySort) {
        this.classifySort = classifySort;
    }

    public List<Production> getProductionList() {
        return productionList;
    }

    public void setProductionList(List<Production> productionList) {
        this.productionList = productionList;
    }
}