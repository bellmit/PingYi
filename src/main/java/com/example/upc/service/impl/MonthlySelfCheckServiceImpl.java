package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.common.ValidationResult;
import com.example.upc.common.ValidatorImpl;
import com.example.upc.controller.param.MonthlySelfCheckParam;
import com.example.upc.dao.MonthlySelfCheckMapper;
import com.example.upc.dao.MonthlySelfcheckOptAnswerMapper;
import com.example.upc.dataobject.MonthlySelfCheck;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.MonthlySelfCheckService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.upc.util.operateExcel.monthlySelfInspection.getWord;

@Service
public class MonthlySelfCheckServiceImpl implements MonthlySelfCheckService {
    @Autowired
    MonthlySelfCheckMapper monthlySelfCheckMapper;
    @Autowired
    MonthlySelfcheckOptAnswerMapper monthlySelfcheckOptAnswerMapper;
    @Autowired
    private ValidatorImpl validator;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertMonSelfcheck(MonthlySelfCheckParam monthlySelfCheckParam, SysUser sysUser) throws InvocationTargetException, IllegalAccessException {
        //参数校验
        ValidationResult result = validator.validate(monthlySelfCheckParam);
        if (result.isHasErrors()) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());
        }

        //插入月度自查记录
        MonthlySelfCheck monthlySelfCheck = new MonthlySelfCheck();
        BeanUtils.copyProperties(monthlySelfCheckParam, monthlySelfCheck);
        monthlySelfCheck.setEnterpriseId(sysUser.getInfoId());
        monthlySelfCheckMapper.insert(monthlySelfCheck);
        int selfCheckId = monthlySelfCheck.getId();

        //插入选择题答案
        monthlySelfCheckParam.getMonthlySelfCheckOptCategoryParamList().forEach(item ->
                monthlySelfcheckOptAnswerMapper.batchInsert(item.getOptList(), selfCheckId));
    }

    @Override
    public List<MonthlySelfCheck> selectByDate(MonthlySelfCheckParam monthlySelfCheckParam, SysUser sysUser) {
        monthlySelfCheckParam.setEnterpriseId(sysUser.getInfoId());
        return monthlySelfCheckMapper.selectByDate(monthlySelfCheckParam);
    }

    @Override
    public void deleteById(MonthlySelfCheckParam monthlySelfCheckParam) {
        if (monthlySelfCheckParam.getId() == null || monthlySelfCheckParam.getId() == 0) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "参数不能为空");
        }
        monthlySelfCheckMapper.deleteByPrimaryKey(monthlySelfCheckParam.getId());
        monthlySelfcheckOptAnswerMapper.deleteBySelfCheckId(monthlySelfCheckParam);
    }

    @Override
    public void standingBook(MonthlySelfCheckParam monthlySelfCheckParam) throws Exception {
        Map<String, Object> data = new HashMap<>();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        data.put("${date}", dateFormat.format(monthlySelfCheckParam.getCheckTime()).toString());
        data.put("${inspector}", monthlySelfCheckParam.getCheckStaff());

        data.put("${inspection}", monthlySelfCheckParam.getCheckContent());
        data.put("${problems}", monthlySelfCheckParam.getExistedProblem());
        data.put("${lastProblems}", monthlySelfCheckParam.getLastExistedProblem());
        data.put("${reform}", monthlySelfCheckParam.getRectifySituation());

        monthlySelfCheckParam.getMonthlySelfCheckOptCategoryParamList().forEach(item ->
        {
            switch (item.getPageNumber()) {
                case 1:
                    data.put("${food1}", item.getOptList().get(0).getOptAnswer());
                    data.put("${food2}", item.getOptList().get(1).getOptAnswer());
                    data.put("${food3}", item.getOptList().get(2).getOptAnswer());
                    break;
                case 2:
                    data.put("${health1}", item.getOptList().get(0).getOptAnswer());
                    data.put("${health2}", item.getOptList().get(1).getOptAnswer());
                    data.put("${health3}", item.getOptList().get(2).getOptAnswer());
                    data.put("${health4}", item.getOptList().get(3).getOptAnswer());
                    break;
                case 3:
                    data.put("${invoice1}", item.getOptList().get(0).getOptAnswer());
                    data.put("${invoice2}", item.getOptList().get(1).getOptAnswer());
                    data.put("${invoice3}", item.getOptList().get(2).getOptAnswer());
                    break;
                case 4:
                    data.put("${clean1}", item.getOptList().get(0).getOptAnswer());
                    data.put("${clean2}", item.getOptList().get(1).getOptAnswer());
                    data.put("${clean3}", item.getOptList().get(2).getOptAnswer());
                    data.put("${clean4}", item.getOptList().get(3).getOptAnswer());
                    break;
                case 5:
                    data.put("${work1}", item.getOptList().get(0).getOptAnswer());
                    data.put("${work2}", item.getOptList().get(1).getOptAnswer());
                    data.put("${work3}", item.getOptList().get(2).getOptAnswer());
                    data.put("${work4}", item.getOptList().get(3).getOptAnswer());
                case 6:
                    data.put("${quality1}", item.getOptList().get(0).getOptAnswer());
                    data.put("${quality2}", item.getOptList().get(1).getOptAnswer());
                    data.put("${quality3}", item.getOptList().get(2).getOptAnswer());
                case 8:
                    data.put("${origin1}",  item.getOptList().get(0).getOptAnswer());
                    data.put("${origin2}",  item.getOptList().get(1).getOptAnswer());
            }
        });

        getWord(data, 1000);
    }
}
