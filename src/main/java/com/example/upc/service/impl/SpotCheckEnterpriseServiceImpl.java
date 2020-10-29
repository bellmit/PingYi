package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.param.SpotCheckEnterpriseSupParam;
import com.example.upc.controller.searchParam.SpotCheckEnterpriseSearchParam;
import com.example.upc.dao.SpotCheckEnterpriseMapper;
import com.example.upc.dao.SysUserMapper;
import com.example.upc.dataobject.SpotCheckEnterprise;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.SpotCheckEnterpriseService;
import com.example.upc.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class SpotCheckEnterpriseServiceImpl implements SpotCheckEnterpriseService {
    @Autowired
    SpotCheckEnterpriseMapper spotCheckEnterpriseMapper;
    @Autowired
    SysUserMapper sysUserMapper;

    //政府看
    @Override
    public PageResult getPage(PageQuery pageQuery, SpotCheckEnterpriseSearchParam spotCheckEnterpriseSearchParam) {
        int count=spotCheckEnterpriseMapper.countListSup(spotCheckEnterpriseSearchParam);
        if (count > 0) {
            List<SpotCheckEnterpriseSupParam> fpList = spotCheckEnterpriseMapper.getPage(pageQuery, spotCheckEnterpriseSearchParam);
            PageResult<SpotCheckEnterpriseSupParam> pageResult = new PageResult<>();
            pageResult.setData(fpList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<SpotCheckEnterpriseSupParam> pageResult = new PageResult<>();
        return pageResult;
    }

    //政府看
    @Override
    public PageResult getUserAdmin(PageQuery pageQuery, SpotCheckEnterpriseSearchParam spotCheckEnterpriseSearchParam) {
        int count=spotCheckEnterpriseMapper.countListAdmin(spotCheckEnterpriseSearchParam);
        if (count > 0) {
            List<SpotCheckEnterpriseSupParam> fpList = spotCheckEnterpriseMapper.getPageAdmin(pageQuery, spotCheckEnterpriseSearchParam);
            PageResult<SpotCheckEnterpriseSupParam> pageResult = new PageResult<>();
            pageResult.setData(fpList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<SpotCheckEnterpriseSupParam> pageResult = new PageResult<>();
        return pageResult;
    }
    //我看我自己

    @Override
    public PageResult<SpotCheckEnterpriseSupParam> getUser(PageQuery pageQuery,int id, SpotCheckEnterpriseSearchParam spotCheckEnterpriseSearchParam) {
        SpotCheckEnterprise spotCheckEnterprise = spotCheckEnterpriseMapper.getUser(id);
        if (spotCheckEnterprise == null)
        {
            throw new BusinessException(EmBusinessError.CHECK_USERNO);
        }
        int count=spotCheckEnterpriseMapper.countListEnterprise(id);
        if (count > 0) {
            List<SpotCheckEnterpriseSupParam> fpList = spotCheckEnterpriseMapper.getPageEnterprise(pageQuery, id);
            PageResult<SpotCheckEnterpriseSupParam> pageResult = new PageResult<>();
            pageResult.setData(fpList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<SpotCheckEnterpriseSupParam> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public PageResult<SpotCheckEnterprise> getPageOk(PageQuery pageQuery) {
        int count=spotCheckEnterpriseMapper.countListOk();
        if (count > 0) {
            List<SpotCheckEnterprise> fpList = spotCheckEnterpriseMapper.getPageOk(pageQuery);
            PageResult<SpotCheckEnterprise> pageResult = new PageResult<>();
            pageResult.setData(fpList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<SpotCheckEnterprise> pageResult = new PageResult<>();
        return pageResult;
    }

//    @Override
//    public SpotCheckEnterprise getUser(int id, SpotCheckEnterpriseSearchParam spotCheckEnterpriseSearchParam)
//    {
//        SpotCheckEnterprise spotCheckEnterprise = spotCheckEnterpriseMapper.getUser(id);
//        if (spotCheckEnterprise == null)
//        {
//            throw new BusinessException(EmBusinessError.CHECK_USERNO);
//        }
//        return (spotCheckEnterprise);
//    }

    @Override
    public void fail() {
        throw new BusinessException(EmBusinessError.USER_NO);
    }

    @Override
    @Transactional
    public void insert(SpotCheckEnterprise spotCheckEnterprise, SysUser sysUser) {

//        ValidationResult result = validator.validate(formatWasteParam);
//        if(result.isHasErrors()){
//            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
//        }
        SpotCheckEnterprise spotCheckEnterprise1 = new SpotCheckEnterprise();
        spotCheckEnterprise1.setEnterpriseName(spotCheckEnterprise.getEnterpriseName());
        spotCheckEnterprise1.setEnterpriseNickname(spotCheckEnterprise.getEnterpriseNickname());
        spotCheckEnterprise1.setArea(spotCheckEnterprise.getArea());
        spotCheckEnterprise1.setAddress(spotCheckEnterprise.getAddress());
        spotCheckEnterprise1.setNumber(spotCheckEnterprise.getNumber());
        spotCheckEnterprise1.setEnterpriseCharger(spotCheckEnterprise.getEnterpriseCharger());
        spotCheckEnterprise1.setPhone(spotCheckEnterprise.getPhone());
        spotCheckEnterprise1.setRemark(spotCheckEnterprise.getRemark());
        spotCheckEnterprise1.setDocument(spotCheckEnterprise.getDocument());
        spotCheckEnterprise1.setState(1);
        spotCheckEnterprise1.setOperator("操作人");
        spotCheckEnterprise1.setOperatorIp("124.124.124");
        spotCheckEnterprise1.setOperatorTime(new Date());

        // TODO: sendEmail

        if(sysUser.getUserType()==2)
        {
            spotCheckEnterpriseMapper.insertSelective(spotCheckEnterprise1);
            SysUser sysUser1 = new SysUser();
            MD5Util md5Code =new MD5Util();
            sysUser1.setUsername("抽检机构");
            sysUser1.setLoginName(spotCheckEnterprise1.getNumber());
            sysUser1.setPassword(md5Code.md5("12345678+"));
            sysUser1.setDeptId(0);
            sysUser1.setUsername("1");
            sysUser1.setUserType(5);
            sysUser1.setInfoId(spotCheckEnterprise1.getId());
            sysUser1.setStatus(1);
            sysUser1.setRemark("");
            sysUser1.setOperator("操作人");
            sysUser1.setOperateIp("124.124.124");
            sysUser1.setOperateTime(new Date());
            sysUserMapper.insertSelective(sysUser1);
        }
         else
        {
            spotCheckEnterpriseMapper.insertSelective(spotCheckEnterprise1);
            sysUserMapper.updateInforId(spotCheckEnterprise1.getId(),sysUser.getId());
        }
    }


    @Override
    @Transactional
    public void update(SpotCheckEnterprise spotCheckEnterprise) {

//        ValidationResult result = validator.validate(formatWasteParam);
//        if(result.isHasErrors()){
//            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
//        }
        SpotCheckEnterprise spotCheckEnterprise1 = new SpotCheckEnterprise();
        spotCheckEnterprise1.setId(spotCheckEnterprise.getId());
        spotCheckEnterprise1.setEnterpriseName(spotCheckEnterprise.getEnterpriseName());
        spotCheckEnterprise1.setEnterpriseNickname(spotCheckEnterprise.getEnterpriseNickname());
        spotCheckEnterprise1.setArea(spotCheckEnterprise.getArea());
        spotCheckEnterprise1.setAddress(spotCheckEnterprise.getAddress());
        spotCheckEnterprise1.setNumber(spotCheckEnterprise.getNumber());
        spotCheckEnterprise1.setEnterpriseCharger(spotCheckEnterprise.getEnterpriseCharger());
        spotCheckEnterprise1.setPhone(spotCheckEnterprise.getPhone());
        spotCheckEnterprise1.setRemark(spotCheckEnterprise.getRemark());
        spotCheckEnterprise1.setDocument(spotCheckEnterprise.getDocument());
        spotCheckEnterprise1.setState(spotCheckEnterprise.getState());
        spotCheckEnterprise1.setOperator("操作人");
        spotCheckEnterprise1.setOperatorIp("124.124.124");
        spotCheckEnterprise1.setOperatorTime(new Date());

        // TODO: sendEmail

        spotCheckEnterpriseMapper.updateByPrimaryKeySelective(spotCheckEnterprise1);
    }

    @Override
    public void check(int id) {
        spotCheckEnterpriseMapper.check(id);
    }

    @Override
    public void delete(int id) {
        SpotCheckEnterprise spotCheckEnterprise = spotCheckEnterpriseMapper.selectByPrimaryKey(id);
        if(spotCheckEnterprise==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"不存在，无法删除");
        }
        spotCheckEnterpriseMapper.deleteByPrimaryKey(id);
    }
}
