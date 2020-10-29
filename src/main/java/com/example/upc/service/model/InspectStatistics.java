package com.example.upc.service.model;

/**
 * @author zcc
 * @date 2019/10/27 21:20
 */
public class InspectStatistics {
    private InspectCheck foodCommon;
    private InspectCheck foodCirculate;
    private InspectCheck foodProduce;

    public InspectCheck getFoodCommon() {
        return foodCommon;
    }

    public void setFoodCommon(InspectCheck foodCommon) {
        this.foodCommon = foodCommon;
    }

    public InspectCheck getFoodCirculate() {
        return foodCirculate;
    }

    public void setFoodCirculate(InspectCheck foodCirculate) {
        this.foodCirculate = foodCirculate;
    }

    public InspectCheck getFoodProduce() {
        return foodProduce;
    }

    public void setFoodProduce(InspectCheck foodProduce) {
        this.foodProduce = foodProduce;
    }
}
