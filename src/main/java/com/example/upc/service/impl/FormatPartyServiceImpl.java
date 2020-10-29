package com.example.upc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.FormatPartyParam;
import com.example.upc.controller.param.FormatPartySupParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.PartySearchParam;
import com.example.upc.dao.FormatPartyConfigMapper;
import com.example.upc.dao.FormatPartyMapper;
import com.example.upc.dao.SupervisionCaMapper;
import com.example.upc.dao.SupervisionEnterpriseMapper;
import com.example.upc.dataobject.*;
import com.example.upc.service.FormatPartyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FormatPartyServiceImpl implements FormatPartyService {
    @Autowired
    FormatPartyMapper formatPartyMapper;
    @Autowired
    FormatPartyConfigMapper formatPartyConfigMapper;
    @Autowired
    SupervisionCaMapper supervisionCaMapper;
    @Autowired
    SupervisionEnterpriseMapper supervisionEnterpriseMapper;

    @Override
    public PageResult getPage(PageQuery pageQuery, PartySearchParam partySearchParam) {
        int count=formatPartyMapper.countListSup(partySearchParam);
        if (count > 0) {
            List<FormatPartySupParam> faList = formatPartyMapper.getPage(pageQuery, partySearchParam);
            PageResult<FormatPartySupParam> pageResult = new PageResult<>();
            pageResult.setData(faList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<FormatPartySupParam> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public PageResult getPageAdmin(PageQuery pageQuery, PartySearchParam partySearchParam) {
        int count=formatPartyMapper.countListAdmin(partySearchParam);
        if (count > 0) {
            List<FormatPartySupParam> faList = formatPartyMapper.getPageAdmin(pageQuery, partySearchParam);
            PageResult<FormatPartySupParam> pageResult = new PageResult<>();
            pageResult.setData(faList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<FormatPartySupParam> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public PageResult getPageEnterprise(PageQuery pageQuery, Integer id, PartySearchParam partySearchParam) {

        int count=formatPartyMapper.countListEnterprise(id, partySearchParam);
        if (count > 0) {

            List<FormatParty> fdList = formatPartyMapper.getPageEnterprise(pageQuery, id, partySearchParam);
            PageResult<FormatParty> pageResult = new PageResult<>();
            pageResult.setData(fdList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<FormatParty> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    @Transactional
    public void insert(String json, SysUser sysUser) {
        FormatPartyParam formatPartyParam = JSONObject.parseObject(json,FormatPartyParam.class);
        FormatParty formatParty = new FormatParty();
        BeanUtils.copyProperties(formatPartyParam,formatParty);

        SupervisionEnterprise supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(sysUser.getInfoId());
        if(supervisionEnterprise==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"企业不存在");
        }
        formatParty.setUnit(sysUser.getInfoId());
        formatParty.setArea(supervisionEnterprise.getArea());
        formatParty.setUp("未上报");
        formatParty.setOperatorIp("124.124.124");
        formatParty.setOperatorTime(new Date());
        formatParty.setOperator("zcc");
        formatPartyMapper.insertSelective(formatParty);
        if(formatParty.getId()==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"插入失败");
        }
        formatPartyConfigMapper.deleteByParentId(formatParty.getId());
        List<FormatPartyConfig> formatPartyConfigList = formatPartyParam.getList();
        if(formatPartyConfigList.size()>0){
            formatPartyConfigMapper.batchInsert(formatPartyConfigList.stream().map((list)->{
                list.setOperatorIp("124.124.124");
                list.setOperatorTime(new Date());
                list.setOperator("zcc");
                list.setParentId(formatParty.getId());
                return list;}).collect(Collectors.toList()));
        }

        // TODO: sendEmail

//        http://localhost:8080/formatdishes/update?id=1&unit=%E5%B1%B1%E4%B8%9C%E5%A6%82%E6%96%B0%E5%85%AC%E5%8F%B8&number=2&name=%E5%A4%A7%E7%B1%B3%E9%A5%AD&price=11&type=%E4%B8%BB%E9%A3%9F&remark=%E7%B1%B3%E9%A5%AD
    }
    @Override
    public void delete(int fpId) {
        FormatParty formatParty = formatPartyMapper.selectByPrimaryKey(fpId);
        if(formatParty==null){
            throw new BusinessException(EmBusinessError.ID_ERROR);
        }
        formatPartyMapper.deleteByPrimaryKey(fpId);
        formatPartyConfigMapper.deleteByParentId(fpId);
    }

    @Override
    @Transactional
    public void update(String json, SysUser sysUser) {
        FormatPartyParam formatPartyParam = JSONObject.parseObject(json,FormatPartyParam.class);
        FormatParty formatParty = new FormatParty();
        BeanUtils.copyProperties(formatPartyParam,formatParty);
        FormatParty before = formatPartyMapper.selectByPrimaryKey(formatParty.getId());
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待更新条目不存在");
        }

        SupervisionEnterprise supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(sysUser.getInfoId());
        if(supervisionEnterprise==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"企业不存在");
        }
        formatParty.setUnit(sysUser.getInfoId());
        formatParty.setArea(supervisionEnterprise.getArea());
        formatParty.setUp("未上报");
        formatParty.setOperatorIp("124.124.124");
        formatParty.setOperatorTime(new Date());
        formatParty.setOperator("zcc");

        formatPartyConfigMapper.deleteByParentId(formatParty.getId());
        List<FormatPartyConfig> formatPartyConfigList = formatPartyParam.getList();
        if(formatPartyConfigList.size()>0){
            formatPartyConfigMapper.batchInsert(formatPartyConfigList.stream().map((list)->{
                list.setOperatorIp("124.124.124");
                list.setOperatorTime(new Date());
                list.setOperator("zcc");
                list.setParentId(formatParty.getId());
                return list;}).collect(Collectors.toList()));
        }
        formatPartyMapper.updateByPrimaryKeySelective(formatParty);
    }


    @Override
    public FormatPartyParam getById(int id) {
        FormatParty formatParty = formatPartyMapper.selectByPrimaryKey(id);
        FormatPartyParam formatPartyParam = new FormatPartyParam();
        BeanUtils.copyProperties(formatParty, formatPartyParam);
        List<FormatPartyConfig> list = formatPartyConfigMapper.selectByParentId(formatParty.getId());
        formatPartyParam.setList(list);
        return formatPartyParam;
    }

    @Override
    @Transactional
    public void updateRecord(int id)
    {
        FormatParty before = formatPartyMapper.selectByPrimaryKey(id);
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待更新条目不存在");
        }
        formatPartyMapper.updateRecord(id);
    }

    @Override
    public void fail() {
        throw new BusinessException(EmBusinessError.USER_NO);
    }

}
