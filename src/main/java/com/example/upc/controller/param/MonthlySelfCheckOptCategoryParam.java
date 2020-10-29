package com.example.upc.controller.param;

import com.example.upc.dataobject.MonthlySelfcheckOpt;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class MonthlySelfCheckOptCategoryParam {
    private Integer categoryId;

    private String categoryName;

    private Integer pageNumber;

    private List<MonthlySelfcheckOptParam> optList;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public List<MonthlySelfcheckOptParam> getOptList() {
        return optList;
    }

    public void setOptList(List<MonthlySelfcheckOptParam> optList) {
        this.optList = optList;
    }
}
