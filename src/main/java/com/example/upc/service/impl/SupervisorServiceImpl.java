package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.param.SupervisorParam;
//import com.example.upc.dao.SysFoodBusinessMapper;
import com.example.upc.dao.SysSupervisorMapper;
//import com.example.upc.dataobject.SysFoodBusiness;
import com.example.upc.dataobject.SysSupervisor;
import com.example.upc.service.SupervisorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class SupervisorServiceImpl implements SupervisorService {
    @Autowired
    SysSupervisorMapper sysSupervisorMapper;
    //@Autowired
   //SysFoodBusinessMapper sysFoodBusinessMapper;

    @Override
    public SysSupervisor selectAllByTelephone (String telephone) throws BusinessException {
        return sysSupervisorMapper.selectAllByTelephone(telephone);
    }

    @Override
    public PageResult getPage(PageQuery pageQuery) {
        int count=sysSupervisorMapper.countList();
        if (count > 0) {
            List<SysSupervisor> spList = sysSupervisorMapper.getPage(pageQuery);
            PageResult<SysSupervisor> pageResult = new PageResult<>();
            pageResult.setData(spList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<SysSupervisor> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    @Transactional
    public void insert(SysSupervisor sysSupervisor) {
//        if(checkNameExist(sysSupervisor.getName(), sysSupervisor.getId())) {
//            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"姓名已被占用");
//        }

        SysSupervisor supervisor = new SysSupervisor();
        supervisor.setUnit(sysSupervisor.getUnit());
        supervisor.setDepartment(sysSupervisor.getDepartment());
        supervisor.setName(sysSupervisor.getName());
        supervisor.setSex(sysSupervisor.getSex());
        supervisor.setDuties(sysSupervisor.getDuties());
//        supervisor.setForm(sysSupervisor.getForm());
//        supervisor.setIdNumber(sysSupervisor.getIdNumber());
        supervisor.setNumber(sysSupervisor.getNumber());
        supervisor.setTelephone(sysSupervisor.getTelephone());
//        supervisor.setOfficePhone(sysSupervisor.getOfficePhone());
//        supervisor.setMold(sysSupervisor.getMold());
//        supervisor.setWorkMobilePhone(sysSupervisor.getWorkMobilePhone());
        supervisor.setType(sysSupervisor.getType());
        supervisor.setOperator("操作人");
        supervisor.setOperatorIp("124.124.124");
        supervisor.setOperatorTime(new Date());

        // TODO: sendEmail

        sysSupervisorMapper.insertSelective(supervisor);
        //http://localhost:8080/supervisor/insert?unit=%E9%9D%92%E5%B2%9B%E5%B8%82%E9%BB%84%E5%B2%9B%E5%8C%BA&department=%E7%A7%91%E5%91%98&name=%E6%9D%8E%E6%98%82&sex=%E5%A5%B3&number=11111&telephone=123456&officePhone=654321&duties=%E7%9B%91%E7%AE%A1&type=%E9%9D%92%E5%B2%9B%E5%B8%82%E9%BB%84%E5%B2%9B%E5%8C%BA
        //http://localhost:8080/ga/insert?department=1&name=1&sexy=1&job=1&type=1&idNumber=1&enforce=1&mobilePhone=1&officePhone=1&number=1&workPhone=1&category=1&photo=1&isStop=1
    }
    @Override
    public void delete(int sId) {
        SysSupervisor supervisor = sysSupervisorMapper.selectByPrimaryKey(sId);
        if(supervisor==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"不存在，无法删除");
        }
        sysSupervisorMapper.deleteByPrimaryKey(sId);
    }

    @Override
    @Transactional
    public void update(SupervisorParam supervisorParam) {
//        if(checkNameExist(sysSupervisor.getName(), sysSupervisor.getId())) {
//            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"姓名已被占用");
//        }

        SysSupervisor sysSupervisor = new SysSupervisor();
        sysSupervisor.setId(supervisorParam.getId());
        sysSupervisor.setUnit(supervisorParam.getUnit());
        sysSupervisor.setDepartment(supervisorParam.getDepartment());
        sysSupervisor.setName(supervisorParam.getName());
        sysSupervisor.setSex(supervisorParam.getSex());
        sysSupervisor.setDuties(supervisorParam.getDuties());
        sysSupervisor.setForm(supervisorParam.getForm());
        sysSupervisor.setIdNumber(supervisorParam.getIdNumber());
        sysSupervisor.setNumber(supervisorParam.getNumber());
        sysSupervisor.setTelephone(supervisorParam.getTelephone());
        sysSupervisor.setOfficePhone(supervisorParam.getOfficePhone());
        sysSupervisor.setMold(supervisorParam.getMold());
        sysSupervisor.setWorkMobilePhone(supervisorParam.getWorkMobilePhone());
        sysSupervisor.setType(supervisorParam.getType());
        sysSupervisor.setOperator("操作人");
        sysSupervisor.setOperatorIp("124.124.124");
        //SimpleDateFormat Date = new SimpleDateFormat("yyyy-MM-dd");
        sysSupervisor.setOperatorTime(new Date());
        // TODO: sendEmail

        sysSupervisorMapper.updateByPrimaryKeySelective(sysSupervisor);
    }
//    public boolean checkNameExist(String name, Integer id) {
//        return sysSupervisorMapper.countByName(name, id) > 0;
//    }
}
