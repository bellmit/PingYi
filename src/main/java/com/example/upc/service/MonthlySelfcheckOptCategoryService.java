package com.example.upc.service;

import com.example.upc.controller.param.MonthlySelfCheckOptCategoryParam;
import com.example.upc.controller.param.MonthlySelfCheckParam;
import com.example.upc.dataobject.MonthlySelfCheck;
import com.example.upc.dataobject.SysUser;

import java.util.List;

public interface MonthlySelfcheckOptCategoryService {
    MonthlySelfCheckParam selectAllOpt(MonthlySelfCheck monthlySelfCheck, SysUser sysUser);
}
