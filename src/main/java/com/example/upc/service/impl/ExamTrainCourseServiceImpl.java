package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.ExamCaTrainParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.param.TrainCourseParam;
import com.example.upc.controller.searchParam.ExamTrainCourseSearchParam;
import com.example.upc.controller.searchParam.ExamTrainMaterialSearchParam;
import com.example.upc.dao.ExamTrainCourseMapper;
import com.example.upc.dao.ExamTrainCourseMaterialMapper;
import com.example.upc.dao.SupervisionCaMapper;
import com.example.upc.dao.SysUserMapper;
import com.example.upc.dataobject.ExamTrainCourse;
import com.example.upc.dataobject.SupervisionCa;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.ExamTrainCourseMaterialService;
import com.example.upc.service.ExamTrainCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zcc
 * @date 2019/4/29 21:26
 */
@Service
public class ExamTrainCourseServiceImpl implements ExamTrainCourseService {
    @Autowired
    private ExamTrainCourseMapper examTrainCourseMapper;
    @Autowired
    private ExamTrainCourseMaterialService examTrainCourseMaterialService;
    @Autowired
    private ExamTrainCourseMaterialMapper examTrainCourseMaterialMapper;
    @Autowired
    private SupervisionCaMapper supervisionCaMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Override
    public PageResult<TrainCourseParam> getPage(PageQuery pageQuery, ExamTrainCourseSearchParam examTrainCourseSearchParam) {
        int count=examTrainCourseMapper.countList();
        if (count > 0) {
            List<TrainCourseParam> examTrainCourseList = examTrainCourseMapper.getPage(pageQuery, examTrainCourseSearchParam);
            PageResult<TrainCourseParam> pageResult = new PageResult<>();
            pageResult.setData(examTrainCourseList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<TrainCourseParam> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    @Transactional
    public void insert(ExamTrainCourse examTrainCourse, List<Integer> materialIds) {
        if(examTrainCourseMapper.countByName(examTrainCourse.getName())>0){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"存在相同名称课程");
        }
        examTrainCourse.setOperateIp("124.124.124");
        examTrainCourse.setOperator("操作人");
        examTrainCourse.setOperateTime(new Date());
        examTrainCourseMapper.insertSelective(examTrainCourse);
        examTrainCourseMaterialService.changeCourseMaterials(examTrainCourse.getId(),materialIds);
    }

    @Override
    @Transactional
    public void update(ExamTrainCourse examTrainCourse, List<Integer> materialIds) {
        ExamTrainCourse before = examTrainCourseMapper.selectByPrimaryKey(examTrainCourse.getId());
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待更新课程不存在");
        }
        examTrainCourse.setOperateIp("124.124.124");
        examTrainCourse.setOperator("操作人");
        examTrainCourse.setOperateTime(new Date());
        examTrainCourseMapper.updateByPrimaryKeySelective(examTrainCourse);
        examTrainCourseMaterialService.changeCourseMaterials(examTrainCourse.getId(),materialIds);
    }

    @Override
    public void delete(int id) {
        ExamTrainCourse examExam = examTrainCourseMapper.selectByPrimaryKey(id);
        if(examExam==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待删除的不存在，无法删除");
        }
        examTrainCourseMapper.deleteByPrimaryKey(id);
        examTrainCourseMaterialMapper.deleteByCourseId(id);
    }

    @Override
    public List<ExamCaTrainParam> getCaTrainList(SysUser sysUser) {
        SupervisionCa supervisionCa = supervisionCaMapper.selectByPrimaryKey(sysUser.getInfoId());
        return examTrainCourseMapper.getCaTrainList(supervisionCa.getWorkType(),sysUser.getId());
    }

    @Override
    public Map<String, Object> getCourseMaterialIds(int courseId, int caId) {
        Map<String,Object> map = new HashMap<>();
        map.put("course",examTrainCourseMapper.selectByPrimaryKey(courseId));
        map.put("list",examTrainCourseMaterialService.getCaListByCourseId(caId,courseId));
        return map;
    }

    @Override
    public Map<String, Object> getCourseIds(int caId) {
        Map<String,Object> map = new HashMap<>();
        SysUser sysUser =sysUserMapper.selectByPrimaryKey(caId);
        SupervisionCa supervisionCa = supervisionCaMapper.selectByPrimaryKey(sysUser.getInfoId());
        map.put("list",examTrainCourseMapper.getCaTrainList(supervisionCa.getWorkType(),caId));
        return map;
    }
}
