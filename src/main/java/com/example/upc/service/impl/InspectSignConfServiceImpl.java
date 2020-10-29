package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.InspectSignParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.InspectSignSearchParam;
import com.example.upc.dao.InspectSignConfMapper;
import com.example.upc.dataobject.InspectSignConf;
import com.example.upc.service.InspectSignConfService;
import com.example.upc.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zcc
 * @date 2019/8/30 21:57
 */
@Service
public class InspectSignConfServiceImpl implements InspectSignConfService {
    @Autowired
    private InspectSignConfMapper inspectSignConfMapper;
    @Override
    public PageResult getPage(PageQuery pageQuery, InspectSignSearchParam inspectSignSearchParam) {
        List<Integer> idList = new ArrayList<>();
        if (inspectSignSearchParam.getSupervisorId()!=null){
            idList=StringUtil.splitToListInt(inspectSignSearchParam.getSupervisorId());
        }
        int count=inspectSignConfMapper.countList(inspectSignSearchParam,idList);
        if (count > 0) {
            List<InspectSignParam> inspectSignConfList = inspectSignConfMapper.getPage(pageQuery,inspectSignSearchParam,idList);
            PageResult<InspectSignParam> pageResult = new PageResult<>();
            pageResult.setData(inspectSignConfList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<InspectSignParam> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    @Transactional
    public void insert(InspectSignConf inspectSignConf) {
        if(inspectSignConfMapper.countByGaId(inspectSignConf.getGaId(),inspectSignConf.getId())>0){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"已有该人员签名");
        }
        inspectSignConf.setOperateIp("124.214.124");
        inspectSignConf.setOperateTime(new Date());
        inspectSignConf.setOperator("操作人");
        inspectSignConfMapper.insertSelective(inspectSignConf);
    }

    @Override
    @Transactional
    public void update(InspectSignConf inspectSignConf) {
        if(inspectSignConfMapper.countByGaId(inspectSignConf.getGaId(),inspectSignConf.getId())>0){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"已有该人员");
        }
        InspectSignConf before = inspectSignConfMapper.selectByPrimaryKey(inspectSignConf.getId());
        if(before ==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"不存在，无法更新");
        }
        inspectSignConf.setOperateIp("124.214.124");
        inspectSignConf.setOperateTime(new Date());
        inspectSignConf.setOperator("操作人");
        inspectSignConfMapper.updateByPrimaryKeySelective(inspectSignConf);
    }

    @Override
    public void delete(int id) {
        InspectSignConf before = inspectSignConfMapper.selectByPrimaryKey(id);
        if(before ==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"不存在，无法删除");
        }
        inspectSignConfMapper.deleteByPrimaryKey(id);
    }
}
