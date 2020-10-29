package com.example.upc.controller.param;

import java.util.Date;
import java.util.List;

public class CommitteCheckOptParam {
    private Integer categoryId;

    private String categoryName;

    private Integer pageNumber;

    private List<CommitteCheckOptTopicParam> optList;

    public List<CommitteCheckOptTopicParam> getOptList() {
        return optList;
    }

    public void setOptList(List<CommitteCheckOptTopicParam> optList) {
        this.optList = optList;
    }

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
}
