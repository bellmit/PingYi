package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dao.SupervisionConfigCategoryMapper;
import com.example.upc.dao.SupervisionConfigLicenceMapper;
import com.example.upc.dataobject.SupervisionConfigLicence;
import com.example.upc.service.SupervisionConfigLicenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author zcc
 * @date 2019/6/26 17:22
 */
@Service
public class SupervisionConfigLicenceServiceImpl implements SupervisionConfigLicenceService {
    @Autowired
    private SupervisionConfigLicenceMapper supervisionConfigLicenceMapper;
    @Override
    public PageResult<SupervisionConfigLicence> getPage(PageQuery pageQuery) {
        int count= supervisionConfigLicenceMapper.countList();
        if (count > 0) {
            List<SupervisionConfigLicence> supervisionConfigLicenceList = supervisionConfigLicenceMapper.getPage(pageQuery);
            PageResult<SupervisionConfigLicence> pageResult = new PageResult<>();
            pageResult.setData(supervisionConfigLicenceList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<SupervisionConfigLicence> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    @Transactional
    public void insert(SupervisionConfigLicence supervisionConfigLicence) {
        supervisionConfigLicence.setOperateIp("124.214.124");
        supervisionConfigLicence.setOperateTime(new Date());
        supervisionConfigLicence.setOperator("操作人");
           supervisionConfigLicenceMapper.insertSelective(supervisionConfigLicence);
    }

    @Override
    @Transactional
    public void update(SupervisionConfigLicence supervisionConfigLicence) {
        SupervisionConfigLicence before = supervisionConfigLicenceMapper.selectByPrimaryKey(supervisionConfigLicence.getId());
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待更新配置不存在");
        }
        supervisionConfigLicence.setOperateIp("124.214.124");
        supervisionConfigLicence.setOperateTime(new Date());
        supervisionConfigLicence.setOperator("操作人");
        supervisionConfigLicenceMapper.updateByPrimaryKeySelective(supervisionConfigLicence);
    }

    @Override
    public List<SupervisionConfigLicence> getByPermiss(int id) {
        return supervisionConfigLicenceMapper.getByIndustry(id);
    }

    @Override
    public void delete(int id) {
        SupervisionConfigLicence before = supervisionConfigLicenceMapper.selectByPrimaryKey(id);
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待删除配置不存在");
        }
        supervisionConfigLicenceMapper.deleteByPrimaryKey(id);
    }

}
