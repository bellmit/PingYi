package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dao.SysDutiesInfoMapper;
import com.example.upc.dataobject.SysDutiesInfo;
import com.example.upc.service.SysDutiesInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author zcc
 * @date 2019/7/9 19:17
 */
@Service
public class SysDutiesInfoServiceImpl implements SysDutiesInfoService {
    @Autowired
    private SysDutiesInfoMapper sysDutiesInfoMapper;
    @Override
    public PageResult<SysDutiesInfo> getPage(PageQuery pageQuery) {
        int count= sysDutiesInfoMapper.countList();
        if (count > 0) {
            List<SysDutiesInfo> sysDutiesInfoList = sysDutiesInfoMapper.getPage(pageQuery);
            PageResult<SysDutiesInfo> pageResult = new PageResult<>();
            pageResult.setData(sysDutiesInfoList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<SysDutiesInfo> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    @Transactional
    public void insert(SysDutiesInfo sysDutiesInfo) {
        if (sysDutiesInfoMapper.countByName(sysDutiesInfo.getName(), sysDutiesInfo.getId())>0) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"存在相同职务名称");
        }
        sysDutiesInfo.setOperateIp("124.214.124");
        sysDutiesInfo.setOperateTime(new Date());
        sysDutiesInfo.setOperator("操作人");
        sysDutiesInfoMapper.insertSelective(sysDutiesInfo);
    }

    @Override
    @Transactional
    public void update(SysDutiesInfo sysDutiesInfo) {
        if (sysDutiesInfoMapper.countByName(sysDutiesInfo.getName(), sysDutiesInfo.getId())>0) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"存在相同职务名称");
        }
        SysDutiesInfo before = sysDutiesInfoMapper.selectByPrimaryKey(sysDutiesInfo.getId());
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待更新的职务不存在");
        }
        sysDutiesInfo.setOperateIp("124.214.124");
        sysDutiesInfo.setOperateTime(new Date());
        sysDutiesInfo.setOperator("操作人");
        sysDutiesInfoMapper.updateByPrimaryKeySelective(sysDutiesInfo);
    }

    @Override
    public void delete(int id) {
        SysDutiesInfo before = sysDutiesInfoMapper.selectByPrimaryKey(id);
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待更新的职务不存在");
        }
        sysDutiesInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<SysDutiesInfo> getList() {
        return sysDutiesInfoMapper.getList();
    }
}
