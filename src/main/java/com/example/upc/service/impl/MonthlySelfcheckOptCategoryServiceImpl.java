package com.example.upc.service.impl;

import com.example.upc.controller.param.MonthlySelfCheckParam;
import com.example.upc.dao.MonthlySelfCheckMapper;
import com.example.upc.dao.MonthlySelfcheckOptCategoryMapper;
import com.example.upc.dataobject.MonthlySelfCheck;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.MonthlySelfcheckOptCategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class MonthlySelfcheckOptCategoryServiceImpl implements MonthlySelfcheckOptCategoryService {
    @Autowired
    MonthlySelfcheckOptCategoryMapper monthlySelfcheckOptCategoryMapper;
    @Autowired
    MonthlySelfCheckMapper monthlySelfCheckMapper;

    @Override
    public MonthlySelfCheckParam selectAllOpt(MonthlySelfCheck monthlySelfCheck, SysUser sysUser) {
        //构造返回参数
        MonthlySelfCheckParam monthlySelfCheckParam = new MonthlySelfCheckParam();
        //构造返回的题目列表
        monthlySelfCheckParam.setMonthlySelfCheckOptCategoryParamList(monthlySelfcheckOptCategoryMapper.selectAllOpt(monthlySelfCheck));

        //获取当前时间
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        //获取前一个月的时间
        calendar.add(Calendar.MONTH, -1);
        date = calendar.getTime();

        //获取上次存在的问题
        if (monthlySelfCheck.getId() == null || monthlySelfCheck.getId() == 0) {
            if (monthlySelfCheckMapper.selectByMonth(date, sysUser.getInfoId()) != null) {
                monthlySelfCheckParam.setLastExistedProblem(monthlySelfCheckMapper.selectByMonth(date, sysUser.getInfoId()).getLastExistedProblem());
            }
        }
        else{
            BeanUtils.copyProperties(monthlySelfCheckMapper.selectByPrimaryKey(monthlySelfCheck.getId()),monthlySelfCheckParam);
        }

        return monthlySelfCheckParam;
    }
}
