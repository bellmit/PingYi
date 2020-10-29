package com.example.upc.controller.param;

import java.util.Date;

/**
 * @author zcc
 * @date 2019/7/17 15:29
 */
public class EnterpriseListResult {
    @Override
    public String toString() {
        return "EnterpriseListResult{" +
                "id=" + id +
                ", enterpriseName='" + enterpriseName + '\'' +
                ", operationMode='" + operationMode + '\'' +
                ", legalPerson='" + legalPerson + '\'' +
                ", cantactWay='" + cantactWay + '\'' +
                ", businessState=" + businessState +
                ", abnormalId=" + abnormalId +
                ", enterpriseScale='" + enterpriseScale + '\'' +
                ", abnormalContent='" + abnormalContent + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", area=" + area +
                ", grid='" + grid + '\'' +
                ", supervisor='" + supervisor + '\'' +
                ", regulators='" + regulators + '\'' +
                ", isStop=" + isStop +
                ", foodBusiness=" + foodBusiness +
                ", foodCommon=" + foodCommon +
                ", foodCirculate=" + foodCirculate +
                ", foodProduce=" + foodProduce +
                ", drugsBusiness=" + drugsBusiness +
                ", medicalUse=" + medicalUse +
                ", cosmeticsUse=" + cosmeticsUse +
                ", endTime=" + endTime +
                '}';
    }

    private Integer id;
    private String enterpriseName;
    private String operationMode;
    private String legalPerson;
    private String cantactWay;
    private Integer businessState;
    private Integer abnormalId;
    private String enterpriseScale;
    private String abnormalContent;
    private String idNumber;
    private Integer area;
    private String grid;
    private String supervisor;
    private String regulators;
    private Integer isStop;
    private Integer foodBusiness;
    private Integer foodCommon;
    private Integer foodCirculate;
    private Integer foodProduce;
    private Integer drugsBusiness;
    private Integer medicalUse;
    private Integer cosmeticsUse;
    private Date endTime;

    public String getAbnormalContent() {
        return abnormalContent;
    }

    public void setAbnormalContent(String abnormalContent) {
        this.abnormalContent = abnormalContent;
    }

    public String getCantactWay() {
        return cantactWay;
    }

    public void setCantactWay(String cantactWay) {
        this.cantactWay = cantactWay;
    }

    public String getOperationMode() {
        return operationMode;
    }

    public void setOperationMode(String operationMode) {
        this.operationMode = operationMode;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public Integer getBusinessState() {
        return businessState;
    }

    public void setBusinessState(Integer businessState) {
        this.businessState = businessState;
    }

    public Integer getAbnormalId() {
        return abnormalId;
    }

    public void setAbnormalId(Integer abnormalId) {
        this.abnormalId = abnormalId;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getEnterpriseScale() {
        return enterpriseScale;
    }

    public void setEnterpriseScale(String enterpriseScale) {
        this.enterpriseScale = enterpriseScale;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public String getGrid() {
        return grid;
    }

    public void setGrid(String grid) {
        this.grid = grid;
    }

    public String getRegulators() {
        return regulators;
    }

    public void setRegulators(String regulators) {
        this.regulators = regulators;
    }

    public Integer getIsStop() {
        return isStop;
    }

    public void setIsStop(Integer isStop) {
        this.isStop = isStop;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getFoodBusiness() {
        return foodBusiness;
    }

    public void setFoodBusiness(Integer foodBusiness) {
        this.foodBusiness = foodBusiness;
    }

    public Integer getFoodCommon() {
        return foodCommon;
    }

    public void setFoodCommon(Integer foodCommon) {
        this.foodCommon = foodCommon;
    }

    public Integer getFoodCirculate() {
        return foodCirculate;
    }

    public void setFoodCirculate(Integer foodCirculate) {
        this.foodCirculate = foodCirculate;
    }

    public Integer getFoodProduce() {
        return foodProduce;
    }

    public void setFoodProduce(Integer foodProduce) {
        this.foodProduce = foodProduce;
    }

    public Integer getDrugsBusiness() {
        return drugsBusiness;
    }

    public void setDrugsBusiness(Integer drugsBusiness) {
        this.drugsBusiness = drugsBusiness;
    }

    public Integer getMedicalUse() {
        return medicalUse;
    }

    public void setMedicalUse(Integer medicalUse) {
        this.medicalUse = medicalUse;
    }

    public Integer getCosmeticsUse() {
        return cosmeticsUse;
    }

    public void setCosmeticsUse(Integer cosmeticsUse) {
        this.cosmeticsUse = cosmeticsUse;
    }
}
