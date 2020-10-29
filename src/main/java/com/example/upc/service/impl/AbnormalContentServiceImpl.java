package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dao.AbnormalContentMapper;
import com.example.upc.dataobject.AbnormalContent;
import com.example.upc.service.AbnormalContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbnormalContentServiceImpl implements AbnormalContentService {
    @Autowired
    AbnormalContentMapper abnormalContentMapper;

    @Override
    public PageResult getPage(PageQuery pageQuery) {
        int count=abnormalContentMapper.countList();
        if (count > 0) {
            List<AbnormalContent> fpList = abnormalContentMapper.getPage(pageQuery);
            PageResult<AbnormalContent> pageResult = new PageResult<>();
            pageResult.setData(fpList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<AbnormalContent> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public List<AbnormalContent> getList() {
        List<AbnormalContent> list = abnormalContentMapper.getList();
        return list;
    }

    @Override
    public void insert(AbnormalContent abnormalContent) {
        if(checkExist(abnormalContent.getContent(), abnormalContent.getId())) {
            throw new BusinessException(EmBusinessError.DISHIES_TYPE);
        }

        AbnormalContent abnormalContent1 = new AbnormalContent();
        abnormalContent1.setContent(abnormalContent.getContent());
        abnormalContent1.setOperator("操作人");
        abnormalContent1.setOperatorIp("124.124.124");
        // TODO: sendEmail

        abnormalContentMapper.insertSelective(abnormalContent1);
    }

    @Override
    public void update(AbnormalContent abnormalContent) {
        if(checkExist(abnormalContent.getContent(), abnormalContent.getId())) {
            throw new BusinessException(EmBusinessError.DISHIES_TYPE);
        }

        AbnormalContent abnormalContent1 = new AbnormalContent();
        abnormalContent1.setId(abnormalContent.getId());
        abnormalContent1.setContent(abnormalContent.getContent());
        abnormalContent1.setOperator("操作人");
        abnormalContent1.setOperatorIp("124.124.124");

        // TODO: sendEmail

        abnormalContentMapper.updateByPrimaryKeySelective(abnormalContent1);
    }

    public boolean checkExist(String content,Integer id) {
        return abnormalContentMapper.countByType(content,id) > 0;
    }
}
