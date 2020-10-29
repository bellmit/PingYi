package com.example.upc.dataobject;

import java.util.Date;

public class SysIndustry {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_industry.id
     *
     * @mbg.generated Fri Jul 12 11:22:39 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_industry.name
     *
     * @mbg.generated Fri Jul 12 11:22:39 CST 2019
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_industry.number
     *
     * @mbg.generated Fri Jul 12 11:22:39 CST 2019
     */
    private Integer number;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_industry.premiss_name
     *
     * @mbg.generated Fri Jul 12 11:22:39 CST 2019
     */
    private String premissName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_industry.status
     *
     * @mbg.generated Fri Jul 12 11:22:39 CST 2019
     */
    private Integer status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_industry.remark
     *
     * @mbg.generated Fri Jul 12 11:22:39 CST 2019
     */
    private String remark;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_industry.operator
     *
     * @mbg.generated Fri Jul 12 11:22:39 CST 2019
     */
    private String operator;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_industry.operate_time
     *
     * @mbg.generated Fri Jul 12 11:22:39 CST 2019
     */
    private Date operateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_industry.operate_ip
     *
     * @mbg.generated Fri Jul 12 11:22:39 CST 2019
     */
    private String operateIp;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_industry.id
     *
     * @return the value of sys_industry.id
     *
     * @mbg.generated Fri Jul 12 11:22:39 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_industry.id
     *
     * @param id the value for sys_industry.id
     *
     * @mbg.generated Fri Jul 12 11:22:39 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_industry.name
     *
     * @return the value of sys_industry.name
     *
     * @mbg.generated Fri Jul 12 11:22:39 CST 2019
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_industry.name
     *
     * @param name the value for sys_industry.name
     *
     * @mbg.generated Fri Jul 12 11:22:39 CST 2019
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_industry.number
     *
     * @return the value of sys_industry.number
     *
     * @mbg.generated Fri Jul 12 11:22:39 CST 2019
     */
    public Integer getNumber() {
        return number;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_industry.number
     *
     * @param number the value for sys_industry.number
     *
     * @mbg.generated Fri Jul 12 11:22:39 CST 2019
     */
    public void setNumber(Integer number) {
        this.number = number;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_industry.premiss_name
     *
     * @return the value of sys_industry.premiss_name
     *
     * @mbg.generated Fri Jul 12 11:22:39 CST 2019
     */
    public String getPremissName() {
        return premissName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_industry.premiss_name
     *
     * @param premissName the value for sys_industry.premiss_name
     *
     * @mbg.generated Fri Jul 12 11:22:39 CST 2019
     */
    public void setPremissName(String premissName) {
        this.premissName = premissName == null ? null : premissName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_industry.status
     *
     * @return the value of sys_industry.status
     *
     * @mbg.generated Fri Jul 12 11:22:39 CST 2019
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_industry.status
     *
     * @param status the value for sys_industry.status
     *
     * @mbg.generated Fri Jul 12 11:22:39 CST 2019
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_industry.remark
     *
     * @return the value of sys_industry.remark
     *
     * @mbg.generated Fri Jul 12 11:22:39 CST 2019
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_industry.remark
     *
     * @param remark the value for sys_industry.remark
     *
     * @mbg.generated Fri Jul 12 11:22:39 CST 2019
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_industry.operator
     *
     * @return the value of sys_industry.operator
     *
     * @mbg.generated Fri Jul 12 11:22:39 CST 2019
     */
    public String getOperator() {
        return operator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_industry.operator
     *
     * @param operator the value for sys_industry.operator
     *
     * @mbg.generated Fri Jul 12 11:22:39 CST 2019
     */
    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_industry.operate_time
     *
     * @return the value of sys_industry.operate_time
     *
     * @mbg.generated Fri Jul 12 11:22:39 CST 2019
     */
    public Date getOperateTime() {
        return operateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_industry.operate_time
     *
     * @param operateTime the value for sys_industry.operate_time
     *
     * @mbg.generated Fri Jul 12 11:22:39 CST 2019
     */
    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_industry.operate_ip
     *
     * @return the value of sys_industry.operate_ip
     *
     * @mbg.generated Fri Jul 12 11:22:39 CST 2019
     */
    public String getOperateIp() {
        return operateIp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_industry.operate_ip
     *
     * @param operateIp the value for sys_industry.operate_ip
     *
     * @mbg.generated Fri Jul 12 11:22:39 CST 2019
     */
    public void setOperateIp(String operateIp) {
        this.operateIp = operateIp == null ? null : operateIp.trim();
    }
}