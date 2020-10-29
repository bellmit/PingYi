package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.*;
import com.example.upc.controller.searchParam.ComplaintEnterpriseSearchParam;
import com.example.upc.controller.searchParam.ComplaintRecordSearchParam;
import com.example.upc.dao.ComplaintRecordMapper;
import com.example.upc.dao.SupervisionEnterpriseMapper;
import com.example.upc.dao.SupervisionGaMapper;
import com.example.upc.dataobject.ComplaintRecord;
import com.example.upc.dataobject.SupervisionGa;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.ComplaintRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ComplaintRecordServiceImpl implements ComplaintRecordService {
    @Autowired
    ComplaintRecordMapper complaintRecordMapper;
    @Autowired
    SupervisionGaMapper supervisionGaMapper;
    @Autowired
    SupervisionEnterpriseMapper supervisionEnterpriseMapper;

    @Override
    public PageResult getPage(PageQuery pageQuery,SysUser sysUser, ComplaintRecordSearchParam complaintRecordSearchParam) {
        SupervisionGa supervisionGa = supervisionGaMapper.selectByPrimaryKey(sysUser.getInfoId());
        int count=complaintRecordMapper.countList(supervisionGa.getDepartment(),supervisionGa.getDepartment(),sysUser.getInfoId(),sysUser.getUsername(),complaintRecordSearchParam);
        if (count > 0) {
            List<ComplaintRecordParam> fpList = complaintRecordMapper.getPage(pageQuery, supervisionGa.getDepartment(),supervisionGa.getDepartment(),sysUser.getInfoId(),sysUser.getUsername(),complaintRecordSearchParam);
            PageResult<ComplaintRecordParam> pageResult = new PageResult<>();
            pageResult.setData(fpList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<ComplaintRecordParam> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public PageResult getPageAdmin (PageQuery pageQuery, ComplaintRecordSearchParam complaintRecordSearchParam) {

        int count=complaintRecordMapper.countListAdmin(complaintRecordSearchParam);
        if (count > 0) {
            List<ComplaintRecordParam> fpList = complaintRecordMapper.getPageAdmin(pageQuery, complaintRecordSearchParam);
            PageResult<ComplaintRecordParam> pageResult = new PageResult<>();
            pageResult.setData(fpList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<ComplaintRecordParam> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public PageResult getPageEnterprise(PageQuery pageQuery, ComplaintEnterpriseSearchParam complaintEnterpriseSearchParam) {

        int count=supervisionEnterpriseMapper.countListAllEnterprise(complaintEnterpriseSearchParam);
        if (count > 0) {
            List<EnterpriseListResultParam> fpList = supervisionEnterpriseMapper.getAllEnterprise(pageQuery,complaintEnterpriseSearchParam);
            PageResult<EnterpriseListResultParam> pageResult = new PageResult<>();
            pageResult.setData(fpList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<EnterpriseListResultParam> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public void insert(ComplaintRecord complaintRecord, SysUser sysUser) {
        SupervisionGa supervisionGa = supervisionGaMapper.selectByPrimaryKey(sysUser.getInfoId());

        ComplaintRecord complaintRecord1 = new ComplaintRecord();
        complaintRecord1.setRecordPerson(sysUser.getUsername());
        complaintRecord1.setRecordNumber(complaintRecord.getRecordNumber());
        if (supervisionGa!=null)//非管理员
        {
        complaintRecord1.setRecordDept(supervisionGa.getDepartment());
        }
        if (supervisionGa==null)//管理员Ga是没有的
        {
            complaintRecord1.setRecordDept(0);
        }
        complaintRecord1.setRecord(complaintRecord.getRecord());
        complaintRecord1.setIncomingTime(complaintRecord.getIncomingTime());
        complaintRecord1.setDeadTime(complaintRecord.getDeadTime());
        complaintRecord1.setFinishTime(complaintRecord.getFinishTime());
        complaintRecord1.setIncomingType(complaintRecord.getIncomingType());
        complaintRecord1.setInformationType(complaintRecord.getInformationType());
        complaintRecord1.setEmergencyType(complaintRecord.getEmergencyType());
        complaintRecord1.setComplaintPerson(complaintRecord.getComplaintPerson());
        complaintRecord1.setContact(complaintRecord.getContact());
        complaintRecord1.setSecrecy(complaintRecord.getSecrecy());
        complaintRecord1.setReply(complaintRecord.getReply());
        complaintRecord1.setEnterprise(complaintRecord.getEnterprise());
        complaintRecord1.setArea(complaintRecord.getArea());
        complaintRecord1.setAddress(complaintRecord.getAddress());
        complaintRecord1.setProblemOne(complaintRecord.getProblemOne());
        complaintRecord1.setProblemTwo(complaintRecord.getProblemTwo());
        complaintRecord1.setIncomingContent(complaintRecord.getIncomingContent());
        complaintRecord1.setSuggestion(complaintRecord.getSuggestion());
        if (complaintRecord.getStep()==1)
        {
            complaintRecord1.setStep(1);
            complaintRecord1.setDept(complaintRecord.getDept());
            complaintRecord1.setLeader(0);
            complaintRecord1.setInstructTime(new Date());
            complaintRecord1.setInstruction(null);
            complaintRecord1.setResult(null);
            complaintRecord1.setState(1);//已受理。未办理
            complaintRecord1.setStateEx(1);
        }
        else if (complaintRecord.getStep()==2)
        {
            complaintRecord1.setStep(2);
            complaintRecord1.setDept(0);
            complaintRecord1.setLeader(0);
            complaintRecord1.setInstructTime(new Date());
            complaintRecord1.setInstruction(null);
            complaintRecord1.setResult(complaintRecord.getResult());
            complaintRecord1.setState(2);//已办结
            complaintRecord1.setStateEx(2);
        }
        else if (complaintRecord.getStep()==3)
        {
            complaintRecord1.setStep(3);
            complaintRecord1.setDept(0);
            complaintRecord1.setLeader(complaintRecord.getLeader());
            complaintRecord1.setInstructTime(new Date());
            complaintRecord1.setInstruction(null);
            complaintRecord1.setResult(null);
            complaintRecord1.setState(3);//已受理，领导未批示
            complaintRecord1.setStateEx(1);
        }
        complaintRecord1.setOkTime(new Date());
        complaintRecord1.setDocument("");
        complaintRecord1.setOperator("操作人");
        complaintRecord1.setOperatorIp("124.124.124");
        complaintRecord1.setOperatorTime(new Date());
        // TODO: sendEmail

        complaintRecordMapper.insertSelective(complaintRecord1);
    }
    @Override
    public void delete(int id) {
        ComplaintRecord complaintRecord = complaintRecordMapper.selectByPrimaryKey(id);
        if(complaintRecord==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"不存在，无法删除");
        }
        complaintRecordMapper.deleteByPrimaryKey(id);
    }
    @Override
    public void update(ComplaintRecord complaintRecord, SysUser sysUser) {
        SupervisionGa supervisionGa = supervisionGaMapper.selectByPrimaryKey(sysUser.getInfoId());

        ComplaintRecord complaintRecord1 = new ComplaintRecord();
        complaintRecord1.setId(complaintRecord.getId());
        complaintRecord1.setRecordPerson(sysUser.getUsername());
        complaintRecord1.setRecordNumber(complaintRecord.getRecordNumber());
        complaintRecord1.setRecord(complaintRecord.getRecord());
        complaintRecord1.setRecordDept(supervisionGa.getDepartment());
        complaintRecord1.setIncomingTime(complaintRecord.getIncomingTime());
        complaintRecord1.setDeadTime(complaintRecord.getDeadTime());
        complaintRecord1.setFinishTime(complaintRecord.getFinishTime());
        complaintRecord1.setIncomingType(complaintRecord.getIncomingType());
        complaintRecord1.setInformationType(complaintRecord.getInformationType());
        complaintRecord1.setEmergencyType(complaintRecord.getEmergencyType());
        complaintRecord1.setComplaintPerson(complaintRecord.getComplaintPerson());
        complaintRecord1.setContact(complaintRecord.getContact());
        complaintRecord1.setSecrecy(complaintRecord.getSecrecy());
        complaintRecord1.setReply(complaintRecord.getReply());
        complaintRecord1.setEnterprise(complaintRecord.getEnterprise());
        complaintRecord1.setArea(complaintRecord.getArea());
        complaintRecord1.setAddress(complaintRecord.getAddress());
        complaintRecord1.setProblemOne(complaintRecord.getProblemOne());
        complaintRecord1.setProblemTwo(complaintRecord.getProblemTwo());
        complaintRecord1.setIncomingContent(complaintRecord.getIncomingContent());
        complaintRecord1.setSuggestion(complaintRecord.getSuggestion());
        complaintRecord1.setStep(complaintRecord.getStep());
        if (complaintRecord.getStep()==1)
        {
            complaintRecord1.setDept(complaintRecord.getDept());
            complaintRecord1.setLeader(0);
            complaintRecord1.setInstructTime(new Date());
            complaintRecord1.setInstruction(null);
            complaintRecord1.setResult(complaintRecord.getResult());
            complaintRecord1.setState(2);//已办结
            complaintRecord1.setStateEx(2);
        }
        else if (complaintRecord.getStep()==3)
        {
                complaintRecord1.setDept(complaintRecord.getDept());
                complaintRecord1.setLeader(complaintRecord.getLeader());
                complaintRecord1.setInstructTime(complaintRecord.getInstructTime());
                complaintRecord1.setInstruction(complaintRecord.getInstruction());
            if (complaintRecord.getState()!=4) {
                complaintRecord1.setResult(null);
                complaintRecord1.setState(4);//已受理，领导已批示但未办结
                complaintRecord1.setStateEx(1);
            }
            else if (complaintRecord.getState()==4) {
                complaintRecord1.setResult(complaintRecord.getResult());
                complaintRecord1.setState(2);//已办结
                complaintRecord1.setStateEx(2);
            }
        }
        complaintRecord1.setOkTime(new Date());
        complaintRecord1.setDocument("");
        complaintRecord1.setOperator("操作人");
        complaintRecord1.setOperatorIp("124.124.124");
        complaintRecord1.setOperatorTime(new Date());

        // TODO: sendEmail

        complaintRecordMapper.updateByPrimaryKeySelective(complaintRecord1);
    }

    @Override
    public ComplaintRecord getRecordById(Integer id)
    {
        ComplaintRecord complaintRecord = complaintRecordMapper.getRecordById(id);
        return complaintRecord;
    }

    @Override
    public void fail() {
        throw new BusinessException(EmBusinessError.USER_NO);
    }

}
