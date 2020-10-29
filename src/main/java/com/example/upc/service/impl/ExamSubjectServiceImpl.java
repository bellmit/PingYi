package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.*;
import com.example.upc.controller.searchParam.ExamSubjectSearchParam;
import com.example.upc.dao.*;
import com.example.upc.dataobject.*;
import com.example.upc.service.ExamSubjectService;
import com.example.upc.service.ExamSubjectTopicService;
import com.example.upc.service.ExamSubmitService;
import com.example.upc.service.ExamTrainCourseMaterialService;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zcc
 * @date 2019/4/29 20:54
 */
@Service
public class ExamSubjectServiceImpl implements ExamSubjectService {
    @Autowired
    private ExamSubjectMapper examSubjectMapper;
    @Autowired
    private SysIndustryMapper sysIndustryMapper;
    @Autowired
    private SysWorkTypeMapper sysWorkTypeMapper;
    @Autowired
    private ExamSubjectTopicMapper examSubjectTopicMapper;
    @Autowired
    private ExamTopicBankMapper examTopicBankMapper;
    @Autowired
    private ExamCaExamMapper examCaExamMapper;
    @Autowired
    private SupervisionCaMapper supervisionCaMapper;
    @Autowired
    private ExamTrainCaMaterialMapper examTrainCaMaterialMapper;

    @Autowired
    private ExamSubjectTopicService examSubjectTopicService;
    @Autowired
    private ExamSubmitService examSubmitService;
    @Autowired
    private ExamTrainCourseMaterialService examTrainCourseMaterialService;

    @Autowired
    private ExamExamMapper examExamMapper;
    @Override
    public PageResult<ExamSubjectSearchParam> getPage(PageQuery pageQuery, ExamSubjectSearchParam examSubjectSearchParam) {
        int count=examSubjectMapper.countList();
        if (count > 0) {
            List<ExamSubjectSearchParam> examSubjectSearchParamList = examSubjectMapper.getPage(pageQuery, examSubjectSearchParam);
            PageResult<ExamSubjectSearchParam> pageResult = new PageResult<>();
            pageResult.setData(examSubjectSearchParamList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<ExamSubjectSearchParam> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    @Transactional
    public void insert(ExamSubject examSubject) {
        if(examSubjectMapper.countByIndustryAndWorkType(examSubject.getIndustry(),examSubject.getWorkType())>0){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"存在相同科目");
        }

        examSubject.setOperator("操作人");
        examSubject.setOperatorIp("14.124.124");
        examSubject.setOperateTime(new Date());

        examSubjectMapper.insertSelective(examSubject);

        List<ExamTopicBank> examTopicBankList=examTopicBankMapper.selectRandomByNum(examSubject);

        examSubjectTopicService.changeSubjectTopics(examSubject,examTopicBankList);
    }

    @Override
    @Transactional
    public void update(ExamSubject examSubject) {
        ExamSubject before = examSubjectMapper.selectByPrimaryKey(examSubject.getId());
        if(before == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待更新科目不存在");
        }
        examSubject.setOperator("操作人");
        examSubject.setOperatorIp("14.124.124");
        examSubject.setOperateTime(new Date());
        examSubjectMapper.updateByPrimaryKeySelective(examSubject);
        List<ExamTopicBank> examTopicBankList=examTopicBankMapper.selectRandomByNum(examSubject);

        examSubjectTopicMapper.deleteBySubjectId(examSubject.getId());

        examSubjectTopicService.changeSubjectTopics(examSubject,examTopicBankList);
    }

    @Override
    @Transactional
    public void delete(int id) {
        ExamSubject examSubject = examSubjectMapper.selectByPrimaryKey(id);
        if(examSubject==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待删除的不存在，无法删除");
        }
        examSubjectMapper.deleteByPrimaryKey(id);
        examSubjectTopicService.deleteBySubjectId(id);
    }

    @Override
    public List<Integer> getSubjectTopicIds(int id) {
        return examSubjectTopicMapper.getTopicIdListBySubjectId(id);
    }

    @Override
    public TopicNumParam getNum(ExamSubjectSearchParam examSubjectSearchParam) {
        return examTopicBankMapper.getNum(examSubjectSearchParam);
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
    public List<ExamSubjectParam> getWorkTypeExamList(SysUser sysUser) {
        SupervisionCa supervisionCa = supervisionCaMapper.selectByPrimaryKey(sysUser.getInfoId());
        List<ExamCaExam> examCaExamList = examCaExamMapper.getByCaId(sysUser.getId());

        List<ExamSubjectParam> examSubjectList = examSubjectMapper.getByWorkType(supervisionCa.getWorkType());

        List<ExamCaExam> examPassList= examCaExamList.stream().filter(examCaExam -> examCaExam.getExamResult()==1).collect(Collectors.toList());

        List<ExamSubjectParam> newExamList = examSubjectList.stream().map(examSubject -> {
            if(checkIfTrain(sysUser.getId())){
                examSubject.setRemark("未完成培训");
            }
            else if(examPassList.size()>0){
                examSubject.setRemark("已完成考试");
                examSubject.setExamCaId(examPassList.get(0).getId());
            }else {
                examSubject.setRemark("点击进入考试");
            }
            return examSubject;
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
    @Transactional(rollbackFor = Exception.class)
    public void changeCaExam(int caId,ExamSubject examSubject, List<ExamCaTopic> examCaTopicList) {
        float totalScore = examCaTopicList.stream().filter(examCaTopic -> CollectionUtils.isEmpty(CheckAnswer(examCaTopic.getAnswer(),examCaTopic.getCheck()))).collect(Collectors.toList())
                .stream()
                .map(ExamCaTopic::getScore).reduce((i, j) -> i + j).get();
//        examCaExamMapper.deleteByCaIdAndExamId(caId,examId);

        ExamCaExam examCaExam = new ExamCaExam();
        examCaExam.setCaId(caId);
        examCaExam.setExamId(examSubject.getId());
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

    private Set<String> CheckAnswer(String str1, String str2)
    {
        Set<String> set = new HashSet<>(Arrays.asList(str1.split(",")));
        if(str2==null||str2.isEmpty())
        {
            return set;
        }
        else
        {
            Set<String> set2 = new HashSet<>(Arrays.asList(str2.split(",")));
            set.removeAll(set2);
            return set;
        }
    }
}
