package com.example.upc.service;

import com.example.upc.common.BusinessException;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.param.UserParam;
import com.example.upc.controller.searchParam.UserSearchParam;
import com.example.upc.dataobject.SysIndustry;
import com.example.upc.dataobject.SysUser;

import java.util.List;
import java.util.Map;

/**
 * @author zcc
 * @date 2019/3/28 11:11
 */
public interface SysUserService {
    SysUser selectByLoginName(String telephone) throws BusinessException;
    void save(UserParam param);
    void update(UserParam param);
    void changePsd(int id,String password);
    void changeUserPsd(int id,String oldPassword,String newPassword);
    void delete(int userId);
    Map<String,Object> govGet(List<SysIndustry> sysIndustryList, List<Integer> sysAreaList, String supervisor);
    PageResult<SysUser> getPageByDeptId(int deptId, PageQuery page);
    PageResult<SysUser> getAllPage(PageQuery page, UserSearchParam userSearchParam);
    void deleteAll();
}
