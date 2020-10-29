package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dao.QuickCheckProductTypeMapper;
import com.example.upc.dataobject.QuickCheckProductType;
import com.example.upc.service.QuickCheckProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class QuickCheckProductTypeServiceImpl implements QuickCheckProductTypeService {
    @Autowired
    QuickCheckProductTypeMapper quickCheckProductTypeMapper;

    @Override
    public PageResult getPage(PageQuery pageQuery) {
        int count=quickCheckProductTypeMapper.countList();
        if (count > 0) {
            List<QuickCheckProductType> fpList = quickCheckProductTypeMapper.getPage(pageQuery);
            PageResult<QuickCheckProductType> pageResult = new PageResult<>();
            pageResult.setData(fpList);
            pageResult.setTotal(count);
            return pageResult;
        }
        PageResult<QuickCheckProductType> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public void insert(QuickCheckProductType quickCheckProductType) {
        if(checkTypeExist(quickCheckProductType.getType(), quickCheckProductType.getId())) {
            throw new BusinessException(EmBusinessError.DISHIES_TYPE);
        }

        QuickCheckProductType quickCheckProductType1 = new QuickCheckProductType();
        quickCheckProductType1.setType(quickCheckProductType.getType());
        quickCheckProductType1.setOperator("操作人");
        quickCheckProductType1.setOperatorIp("124.124.124");
        quickCheckProductType1.setOperatorTime(new Date());
        // TODO: sendEmail

        quickCheckProductTypeMapper.insertSelective(quickCheckProductType1);
        //http://localhost:8080/ga/insert?department=1&name=1&sexy=1&job=1&type=1&idNumber=1&enforce=1&mobilePhone=1&officePhone=1&number=1&workPhone=1&category=1&photo=1&isStop=1
    }
    @Override
    public void delete(int id) {
        QuickCheckProductType quickCheckProductType = quickCheckProductTypeMapper.selectByPrimaryKey(id);
        if(quickCheckProductType==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"不存在，无法删除");
        }
        quickCheckProductTypeMapper.deleteByPrimaryKey(id);
    }
    @Override
    public void update(QuickCheckProductType quickCheckProductType) {
        if(checkTypeExist(quickCheckProductType.getType(), quickCheckProductType.getId())) {
            throw new BusinessException(EmBusinessError.DISHIES_TYPE);
        }

        QuickCheckProductType quickCheckProductType1 = new QuickCheckProductType();
        quickCheckProductType1.setId(quickCheckProductType.getId());
        quickCheckProductType1.setType(quickCheckProductType.getType());
        quickCheckProductType1.setOperator("操作人");
        quickCheckProductType1.setOperatorIp("124.124.124");
        quickCheckProductType1.setOperatorTime(new Date());

        // TODO: sendEmail

        quickCheckProductTypeMapper.updateByPrimaryKeySelective(quickCheckProductType1);
    }

    public boolean checkTypeExist(String type,Integer id) {
        return quickCheckProductTypeMapper.countByType(type,id) > 0;
    }
}
