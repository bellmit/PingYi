package com.example.upc.dataobject;

import javax.validation.constraints.NotBlank;
import java.util.Date;

public class InspectDailyFood {
    private Integer id;
    private Integer checkType;
    private Integer checkObjectId;
    @NotBlank(message = "被检单位不能为空")
    private String checkObject;
    private Integer industry;
    private Integer region;
    private Integer grid;
    @NotBlank(message = "检查地址不能为空")
    private String checkAddress;
    @NotBlank(message = "许可证号不能为空")
    private String okNumber;
    @NotBlank(message = "负责人不能为空")
    private String chargePerson;
    private Integer checkOrgan;
    @NotBlank(message = "联系方式不能为空")
    private String contactPhone;
    private String entourage;
    private String supervisorId;
    @NotBlank(message = "执法人员不能为空")
    private String supervisor;
    @NotBlank(message = "执法证号不能为空")
    private String supervisorNumber;
    private String checkDate;
    private String checkStartHour;
    private String checkStartMinute;
    private String checkEndHour;
    private String checkEndMinute;
    private Date lastCheckTime;
    private Integer checkCount;
    private Integer checkFrequence;
    private Integer checkTotal;
    private String checkResult;
    private String resultProcess;
    private String problem;
    private String disposalMeasures;
    private String firstEnforcer;
    private String secondEnforcer;
    private String document;
    private Integer status;
    private String operator;
    private String operatorIp;
    private Date operatorTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column inspect_daily_food.id
     *
     * @return the value of inspect_daily_food.id
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column inspect_daily_food.id
     *
     * @param id the value for inspect_daily_food.id
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column inspect_daily_food.check_type
     *
     * @return the value of inspect_daily_food.check_type
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public Integer getCheckType() {
        return checkType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column inspect_daily_food.check_type
     *
     * @param checkType the value for inspect_daily_food.check_type
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public void setCheckType(Integer checkType) {
        this.checkType = checkType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column inspect_daily_food.check_object_id
     *
     * @return the value of inspect_daily_food.check_object_id
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public Integer getCheckObjectId() {
        return checkObjectId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column inspect_daily_food.check_object_id
     *
     * @param checkObjectId the value for inspect_daily_food.check_object_id
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public void setCheckObjectId(Integer checkObjectId) {
        this.checkObjectId = checkObjectId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column inspect_daily_food.check_object
     *
     * @return the value of inspect_daily_food.check_object
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public String getCheckObject() {
        return checkObject;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column inspect_daily_food.check_object
     *
     * @param checkObject the value for inspect_daily_food.check_object
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public void setCheckObject(String checkObject) {
        this.checkObject = checkObject == null ? null : checkObject.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column inspect_daily_food.industry
     *
     * @return the value of inspect_daily_food.industry
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public Integer getIndustry() {
        return industry;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column inspect_daily_food.industry
     *
     * @param industry the value for inspect_daily_food.industry
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public void setIndustry(Integer industry) {
        this.industry = industry;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column inspect_daily_food.region
     *
     * @return the value of inspect_daily_food.region
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public Integer getRegion() {
        return region;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column inspect_daily_food.region
     *
     * @param region the value for inspect_daily_food.region
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public void setRegion(Integer region) {
        this.region = region;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column inspect_daily_food.grid
     *
     * @return the value of inspect_daily_food.grid
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public Integer getGrid() {
        return grid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column inspect_daily_food.grid
     *
     * @param grid the value for inspect_daily_food.grid
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public void setGrid(Integer grid) {
        this.grid = grid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column inspect_daily_food.check_address
     *
     * @return the value of inspect_daily_food.check_address
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public String getCheckAddress() {
        return checkAddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column inspect_daily_food.check_address
     *
     * @param checkAddress the value for inspect_daily_food.check_address
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public void setCheckAddress(String checkAddress) {
        this.checkAddress = checkAddress == null ? null : checkAddress.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column inspect_daily_food.ok_number
     *
     * @return the value of inspect_daily_food.ok_number
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public String getOkNumber() {
        return okNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column inspect_daily_food.ok_number
     *
     * @param okNumber the value for inspect_daily_food.ok_number
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public void setOkNumber(String okNumber) {
        this.okNumber = okNumber == null ? null : okNumber.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column inspect_daily_food.charge_person
     *
     * @return the value of inspect_daily_food.charge_person
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public String getChargePerson() {
        return chargePerson;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column inspect_daily_food.charge_person
     *
     * @param chargePerson the value for inspect_daily_food.charge_person
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public void setChargePerson(String chargePerson) {
        this.chargePerson = chargePerson == null ? null : chargePerson.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column inspect_daily_food.check_organ
     *
     * @return the value of inspect_daily_food.check_organ
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public Integer getCheckOrgan() {
        return checkOrgan;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column inspect_daily_food.check_organ
     *
     * @param checkOrgan the value for inspect_daily_food.check_organ
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public void setCheckOrgan(Integer checkOrgan) {
        this.checkOrgan = checkOrgan;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column inspect_daily_food.contact_phone
     *
     * @return the value of inspect_daily_food.contact_phone
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public String getContactPhone() {
        return contactPhone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column inspect_daily_food.contact_phone
     *
     * @param contactPhone the value for inspect_daily_food.contact_phone
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone == null ? null : contactPhone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column inspect_daily_food.entourage
     *
     * @return the value of inspect_daily_food.entourage
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public String getEntourage() {
        return entourage;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column inspect_daily_food.entourage
     *
     * @param entourage the value for inspect_daily_food.entourage
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public void setEntourage(String entourage) {
        this.entourage = entourage == null ? null : entourage.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column inspect_daily_food.supervisor_id
     *
     * @return the value of inspect_daily_food.supervisor_id
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public String getSupervisorId() {
        return supervisorId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column inspect_daily_food.supervisor_id
     *
     * @param supervisorId the value for inspect_daily_food.supervisor_id
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public void setSupervisorId(String supervisorId) {
        this.supervisorId = supervisorId == null ? null : supervisorId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column inspect_daily_food.supervisor
     *
     * @return the value of inspect_daily_food.supervisor
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public String getSupervisor() {
        return supervisor;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column inspect_daily_food.supervisor
     *
     * @param supervisor the value for inspect_daily_food.supervisor
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor == null ? null : supervisor.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column inspect_daily_food.supervisor_number
     *
     * @return the value of inspect_daily_food.supervisor_number
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public String getSupervisorNumber() {
        return supervisorNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column inspect_daily_food.supervisor_number
     *
     * @param supervisorNumber the value for inspect_daily_food.supervisor_number
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public void setSupervisorNumber(String supervisorNumber) {
        this.supervisorNumber = supervisorNumber == null ? null : supervisorNumber.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column inspect_daily_food.check_date
     *
     * @return the value of inspect_daily_food.check_date
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public String getCheckDate() {
        return checkDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column inspect_daily_food.check_date
     *
     * @param checkDate the value for inspect_daily_food.check_date
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate == null ? null : checkDate.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column inspect_daily_food.check_start_hour
     *
     * @return the value of inspect_daily_food.check_start_hour
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public String getCheckStartHour() {
        return checkStartHour;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column inspect_daily_food.check_start_hour
     *
     * @param checkStartHour the value for inspect_daily_food.check_start_hour
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public void setCheckStartHour(String checkStartHour) {
        this.checkStartHour = checkStartHour == null ? null : checkStartHour.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column inspect_daily_food.check_start_minute
     *
     * @return the value of inspect_daily_food.check_start_minute
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public String getCheckStartMinute() {
        return checkStartMinute;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column inspect_daily_food.check_start_minute
     *
     * @param checkStartMinute the value for inspect_daily_food.check_start_minute
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public void setCheckStartMinute(String checkStartMinute) {
        this.checkStartMinute = checkStartMinute == null ? null : checkStartMinute.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column inspect_daily_food.check_end_hour
     *
     * @return the value of inspect_daily_food.check_end_hour
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public String getCheckEndHour() {
        return checkEndHour;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column inspect_daily_food.check_end_hour
     *
     * @param checkEndHour the value for inspect_daily_food.check_end_hour
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public void setCheckEndHour(String checkEndHour) {
        this.checkEndHour = checkEndHour == null ? null : checkEndHour.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column inspect_daily_food.check_end_minute
     *
     * @return the value of inspect_daily_food.check_end_minute
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public String getCheckEndMinute() {
        return checkEndMinute;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column inspect_daily_food.check_end_minute
     *
     * @param checkEndMinute the value for inspect_daily_food.check_end_minute
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public void setCheckEndMinute(String checkEndMinute) {
        this.checkEndMinute = checkEndMinute == null ? null : checkEndMinute.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column inspect_daily_food.last_check_time
     *
     * @return the value of inspect_daily_food.last_check_time
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public Date getLastCheckTime() {
        return lastCheckTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column inspect_daily_food.last_check_time
     *
     * @param lastCheckTime the value for inspect_daily_food.last_check_time
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public void setLastCheckTime(Date lastCheckTime) {
        this.lastCheckTime = lastCheckTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column inspect_daily_food.check_count
     *
     * @return the value of inspect_daily_food.check_count
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public Integer getCheckCount() {
        return checkCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column inspect_daily_food.check_count
     *
     * @param checkCount the value for inspect_daily_food.check_count
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public void setCheckCount(Integer checkCount) {
        this.checkCount = checkCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column inspect_daily_food.check_frequence
     *
     * @return the value of inspect_daily_food.check_frequence
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public Integer getCheckFrequence() {
        return checkFrequence;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column inspect_daily_food.check_frequence
     *
     * @param checkFrequence the value for inspect_daily_food.check_frequence
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public void setCheckFrequence(Integer checkFrequence) {
        this.checkFrequence = checkFrequence;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column inspect_daily_food.check_total
     *
     * @return the value of inspect_daily_food.check_total
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public Integer getCheckTotal() {
        return checkTotal;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column inspect_daily_food.check_total
     *
     * @param checkTotal the value for inspect_daily_food.check_total
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public void setCheckTotal(Integer checkTotal) {
        this.checkTotal = checkTotal;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column inspect_daily_food.check_result
     *
     * @return the value of inspect_daily_food.check_result
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public String getCheckResult() {
        return checkResult;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column inspect_daily_food.check_result
     *
     * @param checkResult the value for inspect_daily_food.check_result
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult == null ? null : checkResult.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column inspect_daily_food.result_process
     *
     * @return the value of inspect_daily_food.result_process
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public String getResultProcess() {
        return resultProcess;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column inspect_daily_food.result_process
     *
     * @param resultProcess the value for inspect_daily_food.result_process
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public void setResultProcess(String resultProcess) {
        this.resultProcess = resultProcess == null ? null : resultProcess.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column inspect_daily_food.problem
     *
     * @return the value of inspect_daily_food.problem
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public String getProblem() {
        return problem;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column inspect_daily_food.problem
     *
     * @param problem the value for inspect_daily_food.problem
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public void setProblem(String problem) {
        this.problem = problem == null ? null : problem.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column inspect_daily_food.disposal_measures
     *
     * @return the value of inspect_daily_food.disposal_measures
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public String getDisposalMeasures() {
        return disposalMeasures;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column inspect_daily_food.disposal_measures
     *
     * @param disposalMeasures the value for inspect_daily_food.disposal_measures
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public void setDisposalMeasures(String disposalMeasures) {
        this.disposalMeasures = disposalMeasures == null ? null : disposalMeasures.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column inspect_daily_food.first_enforcer
     *
     * @return the value of inspect_daily_food.first_enforcer
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public String getFirstEnforcer() {
        return firstEnforcer;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column inspect_daily_food.first_enforcer
     *
     * @param firstEnforcer the value for inspect_daily_food.first_enforcer
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public void setFirstEnforcer(String firstEnforcer) {
        this.firstEnforcer = firstEnforcer == null ? null : firstEnforcer.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column inspect_daily_food.second_enforcer
     *
     * @return the value of inspect_daily_food.second_enforcer
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public String getSecondEnforcer() {
        return secondEnforcer;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column inspect_daily_food.second_enforcer
     *
     * @param secondEnforcer the value for inspect_daily_food.second_enforcer
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public void setSecondEnforcer(String secondEnforcer) {
        this.secondEnforcer = secondEnforcer == null ? null : secondEnforcer.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column inspect_daily_food.document
     *
     * @return the value of inspect_daily_food.document
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public String getDocument() {
        return document;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column inspect_daily_food.document
     *
     * @param document the value for inspect_daily_food.document
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public void setDocument(String document) {
        this.document = document == null ? null : document.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column inspect_daily_food.status
     *
     * @return the value of inspect_daily_food.status
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column inspect_daily_food.status
     *
     * @param status the value for inspect_daily_food.status
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column inspect_daily_food.operator
     *
     * @return the value of inspect_daily_food.operator
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public String getOperator() {
        return operator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column inspect_daily_food.operator
     *
     * @param operator the value for inspect_daily_food.operator
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column inspect_daily_food.operator_ip
     *
     * @return the value of inspect_daily_food.operator_ip
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public String getOperatorIp() {
        return operatorIp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column inspect_daily_food.operator_ip
     *
     * @param operatorIp the value for inspect_daily_food.operator_ip
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public void setOperatorIp(String operatorIp) {
        this.operatorIp = operatorIp == null ? null : operatorIp.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column inspect_daily_food.operator_time
     *
     * @return the value of inspect_daily_food.operator_time
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public Date getOperatorTime() {
        return operatorTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column inspect_daily_food.operator_time
     *
     * @param operatorTime the value for inspect_daily_food.operator_time
     *
     * @mbg.generated Thu Sep 19 22:37:14 CST 2019
     */
    public void setOperatorTime(Date operatorTime) {
        this.operatorTime = operatorTime;
    }
}