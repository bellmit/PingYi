package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.MorningCheckOutputParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dao.SupervisionCaMorningCheckMapper;
import com.example.upc.dataobject.SupervisionCaMorningCheck;
import com.example.upc.service.SupervisionCaMorningCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author zcc
 * @date 2019/6/26 17:18
 */
@Service
public class SupervisionCaMorningCheckServiceIml implements SupervisionCaMorningCheckService {

    @Autowired
    private SupervisionCaMorningCheckMapper supervisionCaMorningCheckMapper;

    @Override
    public PageResult<SupervisionCaMorningCheck> getPage(PageQuery pageQuery) {
        int count= supervisionCaMorningCheckMapper.countList();
        if (count > 0) {
            List<SupervisionCaMorningCheck> supervisionCaMorningCheckList = supervisionCaMorningCheckMapper.getPage(pageQuery);
            PageResult<SupervisionCaMorningCheck> pageResult = new PageResult<>();
            pageResult.setData(supervisionCaMorningCheckList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<SupervisionCaMorningCheck> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public PageResult<SupervisionCaMorningCheck> getPageByCaId(PageQuery pageQuery, Integer id) {
        int count= supervisionCaMorningCheckMapper.countListByCaId(id);
        if (count > 0) {
            List<SupervisionCaMorningCheck> supervisionCaMorningCheckList = supervisionCaMorningCheckMapper.getPageByCaId(pageQuery,id);
            PageResult<SupervisionCaMorningCheck> pageResult = new PageResult<>();
            pageResult.setData(supervisionCaMorningCheckList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<SupervisionCaMorningCheck> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    @Transactional
    public void insert(SupervisionCaMorningCheck supervisionCaMorningCheck) {
        supervisionCaMorningCheck.setOperateIp("124.124.214");
        supervisionCaMorningCheck.setOperateTime(new Date());
        supervisionCaMorningCheck.setOperator("操作人");
        supervisionCaMorningCheckMapper.insertSelective(supervisionCaMorningCheck);
    }

    @Override
    @Transactional
    public void update(SupervisionCaMorningCheck supervisionCaMorningCheck) {
        SupervisionCaMorningCheck before = supervisionCaMorningCheckMapper.selectByPrimaryKey(supervisionCaMorningCheck.getId());
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待更新晨检不存在");
        }
        supervisionCaMorningCheck.setOperateIp("124.124.214");
        supervisionCaMorningCheck.setOperateTime(new Date());
        supervisionCaMorningCheck.setOperator("操作人");
        supervisionCaMorningCheckMapper.updateByPrimaryKeySelective(supervisionCaMorningCheck);

    }

    @Override
    public void delete(int id) {
        SupervisionCaMorningCheck before = supervisionCaMorningCheckMapper.selectByPrimaryKey(id);
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待删除晨检不存在");
        }
        supervisionCaMorningCheckMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<MorningCheckOutputParam> output(String start,String end) {
        if (!start.isEmpty()&&!end.isEmpty()){
            List<MorningCheckOutputParam> outputList = supervisionCaMorningCheckMapper.output(start,end);
            if (outputList.size()>0)
            {
                return  outputList;
            }
            else
            {
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"无晨检记录供导出");
            }
        }
        else {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"无晨检记录供导出");
        }
    }
}
