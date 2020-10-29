package com.example.upc.service;

import com.example.upc.controller.param.MonthlySelfCheckParam;
import com.example.upc.dataobject.MonthlySelfCheck;
import com.example.upc.dataobject.SysUser;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author 75186
 */
public interface MonthlySelfCheckService {
    void insertMonSelfcheck(MonthlySelfCheckParam monthlySelfCheckParam,SysUser sysUser) throws InvocationTargetException, IllegalAccessException;
    List<MonthlySelfCheck> selectByDate(MonthlySelfCheckParam monthlySelfCheckParam,SysUser sysUser);
    void deleteById(MonthlySelfCheckParam monthlySelfCheckParam);
    void standingBook(MonthlySelfCheckParam monthlySelfCheckParam) throws Exception;
}
