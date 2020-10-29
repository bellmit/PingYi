package com.example.upc.controller.param;

public class TrainCaMaterialPeriodParam extends TrainCaMaterialParam {
    private Float nowPeriod=0.00f;
    private Float allPeriod=0.00f;

    public Float getNowPeriod() {
        return nowPeriod;
    }

    public void setNowPeriod(Float nowPeriod) {
        this.nowPeriod = nowPeriod;
    }

    public Float getAllPeriod() {
        return allPeriod;
    }

    public void setAllPeriod(Float allPeriod) {
        this.allPeriod = allPeriod;
    }
}
