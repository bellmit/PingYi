package com.example.upc.dataobject;

import java.util.Date;

public class ExamSubjectTopic {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column exam_subject_topic.id
     *
     * @mbg.generated Wed Aug 12 20:38:35 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column exam_subject_topic.subject_id
     *
     * @mbg.generated Wed Aug 12 20:38:35 CST 2020
     */
    private Integer subjectId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column exam_subject_topic.topic_id
     *
     * @mbg.generated Wed Aug 12 20:38:35 CST 2020
     */
    private Integer topicId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column exam_subject_topic.operator
     *
     * @mbg.generated Wed Aug 12 20:38:35 CST 2020
     */
    private String operator;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column exam_subject_topic.score
     *
     * @mbg.generated Wed Aug 12 20:38:35 CST 2020
     */
    private Float score;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column exam_subject_topic.operate_time
     *
     * @mbg.generated Wed Aug 12 20:38:35 CST 2020
     */
    private Date operateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column exam_subject_topic.operate_ip
     *
     * @mbg.generated Wed Aug 12 20:38:35 CST 2020
     */
    private String operateIp;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column exam_subject_topic.id
     *
     * @return the value of exam_subject_topic.id
     *
     * @mbg.generated Wed Aug 12 20:38:35 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column exam_subject_topic.id
     *
     * @param id the value for exam_subject_topic.id
     *
     * @mbg.generated Wed Aug 12 20:38:35 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column exam_subject_topic.subject_id
     *
     * @return the value of exam_subject_topic.subject_id
     *
     * @mbg.generated Wed Aug 12 20:38:35 CST 2020
     */
    public Integer getSubjectId() {
        return subjectId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column exam_subject_topic.subject_id
     *
     * @param subjectId the value for exam_subject_topic.subject_id
     *
     * @mbg.generated Wed Aug 12 20:38:35 CST 2020
     */
    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column exam_subject_topic.topic_id
     *
     * @return the value of exam_subject_topic.topic_id
     *
     * @mbg.generated Wed Aug 12 20:38:35 CST 2020
     */
    public Integer getTopicId() {
        return topicId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column exam_subject_topic.topic_id
     *
     * @param topicId the value for exam_subject_topic.topic_id
     *
     * @mbg.generated Wed Aug 12 20:38:35 CST 2020
     */
    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column exam_subject_topic.operator
     *
     * @return the value of exam_subject_topic.operator
     *
     * @mbg.generated Wed Aug 12 20:38:35 CST 2020
     */
    public String getOperator() {
        return operator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column exam_subject_topic.operator
     *
     * @param operator the value for exam_subject_topic.operator
     *
     * @mbg.generated Wed Aug 12 20:38:35 CST 2020
     */
    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column exam_subject_topic.score
     *
     * @return the value of exam_subject_topic.score
     *
     * @mbg.generated Wed Aug 12 20:38:35 CST 2020
     */
    public Float getScore() {
        return score;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column exam_subject_topic.score
     *
     * @param score the value for exam_subject_topic.score
     *
     * @mbg.generated Wed Aug 12 20:38:35 CST 2020
     */
    public void setScore(Float score) {
        this.score = score;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column exam_subject_topic.operate_time
     *
     * @return the value of exam_subject_topic.operate_time
     *
     * @mbg.generated Wed Aug 12 20:38:35 CST 2020
     */
    public Date getOperateTime() {
        return operateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column exam_subject_topic.operate_time
     *
     * @param operateTime the value for exam_subject_topic.operate_time
     *
     * @mbg.generated Wed Aug 12 20:38:35 CST 2020
     */
    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column exam_subject_topic.operate_ip
     *
     * @return the value of exam_subject_topic.operate_ip
     *
     * @mbg.generated Wed Aug 12 20:38:35 CST 2020
     */
    public String getOperateIp() {
        return operateIp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column exam_subject_topic.operate_ip
     *
     * @param operateIp the value for exam_subject_topic.operate_ip
     *
     * @mbg.generated Wed Aug 12 20:38:35 CST 2020
     */
    public void setOperateIp(String operateIp) {
        this.operateIp = operateIp == null ? null : operateIp.trim();
    }
}