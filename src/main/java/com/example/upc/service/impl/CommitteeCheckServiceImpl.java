package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.common.ValidationResult;
import com.example.upc.common.ValidatorImpl;
import com.example.upc.controller.param.CommitteCheckOptCategoryParam;
import com.example.upc.controller.param.CommitteeCheckOptAnswerWithTopic;
import com.example.upc.controller.param.CommitteeCheckParam;
import com.example.upc.dao.CommitteCheckOptCategoryMapper;
import com.example.upc.dao.CommitteeAdditionalAnswerMapper;
import com.example.upc.dao.CommitteeCheckMapper;
import com.example.upc.dao.CommitteeCheckOptAnswerMapper;
import com.example.upc.dataobject.*;
import com.example.upc.service.CommitteeCheckService;
import com.example.upc.util.JsonToImageUrl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommitteeCheckServiceImpl implements CommitteeCheckService {
    @Autowired
    private CommitteeCheckMapper committeeCheckMapper;
    @Autowired
    private CommitteeAdditionalAnswerMapper committeeAdditionalAnswerMapper;
    @Autowired
    private CommitteeCheckOptAnswerMapper committeeCheckOptAnswerMapper;
    @Autowired
    private CommitteCheckOptCategoryMapper committeCheckOptCategoryMapper;
    @Autowired
    private ValidatorImpl validator;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insert( CommitteeCheckParam committeeCheckParam, SysUser sysUser){
        ValidationResult result = validator.validate(committeeCheckParam);
        if (result.isHasErrors()) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());
        }

        CommitteeCheck committeeCheck = new CommitteeCheck();
        CommitteeCheckOptAnswer committeeCheckOptAnswer = new CommitteeCheckOptAnswer();
        CommitteeAdditionalAnswer committeeAdditionalAnswer = new CommitteeAdditionalAnswer();
        BeanUtils.copyProperties(committeeCheckParam,committeeCheck);
//        BeanUtils.copyProperties(committeeCheckParam,committeeCheckOptAnswer);
        BeanUtils.copyProperties(committeeCheckParam,committeeAdditionalAnswer);

        committeeCheck.setEnterpriseId(sysUser.getInfoId());
        committeeCheckMapper.insertSelective(committeeCheck);
        int checkId = committeeCheck.getId();
        committeeAdditionalAnswer.setCommitteeCheckId(checkId);
        committeeAdditionalAnswerMapper.insertSelective(committeeAdditionalAnswer);

        for (CommitteCheckOptCategoryParam p1:committeeCheckParam.getCommitteCheckOptCategoryParamList()
             ) {
            for (CommitteeCheckOptAnswer p2:p1.getCommitteeCheckOptAnswerList()
                 ) {
                System.out.println(p2.getCommitteeCheckOptId()+p2.getAnswer());
                BeanUtils.copyProperties(p2,committeeCheckOptAnswer);
                committeeCheckOptAnswer.setCommitteeCheckId(checkId);
                committeeCheckOptAnswerMapper.insertSelective(committeeCheckOptAnswer);
            }
        }

    }

    @Override
    public List<CommitteeCheck> getByDate( CommitteeCheckParam committeeCheckParam, SysUser sysUser){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        committeeCheckParam.setEnterpriseId(sysUser.getInfoId());
        List<CommitteeCheck> committeeCheckList = committeeCheckMapper.getByDate(committeeCheckParam);
        committeeCheckList.forEach(item-> {
            item.setRepresentSign1(JsonToImageUrl.JSON2ImageUrl(item.getRepresentSign1()));
            item.setRepresentSign2(JsonToImageUrl.JSON2ImageUrl(item.getRepresentSign2()));
            item.setRepresentSign3(JsonToImageUrl.JSON2ImageUrl(item.getRepresentSign3()));
        });
        return committeeCheckList;
    }

    @Override
    public CommitteeCheckParam getByCheckId( CommitteeCheckParam committeeCheckParam, SysUser sysUser){
        List<CommitteCheckOptCategoryParam> committeCheckOptCategoryParamList = new ArrayList<>();
        CommitteeCheckParam committeeCheckParam1 = new CommitteeCheckParam();

        Integer checkId = committeeCheckParam.getCheckId();
        CommitteeCheck committeeCheck = committeeCheckMapper.selectByPrimaryKey(checkId);
        committeeCheck.setRepresentSign1(JsonToImageUrl.JSON2ImageUrl(committeeCheck.getRepresentSign1()));
        committeeCheck.setRepresentSign2(JsonToImageUrl.JSON2ImageUrl(committeeCheck.getRepresentSign2()));
        committeeCheck.setRepresentSign3(JsonToImageUrl.JSON2ImageUrl(committeeCheck.getRepresentSign3()));
        CommitteeAdditionalAnswer committeeAdditionalAnswer = committeeAdditionalAnswerMapper.getByCheckId(checkId);
        committeeAdditionalAnswer.setAccompanyPic1(JsonToImageUrl.JSON2ImageUrl(committeeAdditionalAnswer.getAccompanyPic1()));
        committeeAdditionalAnswer.setAccompanyPic2(JsonToImageUrl.JSON2ImageUrl(committeeAdditionalAnswer.getAccompanyPic2()));
        List<CommitteCheckOptCategory> committeCheckOptCategoryList = committeCheckOptCategoryMapper.getAll();
        for (CommitteCheckOptCategory item:committeCheckOptCategoryList
             ) {
            CommitteCheckOptCategoryParam committeCheckOptCategoryParam = new CommitteCheckOptCategoryParam();
            BeanUtils.copyProperties(item,committeCheckOptCategoryParam);
            List<CommitteeCheckOptAnswerWithTopic> committeeCheckOptAnswer = committeeCheckOptAnswerMapper.getByCheckId(checkId,item.getId());
            committeCheckOptCategoryParam.setCommitteeCheckOptAnswerList(committeeCheckOptAnswer);
            committeCheckOptCategoryParamList.add(committeCheckOptCategoryParam);
        }

        BeanUtils.copyProperties(committeeCheck,committeeCheckParam1);
        BeanUtils.copyProperties(committeeAdditionalAnswer,committeeCheckParam1);
        committeeCheckParam1.setCommitteCheckOptCategoryParamList(committeCheckOptCategoryParamList);
        return committeeCheckParam1;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByCheckId(CommitteeCheckParam committeeCheckParam, SysUser sysUser){
        Integer checkId = committeeCheckParam.getCheckId();
        committeeCheckMapper.deleteByPrimaryKey(checkId);
        committeeCheckOptAnswerMapper.deleteByCheckId(checkId);
        committeeAdditionalAnswerMapper.deleteByCheckId(checkId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateSign(@RequestBody CommitteeCheck committeeCheck){
        committeeCheckMapper.updateSign(committeeCheck);
    }

}
