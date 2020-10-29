package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.param.QuickCheckEnterpriseSupParam;
import com.example.upc.controller.searchParam.QuickCheckEnterpriseSearchParam;
import com.example.upc.dao.QuickCheckEnterpriseMapper;
import com.example.upc.dao.SysUserMapper;
import com.example.upc.dataobject.QuickCheckEnterprise;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.QuickCheckEnterpriseService;
import com.example.upc.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class QuickCheckEnterpriseServiceImpl implements QuickCheckEnterpriseService {

    @Autowired
    QuickCheckEnterpriseMapper quickCheckEnterpriseMapper;
    @Autowired
    SysUserMapper sysUserMapper;


    //政府看
    @Override
    public PageResult getPage(PageQuery pageQuery, QuickCheckEnterpriseSearchParam quickCheckEnterpriseSearchParam) {
        int count=quickCheckEnterpriseMapper.countListSup(quickCheckEnterpriseSearchParam);
        if (count > 0) {
            List<QuickCheckEnterpriseSupParam> fpList = quickCheckEnterpriseMapper.getPage(pageQuery, quickCheckEnterpriseSearchParam);
            PageResult<QuickCheckEnterpriseSupParam> pageResult = new PageResult<>();
            pageResult.setData(fpList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<QuickCheckEnterpriseSupParam> pageResult = new PageResult<>();
        return pageResult;
    }
    //我看我自己
    @Override
    public PageResult getUser(PageQuery pageQuery,int id, QuickCheckEnterpriseSearchParam quickCheckEnterpriseSearchParam) {
        QuickCheckEnterprise quickCheckEnterprise = quickCheckEnterpriseMapper.getUser(id);
        if (quickCheckEnterprise == null)
        {
            throw new BusinessException(EmBusinessError.CHECK_USERNO);
        }
        int count=quickCheckEnterpriseMapper.countListEnterprise(id);
        if (count > 0) {
            List<QuickCheckEnterpriseSupParam> fpList = quickCheckEnterpriseMapper.getPageEnterprise(pageQuery, id);
            PageResult<QuickCheckEnterpriseSupParam> pageResult = new PageResult<>();
            pageResult.setData(fpList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<QuickCheckEnterpriseSupParam> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public PageResult getPageOk(PageQuery pageQuery) {
        int count=quickCheckEnterpriseMapper.countListOk();
        if (count > 0) {
            List<QuickCheckEnterprise> fpList = quickCheckEnterpriseMapper.getPageOk(pageQuery);
            PageResult<QuickCheckEnterprise> pageResult = new PageResult<>();
            pageResult.setData(fpList);
            pageResult.setTotal(count);
            return pageResult;
        }
        PageResult<QuickCheckEnterprise> pageResult = new PageResult<>();
        return pageResult;
    }

//    @Override
//    public QuickCheckEnterprise getUser(int id, QuickCheckEnterpriseSearchParam quickCheckEnterpriseSearchParam)
//    {
//        QuickCheckEnterprise quickCheckEnterprise = quickCheckEnterpriseMapper.getUser(id);
//        if (quickCheckEnterprise == null)
//        {
//            throw new BusinessException(EmBusinessError.CHECK_USERNO);
//        }
//        return (quickCheckEnterprise);
//    }

    @Override
    public void fail() {
        throw new BusinessException(EmBusinessError.USER_NO);
    }

    @Override
    @Transactional
    public void insert(QuickCheckEnterprise quickCheckEnterprise, SysUser sysUser) {

//        ValidationResult result = validator.validate(formatWasteParam);
//        if(result.isHasErrors()){
//            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
//        }
        QuickCheckEnterprise quickCheckEnterprise1 = new QuickCheckEnterprise();
        quickCheckEnterprise1.setEnterpriseName(quickCheckEnterprise.getEnterpriseName());
        quickCheckEnterprise1.setEnterpriseNickname(quickCheckEnterprise.getEnterpriseNickname());
        quickCheckEnterprise1.setArea(quickCheckEnterprise.getArea());
        quickCheckEnterprise1.setAddress(quickCheckEnterprise.getAddress());
        quickCheckEnterprise1.setNumber(quickCheckEnterprise.getNumber());
        quickCheckEnterprise1.setEnterpriseCharger(quickCheckEnterprise.getEnterpriseCharger());
        quickCheckEnterprise1.setPhone(quickCheckEnterprise.getPhone());
        quickCheckEnterprise1.setRemark(quickCheckEnterprise.getRemark());
        quickCheckEnterprise1.setDocument(quickCheckEnterprise.getDocument());
        quickCheckEnterprise1.setState(1);
        quickCheckEnterprise1.setOperator("操作人");
        quickCheckEnterprise1.setOperatorIp("124.124.124");
        quickCheckEnterprise1.setOperatorTime(new Date());

        // TODO: sendEmail

        if(sysUser.getUserType()==2)
        {
            quickCheckEnterpriseMapper.insertSelective(quickCheckEnterprise1);
            SysUser sysUser1 = new SysUser();
            MD5Util md5Code =new MD5Util();
            sysUser1.setUsername("快检机构");
            sysUser1.setLoginName(quickCheckEnterprise1.getNumber());
            sysUser1.setPassword(md5Code.md5("12345678+"));
            sysUser1.setDeptId(0);
            sysUser1.setUsername("1");
            sysUser1.setUserType(4);
            sysUser1.setInfoId(quickCheckEnterprise1.getId());
            sysUser1.setStatus(1);
            sysUser1.setRemark("");
            sysUser1.setOperator("操作人");
            sysUser1.setOperateIp("124.124.124");
            sysUser1.setOperateTime(new Date());
            sysUserMapper.insertSelective(sysUser1);
        }
        else
            {
            quickCheckEnterpriseMapper.insertSelective(quickCheckEnterprise1);
            sysUserMapper.updateInforId(quickCheckEnterprise1.getId(), sysUser.getId());
            }
    }

    @Override
    @Transactional
    public void update(QuickCheckEnterprise quickCheckEnterprise) {

//        ValidationResult result = validator.validate(formatWasteParam);
//        if(result.isHasErrors()){
//            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
//        }
        QuickCheckEnterprise quickCheckEnterprise1 = new QuickCheckEnterprise();
        quickCheckEnterprise1.setId(quickCheckEnterprise.getId());
        quickCheckEnterprise1.setEnterpriseName(quickCheckEnterprise.getEnterpriseName());
        quickCheckEnterprise1.setEnterpriseNickname(quickCheckEnterprise.getEnterpriseNickname());
        quickCheckEnterprise1.setArea(quickCheckEnterprise.getArea());
        quickCheckEnterprise1.setAddress(quickCheckEnterprise.getAddress());
        quickCheckEnterprise1.setNumber(quickCheckEnterprise.getNumber());
        quickCheckEnterprise1.setEnterpriseCharger(quickCheckEnterprise.getEnterpriseCharger());
        quickCheckEnterprise1.setPhone(quickCheckEnterprise.getPhone());
        quickCheckEnterprise1.setRemark(quickCheckEnterprise.getRemark());
        quickCheckEnterprise1.setDocument(quickCheckEnterprise.getDocument());
        quickCheckEnterprise1.setState(quickCheckEnterprise.getState());
        quickCheckEnterprise1.setOperator("操作人");
        quickCheckEnterprise1.setOperatorIp("124.124.124");
        quickCheckEnterprise1.setOperatorTime(new Date());

        // TODO: sendEmail

        quickCheckEnterpriseMapper.updateByPrimaryKeySelective(quickCheckEnterprise1);
    }

    @Override
    public void check(int id) {
        quickCheckEnterpriseMapper.check(id);
    }

    @Override
    public void delete(int id) {
        QuickCheckEnterprise quickCheckEnterprise = quickCheckEnterpriseMapper.selectByPrimaryKey(id);
        if(quickCheckEnterprise==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"不存在，无法删除");
        }
        quickCheckEnterpriseMapper.deleteByPrimaryKey(id);
    }

}
