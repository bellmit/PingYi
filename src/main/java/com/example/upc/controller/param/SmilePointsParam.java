package com.example.upc.controller.param;

import java.util.List;

public class SmilePointsParam {
    List<SmilePoints> smilePointsList;
    private Integer total;

    public List<SmilePoints> getSmilePointsList() {
        return smilePointsList;
    }

    public void setSmilePointsList(List<SmilePoints> smilePointsList) {
        this.smilePointsList = smilePointsList;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
