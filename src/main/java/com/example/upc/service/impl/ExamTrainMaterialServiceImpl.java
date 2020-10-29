package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.param.TrainMaterialParam;
import com.example.upc.controller.searchParam.ExamTrainMaterialSearchParam;
import com.example.upc.dao.ExamTrainCourseMaterialMapper;
import com.example.upc.dao.ExamTrainMaterialMapper;
import com.example.upc.dataobject.ExamTrainMaterial;
import com.example.upc.dataobject.ExamTrainMaterialWithBLOBs;
import com.example.upc.service.ExamTrainMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author zcc
 * @date 2019/4/29 21:20
 */
@Service
public class ExamTrainMaterialServiceImpl implements ExamTrainMaterialService {

    @Autowired
    private ExamTrainMaterialMapper examTrainMaterialMapper;
    @Autowired
    private ExamTrainCourseMaterialMapper examTrainCourseMaterialMapper;

    @Override
    public PageResult<TrainMaterialParam> getPage(PageQuery pageQuery, ExamTrainMaterialSearchParam examTrainMaterialSearchParam) {
        int count=examTrainMaterialMapper.countList();
        if (count > 0) {
            List<TrainMaterialParam> examTrainMaterialList = examTrainMaterialMapper.getPage(pageQuery, examTrainMaterialSearchParam);
            PageResult<TrainMaterialParam> pageResult = new PageResult<>();
            pageResult.setData(examTrainMaterialList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<TrainMaterialParam> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public PageResult<TrainMaterialParam> getPageByType(PageQuery pageQuery, int typeId) {
        int count=examTrainMaterialMapper.countListByType(typeId);
        if (count > 0) {
            List<TrainMaterialParam> examTrainMaterialList = examTrainMaterialMapper.getPageByType(pageQuery,typeId);
            PageResult<TrainMaterialParam> pageResult = new PageResult<>();
            pageResult.setData(examTrainMaterialList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<TrainMaterialParam> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    @Transactional
    public void insert(ExamTrainMaterialWithBLOBs examTrainMaterial) {
        if(examTrainMaterialMapper.countByName(examTrainMaterial.getName())>0){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"存在相同名称教材");
        }
        examTrainMaterial.setOperateIp("125.125.125");
        examTrainMaterial.setOperator("操作人");
        examTrainMaterial.setOperateTime(new Date());
        examTrainMaterialMapper.insertSelective(examTrainMaterial);
    }

    @Override
    @Transactional
    public void update(ExamTrainMaterialWithBLOBs examTrainMaterial) {
        ExamTrainMaterialWithBLOBs before = examTrainMaterialMapper.selectByPrimaryKey(examTrainMaterial.getId());
        if(before == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待更新教材不存在");
        }
        examTrainMaterial.setOperateIp("125.125.125");
        examTrainMaterial.setOperator("操作人");
        examTrainMaterial.setOperateTime(new Date());
        examTrainMaterialMapper.updateByPrimaryKeySelective(examTrainMaterial);
    }

    @Override
    public void delete(int id) {
        ExamTrainMaterial examTrainMaterial = examTrainMaterialMapper.selectByPrimaryKey(id);
        if(examTrainMaterial==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待删除的不存在，无法删除");
        }
        examTrainMaterialMapper.deleteByPrimaryKey(id);
        examTrainCourseMaterialMapper.deleteByMaterialId(id);
    }
}
