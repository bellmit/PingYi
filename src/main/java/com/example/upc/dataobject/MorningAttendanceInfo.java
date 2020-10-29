package com.example.upc.dataobject;

import java.util.Date;

public class MorningAttendanceInfo {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column morning_attendance_info.id
     *
     * @mbg.generated Tue Oct 06 19:48:41 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column morning_attendance_info.attendance_id
     *
     * @mbg.generated Tue Oct 06 19:48:41 CST 2020
     */
    private Integer attendanceId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column morning_attendance_info.employee_name
     *
     * @mbg.generated Tue Oct 06 19:48:41 CST 2020
     */
    private String employeeName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column morning_attendance_info.body_health
     *
     * @mbg.generated Tue Oct 06 19:48:41 CST 2020
     */
    private String bodyHealth;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column morning_attendance_info.attendance_situation
     *
     * @mbg.generated Tue Oct 06 19:48:41 CST 2020
     */
    private String attendanceSituation;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column morning_attendance_info.operator
     *
     * @mbg.generated Tue Oct 06 19:48:41 CST 2020
     */
    private String operator;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column morning_attendance_info.operator_ip
     *
     * @mbg.generated Tue Oct 06 19:48:41 CST 2020
     */
    private String operatorIp;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column morning_attendance_info.operator_time
     *
     * @mbg.generated Tue Oct 06 19:48:41 CST 2020
     */
    private Date operatorTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column morning_attendance_info.id
     *
     * @return the value of morning_attendance_info.id
     *
     * @mbg.generated Tue Oct 06 19:48:41 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column morning_attendance_info.id
     *
     * @param id the value for morning_attendance_info.id
     *
     * @mbg.generated Tue Oct 06 19:48:41 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column morning_attendance_info.attendance_id
     *
     * @return the value of morning_attendance_info.attendance_id
     *
     * @mbg.generated Tue Oct 06 19:48:41 CST 2020
     */
    public Integer getAttendanceId() {
        return attendanceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column morning_attendance_info.attendance_id
     *
     * @param attendanceId the value for morning_attendance_info.attendance_id
     *
     * @mbg.generated Tue Oct 06 19:48:41 CST 2020
     */
    public void setAttendanceId(Integer attendanceId) {
        this.attendanceId = attendanceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column morning_attendance_info.employee_name
     *
     * @return the value of morning_attendance_info.employee_name
     *
     * @mbg.generated Tue Oct 06 19:48:41 CST 2020
     */
    public String getEmployeeName() {
        return employeeName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column morning_attendance_info.employee_name
     *
     * @param employeeName the value for morning_attendance_info.employee_name
     *
     * @mbg.generated Tue Oct 06 19:48:41 CST 2020
     */
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName == null ? null : employeeName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column morning_attendance_info.body_health
     *
     * @return the value of morning_attendance_info.body_health
     *
     * @mbg.generated Tue Oct 06 19:48:41 CST 2020
     */
    public String getBodyHealth() {
        return bodyHealth;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column morning_attendance_info.body_health
     *
     * @param bodyHealth the value for morning_attendance_info.body_health
     *
     * @mbg.generated Tue Oct 06 19:48:41 CST 2020
     */
    public void setBodyHealth(String bodyHealth) {
        this.bodyHealth = bodyHealth == null ? null : bodyHealth.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column morning_attendance_info.attendance_situation
     *
     * @return the value of morning_attendance_info.attendance_situation
     *
     * @mbg.generated Tue Oct 06 19:48:41 CST 2020
     */
    public String getAttendanceSituation() {
        return attendanceSituation;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column morning_attendance_info.attendance_situation
     *
     * @param attendanceSituation the value for morning_attendance_info.attendance_situation
     *
     * @mbg.generated Tue Oct 06 19:48:41 CST 2020
     */
    public void setAttendanceSituation(String attendanceSituation) {
        this.attendanceSituation = attendanceSituation == null ? null : attendanceSituation.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column morning_attendance_info.operator
     *
     * @return the value of morning_attendance_info.operator
     *
     * @mbg.generated Tue Oct 06 19:48:41 CST 2020
     */
    public String getOperator() {
        return operator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column morning_attendance_info.operator
     *
     * @param operator the value for morning_attendance_info.operator
     *
     * @mbg.generated Tue Oct 06 19:48:41 CST 2020
     */
    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column morning_attendance_info.operator_ip
     *
     * @return the value of morning_attendance_info.operator_ip
     *
     * @mbg.generated Tue Oct 06 19:48:41 CST 2020
     */
    public String getOperatorIp() {
        return operatorIp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column morning_attendance_info.operator_ip
     *
     * @param operatorIp the value for morning_attendance_info.operator_ip
     *
     * @mbg.generated Tue Oct 06 19:48:41 CST 2020
     */
    public void setOperatorIp(String operatorIp) {
        this.operatorIp = operatorIp == null ? null : operatorIp.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column morning_attendance_info.operator_time
     *
     * @return the value of morning_attendance_info.operator_time
     *
     * @mbg.generated Tue Oct 06 19:48:41 CST 2020
     */
    public Date getOperatorTime() {
        return operatorTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column morning_attendance_info.operator_time
     *
     * @param operatorTime the value for morning_attendance_info.operator_time
     *
     * @mbg.generated Tue Oct 06 19:48:41 CST 2020
     */
    public void setOperatorTime(Date operatorTime) {
        this.operatorTime = operatorTime;
    }
}