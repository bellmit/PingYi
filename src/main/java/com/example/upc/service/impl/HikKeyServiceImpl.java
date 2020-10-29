package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dao.HikKeyMapper;
import com.example.upc.dataobject.HikKey;
import com.example.upc.service.HikKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HikKeyServiceImpl implements HikKeyService {
    @Autowired
    private HikKeyMapper hikKeyMapper;

    @Override
    public HikKey selectTopOne(){
        HikKey hikKey = hikKeyMapper.selectTopOne();
        return hikKey;
    }

    @Override
    public PageResult getPage(PageQuery pageQuery) {
        int count=hikKeyMapper.countList();
        if (count > 0) {
            List<HikKey> fpList = hikKeyMapper.getPage(pageQuery);
            PageResult<HikKey> pageResult = new PageResult<>();
            pageResult.setData(fpList);
            pageResult.setTotal(count);
            return pageResult;
        }
        PageResult<HikKey> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public void insert(HikKey hikKey) {

        HikKey hikKey1 = new HikKey();
        hikKey1.setHostNumber(hikKey.getHostNumber());
        hikKey1.setAppkey(hikKey.getAppkey());
        hikKey1.setAppsecret(hikKey.getAppsecret());

        // TODO: sendEmail

        hikKeyMapper.insertSelective(hikKey1);
        //http://localhost:8080/ga/insert?department=1&name=1&sexy=1&job=1&type=1&idNumber=1&enforce=1&mobilePhone=1&officePhone=1&number=1&workPhone=1&category=1&photo=1&isStop=1
    }
    @Override
    public void delete(int id) {
        HikKey hikKey = hikKeyMapper.selectByPrimaryKey(id);
        if(hikKey==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"不存在，无法删除");
        }
        hikKeyMapper.deleteByPrimaryKey(id);
    }
    @Override
    public void update(HikKey hikKey) {
//        if(checkComNameExist(sysFoodProduce.getComName(), sysFoodProduce.getId())) {
//            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"企业名称已被占用");
//        }

        HikKey hikKey1 = new HikKey();
        hikKey1.setId(hikKey.getId());
        hikKey1.setHostNumber(hikKey.getHostNumber());
        hikKey1.setAppkey(hikKey.getAppkey());
        hikKey1.setAppsecret(hikKey.getAppsecret());

        // TODO: sendEmail

        hikKeyMapper.updateByPrimaryKeySelective(hikKey1);
    }
}
