package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dao.SupervisionConfigSubclassMapper;
import com.example.upc.dataobject.SupervisionConfigSubclass;
import com.example.upc.service.SupervisionConfigSubclassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * @author zcc
 * @date 2019/6/26 17:27
 */
@Service
public class SupervisionConfigSubclassServiceImpl implements SupervisionConfigSubclassService {
    @Autowired
    private SupervisionConfigSubclassMapper supervisionConfigSubclassMapper;
    @Override
    public PageResult<SupervisionConfigSubclass> getPage(PageQuery pageQuery) {
        int count= supervisionConfigSubclassMapper.countList();
        if (count > 0) {
            List<SupervisionConfigSubclass> supervisionConfigSubclassList = supervisionConfigSubclassMapper.getPage(pageQuery);
            PageResult<SupervisionConfigSubclass> pageResult = new PageResult<>();
            pageResult.setData(supervisionConfigSubclassList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<SupervisionConfigSubclass> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    @Transactional
    public void insert(SupervisionConfigSubclass supervisionConfigSubclass) {
        supervisionConfigSubclass.setOperateIp("124.214.124");
        supervisionConfigSubclass.setOperateTime(new Date());
        supervisionConfigSubclass.setOperator("操作人");
         supervisionConfigSubclassMapper.insertSelective(supervisionConfigSubclass);
    }

    @Override
    @Transactional
    public void update(SupervisionConfigSubclass supervisionConfigSubclass) {
        SupervisionConfigSubclass before = supervisionConfigSubclassMapper.selectByPrimaryKey(supervisionConfigSubclass.getId());
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待更新配置不存在");
        }
        supervisionConfigSubclass.setOperateIp("124.214.124");
        supervisionConfigSubclass.setOperateTime(new Date());
        supervisionConfigSubclass.setOperator("操作人");
        supervisionConfigSubclassMapper.updateByPrimaryKeySelective(supervisionConfigSubclass);
    }

    @Override
    public void delete(int id) {
        SupervisionConfigSubclass before = supervisionConfigSubclassMapper.selectByPrimaryKey(id);
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待删除配置不存在");
        }
        supervisionConfigSubclassMapper.deleteByPrimaryKey(id);
    }

}
