package com.example.upc.common;

import java.util.List;

/**
 * @author zcc
 * @date 2019/3/25 20:21
 */
public enum EmBusinessError implements CommonError{
    //通用错误类型
    PARAMETER_VALIDATION_ERROR(10001,"参数不合法"),
    UNKNOWN_ERROR(10002,"未知错误"),
    SAME_OUTTIME(10003,"有相同权限点"),
    PLEASE_LOGIN(10004,"请登录"),
    //20000为用户信息相关错误
    STUDENT_NOT_EXIST(20001,"账号不存在"),
    STUDENT_LOGIN_FAIL(20002,"请输入正确的帐号或密码"),
    USER_NO(20003,"用户的身份不正确，请检查后重试"),
    //30000为课程相关错误信息
    COUREE_ERROR(30001,"信息不存在"),
    FILE_ERROR(30002,"文件超出限制"),
    //40000为规范经营相关错误信息
    ID_ERROR(40001,"所选条目不存在，无法删除 "),
    NAME_ERROR(40002,"菜品名已被占用"),
    NUMBER_ERROR(40003,"菜品序号已被占用"),
    UPDATE_ERROR(40004,"待更新条目不存在，无法更新"),
    UPDATE_NOUSE(40005,"库存不足，无法完成操作，请检查当前库存"),
    UPDATE_ERROR2(40006,"待更新条目不存在，无法更新"),
    UPDATE_ERROR3(40007,"待更新条目不存在，无法更新"),
    DISHIES_NAME(40008,"当前名称已被占用，请检查后重试"),
    DISHIES_NUMBER(40009,"当前序号已被占用，请检查后重试"),
    DISHIES_TYPE(40010,"当前类型已被占用，请检查后重试"),
    DISHIES_All(40011,"输入内容已存在，请检查后重试"),
    MEASUREMENT_NAME(40012,"当前单位名称已被占用，请检查后重试"),
    CHECK_USERNO(40013,"暂无所属机构信息，请添加"),
    CHECK_USERERROR(40014,"暂无所属部门信息，请添加"),
    UPDATE(41111,"待更新条目不存在，无法更新"),

    FACE_ERROR(40015,"人脸识别失败");

    private EmBusinessError(int errCode,String errMsg){
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    private int errCode;
    private String errMsg;
    private List errList;
    @Override
    public int getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public List getErrList() {return this.errList;}

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg=errMsg;
        return this;
    }

    @Override
    public CommonError setErrMsg(List errMsg) {
        this.errList=errMsg;
        return this;
    }
}
