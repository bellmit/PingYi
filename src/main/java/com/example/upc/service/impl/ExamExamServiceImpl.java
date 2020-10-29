package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.*;
import com.example.upc.controller.searchParam.TrainPersonSearchParam;
import com.example.upc.dao.*;
import com.example.upc.dataobject.*;
import com.example.upc.service.*;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zcc
 * @date 2019/4/29 20:42
 */
@Service
public class ExamExamServiceImpl implements ExamExamService {
    @Autowired
    private ExamExamMapper examExamMapper;
    @Autowired
    private ExamSubjectTopicService examSubjectTopicService;
    @Autowired
    private ExamSubjectMapper examSubjectMapper;
    @Autowired
    private ExamSubmitService examSubmitService;
    @Autowired
    private ExamCaExamMapper examCaExamMapper;
    @Autowired
    private ExamTrainCourseMaterialService examTrainCourseMaterialService;
    @Autowired
    private SupervisionCaMapper supervisionCaMapper;
    @Autowired
    private ExamTrainCaMaterialMapper examTrainCaMaterialMapper;
    @Override
    public PageResult<ExamExamParam> getPage(PageQuery pageQuery,ExamExamParam examExamParam) {
        int count=examExamMapper.countList();
        if (count > 0) {
            List<ExamExamParam> examExamList = examExamMapper.getPage(pageQuery,examExamParam);
            PageResult<ExamExamParam> pageResult = new PageResult<>();
            pageResult.setData(examExamList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<ExamExamParam> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    @Transactional
    public void insert(ExamExam examExam) {
        if(examExamMapper.countByName(examExam.getName())>0){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"存在相同名称考试");
        }
        examExam.setOperateTime(new Date());
        examExam.setOperatorIp("124.124.124");
        examExam.setOperator("操作人");
        examExamMapper.insertSelective(examExam);
    }

    @Override
    @Transactional
    public void update(ExamExam examExam) {
        ExamExam before = examExamMapper.selectByPrimaryKey(examExam.getId());
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待更新考试不存在");
        }
        examExam.setOperateTime(new Date());
        examExam.setOperatorIp("124.124.124");
        examExam.setOperator("操作人");
        examExamMapper.updateByPrimaryKeySelective(examExam);
    }

    @Override
    public void delete(int id) {
        ExamExam examExam = examExamMapper.selectByPrimaryKey(id);
        if(examExam==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待删除的不存在，无法删除");
        }
        examExamMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Map<String, Object> getExamTopicList(CaTopicParam caTopicParam) {
        List<ExamCaTopic>  examCaTopicList = examSubjectTopicService.getCaListBySubject(caTopicParam);
        ExamSubject examSubject = examSubjectMapper.selectByPrimaryKey(caTopicParam.getSubjectId());
        Map<String, Object> map = Maps.newHashMap();
        map.put("list",examCaTopicList);
        map.put("subject",examSubject);
        return map;
    }

    @Override
    public List<ExamCaExamParam> getWorkTypeExamList(SysUser sysUser) {
        SupervisionCa supervisionCa = supervisionCaMapper.selectByPrimaryKey(sysUser.getInfoId());
        List<ExamCaExam> examCaExamList = examCaExamMapper.getByCaId(sysUser.getId());
        List<ExamCaExamParam> examExamList = examExamMapper.getByWorkType(supervisionCa.getWorkType());
        List<ExamCaExam> examPassList= examCaExamList.stream().filter(examCaExam -> examCaExam.getExamResult()==1).collect(Collectors.toList());
        List<ExamCaExamParam> newExamList = examExamList.stream().map(examExam -> {
            if(checkIfTrain(sysUser.getId())){
                examExam.setRemark("未完成培训");
            }
            else if(examPassList.size()>0){
                examExam.setRemark("已完成考试");
                examExam.setExamCaId(examPassList.get(0).getId());
            }else {
                examExam.setRemark("点击进入考试");
            }
            return examExam;
        }).collect(Collectors.toList());
        return newExamList;
    }


    //判断人员的培训课时是否达到40
    private boolean checkIfTrain(int caId) {
        if ((examTrainCaMaterialMapper.selectByCaId(caId))<10) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkTrain(int courseId,int caId){
        List<TrainCaMaterialParam> trainCaMaterialParamList = examTrainCourseMaterialService.getCaListByCourseId(caId,courseId);
        if(trainCaMaterialParamList.stream().filter(trainCaMaterialParam -> trainCaMaterialParam.getCompletionRate()!=1.0).collect(Collectors.toList()).size()>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    @Transactional
    public void changeCaExam(int caId, int examId,ExamSubject examSubject, List<ExamCaTopic> examCaTopicList) {
        float totalScore = examCaTopicList.stream().filter(examCaTopic -> CollectionUtils.isEmpty(CheckAnswer(examCaTopic.getAnswer(),examCaTopic.getCheck()))).collect(Collectors.toList())
                .stream()
                .map(ExamCaTopic::getScore).reduce((i, j) -> i + j).get();
//        examCaExamMapper.deleteByCaIdAndExamId(caId,examId);
        ExamCaExam examCaExam = new ExamCaExam();
        examCaExam.setCaId(caId);
        examCaExam.setExamId(examId);
        examCaExam.setExamScore(totalScore);
        examCaExam.setExamResult(totalScore>examSubject.getQualifiedScore()?1:0);
        examCaExam.setExamDate(new Date());
        examCaExam.setOperator("操作人");
        examCaExam.setOperateIp("124.124.124");
        examCaExam.setOperateTime(new Date());
        examCaExamMapper.insertSelective(examCaExam);

        Integer examCaId=examCaExam.getId();
        examSubmitService.changeCaSubmit(caId,examCaId,examCaTopicList);
    }

    private Set<String>  CheckAnswer(String str1,String str2)
    {
        Set<String> set = new HashSet<>(Arrays.asList(str1.split(",")));
        Set<String> set2 = new HashSet<>(Arrays.asList(str2.split(",")));
        set.removeAll(set2);
        return set;
    }
}
