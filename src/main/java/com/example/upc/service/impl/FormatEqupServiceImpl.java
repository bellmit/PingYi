package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.common.ValidationResult;
import com.example.upc.common.ValidatorImpl;
import com.example.upc.controller.param.FormatPictureParam;
import com.example.upc.controller.param.FormatPictureSupParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.PictureSearchParam;
import com.example.upc.dao.*;
import com.example.upc.dataobject.FormatEqup;
import com.example.upc.dataobject.SupervisionEnterprise;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.FormatEqupService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class FormatEqupServiceImpl implements FormatEqupService {
    @Autowired
    FormatEqupMapper formatEqupMapper;
    @Autowired
    SupervisionEnterpriseMapper supervisionEnterpriseMapper;
    @Autowired
    SupervisionCaMapper supervisionCaMapper;
    @Autowired
    SysDeptMapper sysDeptMapper;
    @Autowired
    SysDeptAreaMapper sysDeptAreaMapper;
    @Autowired
    SysAreaMapper sysAreaMapper;
    @Autowired
    private ValidatorImpl validator;


    @Override
    public PageResult getPage(PageQuery pageQuery, PictureSearchParam pictureSearchParam) {
        int count=formatEqupMapper.countListSup(pictureSearchParam);
        if (count > 0) {
            List<FormatPictureSupParam> fpList = formatEqupMapper.getPage(pageQuery,pictureSearchParam);
            PageResult<FormatPictureSupParam> pageResult = new PageResult<>();
            pageResult.setData(fpList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<FormatPictureSupParam> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public PageResult getPageAdmin(PageQuery pageQuery,PictureSearchParam pictureSearchParam) {
        int count=formatEqupMapper.countListAdmin(pictureSearchParam);
        if (count > 0) {
            List<FormatPictureSupParam> fpList = formatEqupMapper.getPageAdmin(pageQuery,pictureSearchParam);
            PageResult<FormatPictureSupParam> pageResult = new PageResult<>();
            pageResult.setData(fpList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<FormatPictureSupParam> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public PageResult getPageEnterprise(PageQuery pageQuery, Integer id, PictureSearchParam pictureSearchParam) {

        int count=formatEqupMapper.countListEnterprise(id,pictureSearchParam);
        if (count > 0) {

            List<FormatEqup> fdList = formatEqupMapper.getPageEnterprise(pageQuery,id,pictureSearchParam);
            PageResult<FormatEqup> pageResult = new PageResult<>();
            pageResult.setData(fdList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<FormatEqup> pageResult = new PageResult<>();
        return pageResult;
    }


    @Override
    @Transactional
    public void insert(FormatPictureParam formatPictureParam, SysUser sysUser) {

        ValidationResult result = validator.validate(formatPictureParam);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }

        FormatEqup formatEqup = new FormatEqup();
        BeanUtils.copyProperties(formatPictureParam,formatEqup);

        SupervisionEnterprise supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(sysUser.getInfoId());
        if (supervisionEnterprise==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"无此企业信息");
        }
        formatEqup.setEnterprise(sysUser.getInfoId());
        formatEqup.setArea(supervisionEnterprise.getArea());
        formatEqup.setOperator("操作人");
        formatEqup.setOperatorIp("124.124.124");
        formatEqup.setOperatorTime(new Date());

        // TODO: sendEmail

        formatEqupMapper.insertSelective(formatEqup);
//        http://localhost:8080/formatdishes/update?id=1&unit=%E5%B1%B1%E4%B8%9C%E5%A6%82%E6%96%B0%E5%85%AC%E5%8F%B8&number=2&name=%E5%A4%A7%E7%B1%B3%E9%A5%AD&price=11&type=%E4%B8%BB%E9%A3%9F&remark=%E7%B1%B3%E9%A5%AD
    }
    @Override
    public void delete(int fpId) {
        FormatEqup formatEqup  = formatEqupMapper.selectByPrimaryKey(fpId);
        if(formatEqup==null){
            throw new BusinessException(EmBusinessError.ID_ERROR);
        }
        formatEqupMapper.deleteByPrimaryKey(fpId);
    }

    @Override
    @Transactional
    public void update(FormatPictureParam formatPictureParam, SysUser sysUser) {

        ValidationResult result = validator.validate(formatPictureParam);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }

        if(formatEqupMapper.selectByPrimaryKey(formatPictureParam.getId())==null){
            throw new BusinessException(EmBusinessError.UPDATE_ERROR);
        }

        FormatEqup formatEqup = new FormatEqup();
        BeanUtils.copyProperties(formatPictureParam,formatEqup);

        SupervisionEnterprise supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(sysUser.getInfoId());
        if (supervisionEnterprise==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"无此企业信息");
        }

        formatEqup.setEnterprise(sysUser.getInfoId());
        formatEqup.setArea(supervisionEnterprise.getArea());
        formatEqup.setOperator("操作人");
        formatEqup.setOperatorIp("124.124.124");
        formatEqup.setOperatorTime(new Date());

        // TODO: sendEmail

        formatEqupMapper.updateByPrimaryKeySelective(formatEqup);
    }

    @Override
    public void fail() {
        throw new BusinessException(EmBusinessError.USER_NO);
    }
}
