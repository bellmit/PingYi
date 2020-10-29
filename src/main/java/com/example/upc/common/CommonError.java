package com.example.upc.common;

import java.util.List;

/**
 * @author zcc
 * @date 2019/3/25 20:20
 */
public interface CommonError {
    public int getErrCode();
    public String getErrMsg();
    public CommonError setErrMsg(String errMsg);
    public CommonError setErrMsg(List errMsg);
    public List getErrList();
}
