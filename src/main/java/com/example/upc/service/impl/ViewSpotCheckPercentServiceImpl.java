package com.example.upc.service.impl;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.param.SpotCheckPercentParam;
import com.example.upc.dao.*;
import com.example.upc.dataobject.*;
import com.example.upc.service.ViewSpotCheckPercentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViewSpotCheckPercentServiceImpl implements ViewSpotCheckPercentService {

    @Autowired
    ViewSpotCheckTotalPercentMapper viewSpotCheckTotalPercentMapper;
    @Autowired
    ViewSpotCheckMoneyMapper viewSpotCheckMoneyMapper;
    @Autowired
    ViewSpotCheckTeamStepResultMapper viewSpotCheckTeamStepResultMapper;
    @Autowired
    ViewSpotCheckTeamResultMapper viewSpotCheckTeamResultMapper;
    @Autowired
    ViewSpotCheckStepResultMapper viewSpotCheckStepResultMapper;
    @Autowired
    ViewSpotCheckTypeNameResultMapper viewSpotCheckTypeNameResultMapper;
    @Autowired
    ViewSpotCheckTypeResultMapper viewSpotCheckTypeResultMapper;
    @Autowired
    ViewSpotCheckOrgResultMapper viewSpotCheckOrgResultMapper;
    @Autowired
    ViewSpotCheckOrgTypeNameMoneyResultMapper viewSpotCheckOrgTypeNameMoneyResultMapper;
    @Autowired
    ViewSpotCheckOrgTypeMoneyResultMapper viewSpotCheckOrgTypeMoneyResultMapper;
    @Autowired
    ViewSpotCheckSampleTopTenMapper viewSpotCheckSampleTopTenMapper;
    @Autowired
    ViewSpotCheckNameTopTenMapper viewSpotCheckNameTopTenMapper;
    @Autowired
    ViewSpotCheckTeamSumResultMapper viewSpotCheckTeamSumResultMapper;
    @Autowired
    ViewSpotCheckStepSumResultMapper viewSpotCheckStepSumResultMapper;
    @Autowired
    ViewSpotCheckTeamStepSingleResultMapper viewSpotCheckTeamStepSingleResultMapper;
    @Autowired
    ViewSpotCheckTeamStepAllResultMapper viewSpotCheckTeamStepAllResultMapper;



    @Override
    public SpotCheckPercentParam getListAll()
    {
        SpotCheckPercentParam spotCheckPercentParam = new SpotCheckPercentParam();
        List<ViewSpotCheckTotalPercent> list1 = viewSpotCheckTotalPercentMapper.getListAll();
        spotCheckPercentParam.setList1(list1);
        List<ViewSpotCheckTeamResult> list2 = viewSpotCheckTeamResultMapper.getListAll();
        spotCheckPercentParam.setList2(list2);
        List<ViewSpotCheckStepResult> list3 = viewSpotCheckStepResultMapper.getListAll();
        spotCheckPercentParam.setList3(list3);
        List<ViewSpotCheckTypeResult> list4 = viewSpotCheckTypeResultMapper.getListAll();
        spotCheckPercentParam.setList4(list4);
        List<ViewSpotCheckMoney> list5 = viewSpotCheckMoneyMapper.getListAll();
        spotCheckPercentParam.setList5(list5);
        List<ViewSpotCheckOrgResult> list6 = viewSpotCheckOrgResultMapper.getListAll();
        spotCheckPercentParam.setList6(list6);
        List<ViewSpotCheckSampleTopTen> list7 = viewSpotCheckSampleTopTenMapper.getListAll();
        spotCheckPercentParam.setList7(list7);
        List<ViewSpotCheckNameTopTen> list8 = viewSpotCheckNameTopTenMapper.getListAll();
        spotCheckPercentParam.setList8(list8);
        List<ViewSpotCheckTeamSumResult> list9 = viewSpotCheckTeamSumResultMapper.getListAll();
        spotCheckPercentParam.setList9(list9);
        List<ViewSpotCheckStepSumResult> list10 = viewSpotCheckStepSumResultMapper.getListAll();
        spotCheckPercentParam.setList10(list10);

        return  spotCheckPercentParam;
    }

    @Override
    public List<ViewSpotCheckTotalPercent> getListAllTotalPercent()
    {
        List<ViewSpotCheckTotalPercent> list = viewSpotCheckTotalPercentMapper.getListAll();
        return list;
    }


    @Override
    public List<ViewSpotCheckTeamStepResult> getListAllTeamStepResult()
    {
        List<ViewSpotCheckTeamStepResult> list = viewSpotCheckTeamStepResultMapper.getListAll();
        return list;
    }

    @Override
    public List<ViewSpotCheckTeamStepResult> getListTeamStepResultByTeam(String team)
    {
        List<ViewSpotCheckTeamStepResult> list = viewSpotCheckTeamStepResultMapper.getListByTeam(team);
        return list;
    }

    @Override
    public List<ViewSpotCheckTeamStepResult> getListTeamStepResultByStep(String step)
    {
        List<ViewSpotCheckTeamStepResult> list = viewSpotCheckTeamStepResultMapper.getListByStep(step);
        return list;
    }

    @Override
    public List<ViewSpotCheckTeamResult> getListAllTeamResult()
    {
        List<ViewSpotCheckTeamResult> list = viewSpotCheckTeamResultMapper.getListAll();
        return list;
    }

    @Override
    public List<ViewSpotCheckStepResult> getListAllStepResult()
    {
        List<ViewSpotCheckStepResult> list = viewSpotCheckStepResultMapper.getListAll();
        return list;
    }

    @Override
    public List<ViewSpotCheckTypeNameResult> getListAllTypeNameResult()
    {
        List<ViewSpotCheckTypeNameResult> list = viewSpotCheckTypeNameResultMapper.getListAll();
        return list;
    }

    @Override
    public List<ViewSpotCheckTypeNameResult> getListTypeNameResultByTypeTopTen(String type)
    {
        List<ViewSpotCheckTypeNameResult> list = viewSpotCheckTypeNameResultMapper.getListByTypeTopTen(type);
        return list;
    }

    @Override
    public List<ViewSpotCheckTypeResult> getListAllTypeResult()
    {
        List<ViewSpotCheckTypeResult> list = viewSpotCheckTypeResultMapper.getListAll();
        return list;
    }

    @Override
    public List<ViewSpotCheckOrgResult> getListAllOrgResult()
    {
        List<ViewSpotCheckOrgResult> list = viewSpotCheckOrgResultMapper.getListAll();
        return list;
    }

    @Override
    public List<ViewSpotCheckOrgTypeMoneyResult> getListByOrg(String org)
    {
        List<ViewSpotCheckOrgTypeMoneyResult> list = viewSpotCheckOrgTypeMoneyResultMapper.getListByOrg(org);
        return list;
    }

    @Override
    public List<ViewSpotCheckOrgTypeNameMoneyResult> getListByTypeTopTen(String type, String org)
    {
        List<ViewSpotCheckOrgTypeNameMoneyResult> list = viewSpotCheckOrgTypeNameMoneyResultMapper.getListByTypeTopTen(type, org);
        return list;
    }

    @Override
    public List<ViewSpotCheckSampleTopTen> getListSampleTopTen()
    {
        List<ViewSpotCheckSampleTopTen> list = viewSpotCheckSampleTopTenMapper.getListAll();
        return list;
    }

    @Override
    public List<ViewSpotCheckNameTopTen> getListNameTopTen()
    {
        List<ViewSpotCheckNameTopTen> list = viewSpotCheckNameTopTenMapper.getListAll();
        return list;
    }


    @Override
    public List<ViewSpotCheckTeamStepSingleResult> getListTeamStepSingleResultByTeam(String team)
    {
        List<ViewSpotCheckTeamStepSingleResult> list = viewSpotCheckTeamStepSingleResultMapper.getListByTeam(team);
        return list;
    }

    @Override
    public List<ViewSpotCheckTeamStepAllResult> getListTeamStepAllResultByStep(String step)
    {
        List<ViewSpotCheckTeamStepAllResult> list = viewSpotCheckTeamStepAllResultMapper.getListByStep(step);
        return list;
    }

}
