package com.example.upc.service.impl;

import com.example.upc.controller.param.BillReportParam;
import com.example.upc.controller.searchParam.BillReportSearchParam;
import com.example.upc.dao.BillReportMapper;
import com.example.upc.dao.OriginRecordBillMapper;
import com.example.upc.dataobject.BillReport;
import com.example.upc.dataobject.Billdao;
import com.example.upc.dataobject.OriginRecordBill;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.BillReportService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.example.upc.util.JsonToImageUrl.JSON2ImageUrl;

@Service
public class BillReportServiceImpl implements BillReportService {
    @Autowired
    BillReportMapper billreportMapper;
    @Autowired
    OriginRecordBillMapper originRecordBillMapper;

    @Override
    public List<BillReportParam> getBillReport(BillReportSearchParam billReportSearchParam, SysUser sysUser){
        if(billReportSearchParam.getDate()!=null)
        {
        billReportSearchParam.setEndTime(new Date(billReportSearchParam.getDate().getTime()+(long) 24 * 60 * 60 * 1000));
        }
        billReportSearchParam.setEnterpriseId(sysUser.getInfoId());
        List<BillReportParam> billReportParamList=billreportMapper.selectBillReport(billReportSearchParam);
        for(BillReportParam billReportParam:billReportParamList){
           // billReportParam.setPicture(JSON2ImageUrl(billReportParam.getPicture()));
            billReportParam.setPicture(billReportParam.getPicture().equals("[]")||billReportParam.getPicture().equals("")?"":JSON2ImageUrl(billReportParam.getPicture()));
        }
        return billReportParamList;
    }

    @Override
    public List<BillReportParam> getBillReportByBillId(BillReportSearchParam billReportSearchParam, SysUser sysUser){
        String billList=billReportSearchParam.getBillList();
        if(billList!=null && !"".equals(billList.trim()))
        {
            List<String> idList = Arrays.asList(billList.split(","));
            List<Integer> billIdList = new ArrayList<Integer>();
            idList.forEach(item->billIdList.add(Integer.parseInt(item)));
            return billreportMapper.selectBillReportByBillList(billIdList);
        }
        return null;
    }

    @Override
    public void insert(BillReportSearchParam billReportSearchParam, SysUser sysUser){

        BillReport billReport = new BillReport();
        BeanUtils.copyProperties(billReportSearchParam,billReport);
        billReport.setEnterpriseId(sysUser.getInfoId());
        billReport.setOperator(sysUser.getLoginName());
        billReport.setOperatorIp("127.0.0.1");
        billReport.setOperatorTime(new Date());
        billreportMapper.insert(billReport);
        billReportSearchParam.getIdList().forEach(item ->{
            OriginRecordBill originRecordBill = new OriginRecordBill();
            originRecordBill.setBillId(billReport.getId());
            originRecordBill.setRecordId(item);
            originRecordBill.setOperator(sysUser.getLoginName());
            originRecordBill.setOperatorIp("127.0.0.1");
            originRecordBill.setOperatorTime(new Date());
            originRecordBillMapper.insert(originRecordBill);
        });

    }

    @Override
    public void update(BillReportSearchParam billReportSearchParam, SysUser sysUser){

        BillReport billReport = new BillReport();
        BeanUtils.copyProperties(billReportSearchParam,billReport);
        billReport.setEnterpriseId(sysUser.getInfoId());
        billReport.setOperator(sysUser.getLoginName());
        billReport.setOperatorIp("127.0.0.1");
        billReport.setOperatorTime(new Date());
        billreportMapper.updateByPrimaryKey(billReport);
        originRecordBillMapper.deleteByBillId(billReportSearchParam.getId());
        billReportSearchParam.getIdList().forEach(item ->{
            OriginRecordBill originRecordBill = new OriginRecordBill();
            originRecordBill.setBillId(billReport.getId());
            originRecordBill.setRecordId(item);
            originRecordBill.setOperator(sysUser.getLoginName());
            originRecordBill.setOperatorIp("127.0.0.1");
            originRecordBill.setOperatorTime(new Date());
            originRecordBillMapper.insert(originRecordBill);
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(BillReportSearchParam billReportSearchParam, SysUser sysUser){
        billreportMapper.deleteByPrimaryKey(billReportSearchParam.getId());
        originRecordBillMapper.deleteByBillId(billReportSearchParam.getId());
    }
}
