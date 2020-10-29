package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.InspectBookParam;
import com.example.upc.controller.param.NameParam;
import com.example.upc.dao.InspectDailyBookMapper;
import com.example.upc.dataobject.InspectDailyBook;
import com.example.upc.service.InspectDailyBookService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zcc
 * @date 2019/7/7 1:41
 */
@Service
public class InspectDailyBookServiceImpl implements InspectDailyBookService {
    @Autowired
    private InspectDailyBookMapper inspectDailyBookMapper;

    @Override
    @Transactional
    public void changeDailyBooks(int dailyFoodId, List<InspectBookParam> inspectBookParamList) {
        if(inspectBookParamList==null){
            return;
        }
        if(inspectBookParamList.size()<1){
            return;
        }
        inspectDailyBookMapper.deleteByDailyFoodId(dailyFoodId);
        List<InspectDailyBook> inspectDailyBookList = Lists.newArrayList();
        for(InspectBookParam inspectBookParam : inspectBookParamList) {
            InspectDailyBook inspectDailyBook = new InspectDailyBook();
            inspectDailyBook.setDailyFoodId(dailyFoodId);
            inspectDailyBook.setBookConfId(inspectBookParam.getId());
            inspectDailyBook.setIsPublic(inspectBookParam.getIsPublic());
            inspectDailyBook.setRemark(inspectBookParam.getRemark());
            inspectDailyBook.setOperator("操作人");
            inspectDailyBook.setOperateIp("124.124.124");
            inspectDailyBook.setOperateTime(new Date());
            inspectDailyBookList.add(inspectDailyBook);
        }
        inspectDailyBookMapper.batchInsert(inspectDailyBookList);
    }

    @Override
    public List<InspectBookParam> getByDailyFoodId(int id) {
        return inspectDailyBookMapper.getByDailyFoodId(id);
    }

    @Override
    @Transactional
    public void insert(InspectDailyBook inspectDailyBook) {
        inspectDailyBook.setOperator("操作人");
        inspectDailyBook.setOperateIp("124.124.124");
        inspectDailyBook.setOperateTime(new Date());
        inspectDailyBookMapper.insertSelective(inspectDailyBook);
    }

    @Override
    @Transactional
    public void update(InspectDailyBook inspectDailyBook) {
        InspectDailyBook before = inspectDailyBookMapper.selectByPrimaryKey(inspectDailyBook.getId());
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待更新不存在");
        }
        inspectDailyBook.setOperator("操作人");
        inspectDailyBook.setOperateIp("124.124.124");
        inspectDailyBook.setOperateTime(new Date());
        inspectDailyBookMapper.updateByPrimaryKeySelective(inspectDailyBook);
    }

    @Override
    public void delete(int id) {
        InspectDailyBook before = inspectDailyBookMapper.selectByPrimaryKey(id);
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待删除不存在");
        }
        inspectDailyBookMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteByDailyId(int id) {
        inspectDailyBookMapper.deleteByDailyFoodId(id);
    }

    @Override
    public List<NameParam> getCheckBookNameList(int dailyFoodId, String typeUrl) {
        List<NameParam> nameList=new ArrayList<>();
        if(typeUrl.equals("/detailList")){
            nameList = inspectDailyBookMapper.getNameListByList(dailyFoodId);
        }else if(typeUrl.equals("/compulsoryMeasure")){
            nameList = inspectDailyBookMapper.getNameListByForce(dailyFoodId);
        }
       return nameList;
    }
}
