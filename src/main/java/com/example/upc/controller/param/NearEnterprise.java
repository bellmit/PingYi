package com.example.upc.controller.param;

public class NearEnterprise implements Comparable<NearEnterprise> {

    private Integer enterpriseId; //企业id
    private String enterpriseName; //企业名称
    private String shopName; //招牌名称
    private String cantactWay; //联系方式
    private String propagandaEnclosure; //门头照片
    private Float averageScore=0.0f; //平均分
    private String checkType; //类型
    private int distance; //企业与目前的距离
    private String point; //企业的坐标
    private String dynamicGrade; //量化等级

    @Override
    public int compareTo(NearEnterprise nearEnterprise){
        return this.getDistance() - nearEnterprise.getDistance();
    }

    public Integer getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Integer enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getCantactWay() {
        return cantactWay;
    }

    public void setCantactWay(String cantactWay) {
        this.cantactWay = cantactWay;
    }

    public String getPropagandaEnclosure() {
        return propagandaEnclosure;
    }

    public void setPropagandaEnclosure(String propagandaEnclosure) {
        this.propagandaEnclosure = propagandaEnclosure;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public float getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(float averageScore) {
        this.averageScore = averageScore;
    }

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public String getDynamicGrade() {
        return dynamicGrade;
    }

    public void setDynamicGrade(String dynamicGrade) {
        this.dynamicGrade = dynamicGrade;
    }

}
