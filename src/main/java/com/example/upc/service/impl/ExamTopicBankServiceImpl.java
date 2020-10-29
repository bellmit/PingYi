package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.ExamTopicSearchParam;
import com.example.upc.dao.ExamSubjectTopicMapper;
import com.example.upc.dao.ExamTopicBankMapper;
import com.example.upc.dataobject.ExamTopicBank;
import com.example.upc.service.ExamTopicBankService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zcc
 * @date 2019/4/29 20:19
 */
@Service
public class ExamTopicBankServiceImpl implements ExamTopicBankService {
    @Autowired
    private ExamTopicBankMapper examTopicBankMapper;
    @Autowired
    private ExamSubjectTopicMapper examSubjectTopicMapper;

    @Override
    public PageResult<ExamTopicBank> getPage(PageQuery pageQuery, ExamTopicSearchParam examTopicSearchParam) {
        int count=examTopicBankMapper.countList();
        if (count > 0) {
            List<ExamTopicBank> examTopicBankList = examTopicBankMapper.getPage(pageQuery,examTopicSearchParam);
            PageResult<ExamTopicBank> pageResult = new PageResult<>();
            pageResult.setData(examTopicBankList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<ExamTopicBank> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public List<ExamTopicBank> getList() {
        return examTopicBankMapper.getList();
    }

    @Override
    @Transactional
    public void insert(ExamTopicBank examTopicBank) {
      if(examTopicBankMapper.countByTitle(examTopicBank.getTitle())>0){
          throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"存在相同题目");
      }
      examTopicBank.setOperateIp("124.124.124");
      examTopicBank.setOperateTime(new Date());
      examTopicBank.setOperator("操作人");
      examTopicBankMapper.insertSelective(examTopicBank);
    }

    @Override
    @Transactional
    public void update(ExamTopicBank examTopicBank) {
        ExamTopicBank before = examTopicBankMapper.selectByPrimaryKey(examTopicBank.getId());
        if(before==null){
          throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待更新题目不存在");
        }
        examTopicBank.setOperateIp("124.124.124");
        examTopicBank.setOperateTime(new Date());
        examTopicBank.setOperator("操作人");
        examTopicBankMapper.updateByPrimaryKeySelective(examTopicBank);
    }

    @Override
    @Transactional
    public void delete(int id) {
        ExamTopicBank examTopicBank = examTopicBankMapper.selectByPrimaryKey(id);
        if(examTopicBank==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待删除的不存在，无法删除");
        }
        examTopicBankMapper.deleteByPrimaryKey(id);
        examSubjectTopicMapper.deleteByTopicId(id);
    }
}
