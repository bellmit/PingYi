package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.param.UserParam;
import com.example.upc.controller.searchParam.EnterpriseSearchParam;
import com.example.upc.controller.searchParam.UserSearchParam;
import com.example.upc.dao.SupervisionEnterpriseMapper;
import com.example.upc.dao.SysUserErrorMapper;
import com.example.upc.dao.SysUserMapper;
import com.example.upc.dataobject.SysIndustry;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.SysUserService;
import com.example.upc.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zcc
 * @date 2019/3/28 11:16
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SupervisionEnterpriseMapper supervisionEnterpriseMapper;
    @Autowired
    private SysUserErrorMapper sysUserErrorMapper;

    @Override
    public SysUser selectByLoginName(String loginName) throws BusinessException {
        return sysUserMapper.selectByLoginName(loginName);
    }

    @Override
    @Transactional
    public void save(UserParam param) {
        if(checkLoginNameExist(param.getLoginName(), param.getId())) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"用户名已被占用");
        }
        MD5Util md5Code =new MD5Util();
        String encryptedPassword = md5Code.md5("123456+");
        SysUser user = new SysUser();
        user.setUsername(param.getUsername());
        user.setLoginName(param.getLoginName());
        user.setPassword(encryptedPassword);
        user.setDeptId(param.getDeptId());
        user.setUserArea(param.getUserArea());
        user.setUserType(param.getUserType());
        user.setInfoName(param.getInfoName());
        user.setInfoId(param.getInfoId());
        user.setStatus(param.getStatus());
        user.setRemark(param.getRemark());
        user.setOperator("操作人");
        user.setOperateIp("124.124.124");
        user.setOperateTime(new Date());
        sysUserMapper.insertSelective(user);
    }

    @Override
    @Transactional
    public void update(UserParam param) {
        if(checkLoginNameExist(param.getLoginName(), param.getId())) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"用户名已被占用");
        }
        SysUser before = sysUserMapper.selectByPrimaryKey(param.getId());
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待更新的用户不存在");
        }

        SysUser after = new SysUser();
        after.setId(param.getId());
        after.setUsername(param.getUsername());
        after.setLoginName(param.getLoginName());
        after.setPassword(before.getPassword());
        after.setDeptId(param.getDeptId());
        after.setUserArea(param.getUserArea());
        after.setUserType(param.getUserType());
        after.setInfoName(param.getInfoName());
        after.setInfoId(param.getInfoId());
        after.setStatus(param.getStatus());
        after.setRemark(param.getRemark());
        after.setOperator("操作人");
        after.setOperateIp("124.124.124");
        after.setOperateTime(new Date());
        sysUserMapper.updateByPrimaryKeySelective(after);
    }

    @Override
    @Transactional
    public void changePsd(int id,String password) {
        SysUser before = sysUserMapper.selectByPrimaryKey(id);
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待更新的用户不存在");
        }
        String encryptedPassword = MD5Util.md5(password);
        sysUserMapper.changePsd(id,encryptedPassword);
    }

    @Override
    @Transactional
    public void changeUserPsd(int id, String oldPassword, String newPassword) {
        SysUser before = sysUserMapper.selectByPrimaryKey(id);
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待更新的用户不存在");
        }
        if(!before.getPassword().equals(MD5Util.md5(oldPassword))){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"原密码不正确");
        }
        String encryptedPassword = MD5Util.md5(newPassword);
        sysUserMapper.changePsd(id,encryptedPassword);
    }

    @Override
    public void delete(int userId) {
        SysUser user = sysUserMapper.selectByPrimaryKey(userId);
        if(user==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待删除的用户不存在，无法删除");
        }
        sysUserMapper.deleteByPrimaryKey(userId);
    }

    @Override
    public Map<String, Object> govGet(List<SysIndustry> sysIndustryList, List<Integer> sysAreaList, String supervisor) {
        Map<Integer,Integer> map = new HashMap<>();
        for (SysIndustry sysIndustry:sysIndustryList){
            EnterpriseSearchParam enterpriseSearchParam = new EnterpriseSearchParam();
            List<String> industryList = new ArrayList<>();
            industryList.add(sysIndustry.getRemark());
            enterpriseSearchParam.setAreaList(sysAreaList);
            enterpriseSearchParam.setIndustryList(industryList);
            enterpriseSearchParam.setSupervisor(supervisor);
            map.put(sysIndustry.getId(),supervisionEnterpriseMapper.countList(enterpriseSearchParam));
        }
        Map<String, Object> resultMap = new HashMap<>();
        EnterpriseSearchParam enterpriseSearchParam = new EnterpriseSearchParam();
        enterpriseSearchParam.setAreaList(sysAreaList);
        enterpriseSearchParam.setSupervisor(supervisor);
        enterpriseSearchParam.setIndustryList(sysIndustryList.stream().map((sysIndustry -> sysIndustry.getRemark())).collect(Collectors.toList()));
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageSize(5);
        pageQuery.setPageNo(1);
        resultMap.put("enterpriseList",supervisionEnterpriseMapper.getPage(pageQuery,enterpriseSearchParam));
        resultMap.put("enterpriseStatistics",map);
        return resultMap;
    }

    @Override
    public PageResult<SysUser> getPageByDeptId(int deptId, PageQuery page) {
        int count = sysUserMapper.countByDeptId(deptId);
        if (count > 0) {
            List<SysUser> list = sysUserMapper.getPageByDeptId(deptId, page);
            PageResult<SysUser> pageResult = new PageResult<>();
            pageResult.setData(list);
            pageResult.setTotal(count);
            return pageResult;
        }
        PageResult<SysUser> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public PageResult<SysUser> getAllPage(PageQuery pageQuery, UserSearchParam userSearchParam) {
        int count=sysUserMapper.countList(userSearchParam);
        if (count > 0) {
            List<SysUser> sysUsers = sysUserMapper.getAllPage(pageQuery,userSearchParam);
            PageResult<SysUser> pageResult = new PageResult<>();
            pageResult.setData(sysUsers);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<SysUser> pageResult = new PageResult<>();
        return pageResult;
    }

    public boolean checkLoginNameExist(String loginName, Integer userId) {
        return sysUserMapper.countByLoginName(loginName, userId) > 0;
    }

    @Override
    public void deleteAll() {
        sysUserErrorMapper.deleteAll();
    }

}
