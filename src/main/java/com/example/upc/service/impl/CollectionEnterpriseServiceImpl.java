package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.common.ValidationResult;
import com.example.upc.common.ValidatorImpl;
import com.example.upc.controller.param.CollectionEnterpriseParam;
import com.example.upc.controller.param.EnterpriseParam;
import com.example.upc.controller.param.NearEnterprise;
import com.example.upc.controller.searchParam.EnterpriseSearchParam;
import com.example.upc.dao.CollectionEnterpriseMapper;
import com.example.upc.dao.GridPointsMapper;
import com.example.upc.dataobject.CollectionEnterprise;
import com.example.upc.service.CollectionEnterpriseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Service
public class CollectionEnterpriseServiceImpl implements CollectionEnterpriseService {
    @Autowired
    CollectionEnterpriseMapper collectionEnterpriseMapper;
    @Autowired
    GridPointsMapper gridPointsMapper;
    @Autowired
    ValidatorImpl validator;

    @Override
    public List<NearEnterprise> selectByWeChatId(CollectionEnterpriseParam collectionEnterpriseParam) {
        ValidationResult result = validator.validate(collectionEnterpriseParam.getWeChatId());
        if (result.isHasErrors()) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());
        }
        List<Integer> idList = collectionEnterpriseMapper.selectEnterpriseId(collectionEnterpriseParam);
        if(idList.size()!=0){
            EnterpriseSearchParam enterpriseSearchParam = new EnterpriseSearchParam();
            enterpriseSearchParam.setIds(idList);
            return gridPointsMapper.getEnterpriseByParam(enterpriseSearchParam);
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insert(CollectionEnterpriseParam collectionEnterpriseParam) throws InvocationTargetException, IllegalAccessException {
        ValidationResult result = validator.validate(collectionEnterpriseParam);
        if (result.isHasErrors()) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());
        }

        CollectionEnterprise collectionEnterprise = new CollectionEnterprise();
        BeanUtils.copyProperties(collectionEnterpriseParam, collectionEnterprise);
        collectionEnterpriseMapper.insert(collectionEnterprise);
    }

    @Override
    public void delete(CollectionEnterpriseParam collectionEnterpriseParam) {
        ValidationResult result = validator.validate(collectionEnterpriseParam.getId());
        if (result.isHasErrors()) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());
        }
        collectionEnterpriseMapper.deleteByPrimaryKey(collectionEnterpriseParam.getId());
    }
}
