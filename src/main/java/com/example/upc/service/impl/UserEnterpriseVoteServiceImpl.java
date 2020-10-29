package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.searchParam.EnterpriseVoteSearchParam;
import com.example.upc.dao.UserEnterpriseProblemMapper;
import com.example.upc.dao.UserEnterpriseProblemRecordMapper;
import com.example.upc.dao.UserEnterpriseVoteMapper;
import com.example.upc.dataobject.UserEnterpriseProblem;
import com.example.upc.dataobject.UserEnterpriseProblemRecord;
import com.example.upc.dataobject.UserEnterpriseVote;
import com.example.upc.service.UserEnterpriseVoteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class UserEnterpriseVoteServiceImpl implements UserEnterpriseVoteService {
    @Autowired
    private UserEnterpriseVoteMapper userEnterpriseVoteMapper;
    @Autowired
    private UserEnterpriseProblemMapper userEnterpriseProblemMapper;
    @Autowired
    private UserEnterpriseProblemRecordMapper userEnterpriseProblemRecordMapper;

    @Override
    public Object insert(EnterpriseVoteSearchParam enterpriseVoteSearchParam) throws Exception{

        if(enterpriseVoteSearchParam.getEnterpriseId()==null || enterpriseVoteSearchParam.getUserId()==null){
            throw new BusinessException(EmBusinessError.UPDATE_ERROR2,"用户Id和企业id不可为空");
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        try {
            startDate  = simpleDateFormat.parse(enterpriseVoteSearchParam.getStartTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date endDate = new Date();
        endDate.setTime(startDate.getTime()+86399999);
        List<UserEnterpriseVote> judgeUserEnterpriseVote =userEnterpriseVoteMapper.selectByUserIdAndEnterpriseId(enterpriseVoteSearchParam.getUserId(),enterpriseVoteSearchParam.getEnterpriseId(),startDate,endDate);
        if(judgeUserEnterpriseVote.size()>0){
            throw new BusinessException(EmBusinessError.UPDATE_ERROR,"当天已经评价完，请勿重复评价");
        }else {
            UserEnterpriseVote userEnterpriseVote = new UserEnterpriseVote();
            BeanUtils.copyProperties(enterpriseVoteSearchParam,userEnterpriseVote);
            userEnterpriseVote.setOperationTime(new Date());
            float avg;
            avg =(userEnterpriseVote.getVote1()+userEnterpriseVote.getVote2()+userEnterpriseVote.getVote3()+
                    userEnterpriseVote.getVote4()+userEnterpriseVote.getVote5() )/5;
            userEnterpriseVote.setAverage(avg);
            userEnterpriseVoteMapper.insert(userEnterpriseVote);


            enterpriseVoteSearchParam.getProblemIdList().forEach(item ->{
                UserEnterpriseProblemRecord userEnterpriseProblemRecord =new UserEnterpriseProblemRecord();
                userEnterpriseProblemRecord.setProblemId(item);
                UserEnterpriseProblem name =userEnterpriseProblemMapper.selectByPrimaryKey(item);
                userEnterpriseProblemRecord.setProblemName(name.getProblemName());
                userEnterpriseProblemRecord.setProblemVoteId(userEnterpriseVote.getId());
                userEnterpriseProblemRecord.setOperationTime(new Date());
                userEnterpriseProblemRecordMapper.insert(userEnterpriseProblemRecord);
            });
        }
        return null;
    }

    @Override
    public Object getProblem() {
        List<UserEnterpriseProblem> problemsList;
        problemsList = userEnterpriseProblemMapper.getAllProblem();
        return  problemsList;
    }
}
