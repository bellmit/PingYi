package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.common.ValidationResult;
import com.example.upc.common.ValidatorImpl;
import com.example.upc.controller.UploadController;
import com.example.upc.controller.param.FormatWasteParam;
import com.example.upc.controller.param.FormatWasteSupParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.InspectionSearchParam;
import com.example.upc.controller.searchParam.WasteSearchParam;
import com.example.upc.dao.*;
import com.example.upc.dataobject.*;
import com.example.upc.service.FormatWasteService;
import com.example.upc.util.operateExcel.WasteExcel;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FormatWasteServiceImpl implements FormatWasteService {

    @Autowired
    FormatWasteMapper formatWasteMapper;
    @Autowired
    SupervisionCaMapper supervisionCaMapper;
    @Autowired
    SupervisionEnterpriseMapper supervisionEnterpriseMapper;
    @Autowired
    SysDeptMapper sysDeptMapper;
    @Autowired
    SysDeptAreaMapper sysDeptAreaMapper;
    @Autowired
    SysAreaMapper sysAreaMapper;
    @Autowired
    private ValidatorImpl validator;

    @Override
    public PageResult getPage(PageQuery pageQuery, WasteSearchParam wasteSearchParam) {
        int count=formatWasteMapper.countListSup(wasteSearchParam);
        if (count > 0) {
            List<FormatWasteSupParam> fpList = formatWasteMapper.getPage(pageQuery, wasteSearchParam);
            PageResult<FormatWasteSupParam> pageResult = new PageResult<>();
            pageResult.setData(fpList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<FormatWasteSupParam> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public PageResult getPageAdmin(PageQuery pageQuery, WasteSearchParam wasteSearchParam) {
        int count=formatWasteMapper.countListAdmin(wasteSearchParam);
        if (count > 0) {
            List<FormatWasteSupParam> fpList = formatWasteMapper.getPageAdmin(pageQuery, wasteSearchParam);
            PageResult<FormatWasteSupParam> pageResult = new PageResult<>();
            pageResult.setData(fpList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<FormatWasteSupParam> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public PageResult getPageEnterprise(PageQuery pageQuery, Integer id, WasteSearchParam wasteSearchParam) {

        int count=formatWasteMapper.countListEnterprise(id, wasteSearchParam);
        if (count > 0) {
            List<FormatWaste> fdList = formatWasteMapper.getPageEnterprise(pageQuery, id, wasteSearchParam);
            PageResult<FormatWaste> pageResult = new PageResult<>();
            pageResult.setData(fdList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<FormatWaste> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public List<FormatWaste> getPageEnterprise2( Integer id, WasteSearchParam wasteSearchParam) {

        int count=formatWasteMapper.countListEnterprise(id, wasteSearchParam);
        if (count > 0) {
            List<FormatWaste> fdList = formatWasteMapper.getPageEnterprise2(id, wasteSearchParam);
            return fdList;
        }
        List<FormatWaste> pageResult = new ArrayList<>();
        return pageResult;
    }

    @Override
    @Transactional
    public void insert(FormatWasteParam formatWasteParam, SysUser sysUser) {

        ValidationResult result = validator.validate(formatWasteParam);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }

        SupervisionEnterprise supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(sysUser.getInfoId());
        if (supervisionEnterprise==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"无企业信息");
        }
        FormatWaste formatWaste1 = new FormatWaste();
        formatWaste1.setEnterprise(sysUser.getInfoId());
        formatWaste1.setArea(supervisionEnterprise.getArea());
        formatWaste1.setDisposaltime(formatWasteParam.getDisposaltime());
        formatWaste1.setKind(formatWasteParam.getKind());
        formatWaste1.setNumber(formatWasteParam.getNumber());
        formatWaste1.setDisposalperson(formatWasteParam.getDisposalperson());
        formatWaste1.setRegistrationtime(formatWasteParam.getRegistrationtime());
        formatWaste1.setRecyclingenterprises(formatWasteParam.getRecyclingenterprises());
        formatWaste1.setRecycler(formatWasteParam.getRecycler());
        formatWaste1.setExtra(formatWasteParam.getExtra());
        formatWaste1.setOperator("操作人");
        formatWaste1.setOperatorIp("124.124.124");
        formatWaste1.setOperatorTime(new Date());

        // TODO: sendEmail

        formatWasteMapper.insertSelective(formatWaste1);
//        http://localhost:8080/formatdishes/update?id=1&unit=%E5%B1%B1%E4%B8%9C%E5%A6%82%E6%96%B0%E5%85%AC%E5%8F%B8&number=2&name=%E5%A4%A7%E7%B1%B3%E9%A5%AD&price=11&type=%E4%B8%BB%E9%A3%9F&remark=%E7%B1%B3%E9%A5%AD
    }
    @Override
    public void delete(int fwId) {
        FormatWaste formatWaste = formatWasteMapper.selectByPrimaryKey(fwId);
        if(formatWaste==null){
            throw new BusinessException(EmBusinessError.ID_ERROR);
        }
        formatWasteMapper.deleteByPrimaryKey(fwId);
    }

    @Override
    @Transactional
    public void update(FormatWasteParam formatWasteParam, SysUser sysUser) {

        ValidationResult result = validator.validate(formatWasteParam);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }

        if(formatWasteMapper.selectByPrimaryKey(formatWasteParam.getId())==null){
            throw new BusinessException(EmBusinessError.UPDATE_ERROR);
        }

        SupervisionEnterprise supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(sysUser.getInfoId());
        if (supervisionEnterprise==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"无此企业信息");
        }

        FormatWaste formatWaste1 = new FormatWaste();
        formatWaste1.setId(formatWasteParam.getId());
        formatWaste1.setEnterprise(sysUser.getInfoId());
        formatWaste1.setArea(supervisionEnterprise.getArea());
        formatWaste1.setDisposaltime(formatWasteParam.getDisposaltime());
        formatWaste1.setKind(formatWasteParam.getKind());
        formatWaste1.setNumber(formatWasteParam.getNumber());
        formatWaste1.setDisposalperson(formatWasteParam.getDisposalperson());
        formatWaste1.setRegistrationtime(formatWasteParam.getRegistrationtime());
        formatWaste1.setRecyclingenterprises(formatWasteParam.getRecyclingenterprises());
        formatWaste1.setRecycler(formatWasteParam.getRecycler());
        formatWaste1.setExtra(formatWasteParam.getExtra());
        formatWaste1.setOperator("操作人");
        formatWaste1.setOperatorIp("124.124.124");
        formatWaste1.setOperatorTime(new Date());

        // TODO: sendEmail

        formatWasteMapper.updateByPrimaryKeySelective(formatWaste1);
    }

    @Override
    public String standingBook ( WasteSearchParam wasteSearchParam, SysUser sysUser) throws IOException {
        List<FormatWaste> formatWasteList = formatWasteMapper.getPageEnterprise2(sysUser.getInfoId(),wasteSearchParam);
        List<String[]> data = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (FormatWaste item:formatWasteList
             ) {
            data.add(new String[]{
                    dateFormat.format(item.getDisposaltime()),item.getKind(),item.getNumber(),item.getDisposalperson(),item.getRecyclingenterprises(),item.getRecycler(),item.getExtra()

            });
        }
        String fileName = "废弃物处理";
        String path = WasteExcel.getXLsx(data,"/template/【导出】废弃物处理模板.xlsx",fileName,sysUser.getInfoId());

        //下载

//        UploadController.downloadStandingBook(response, fileName,path);

        return path;
    }

    @Override
    public void fail() {
        throw new BusinessException(EmBusinessError.USER_NO);
    }

}
