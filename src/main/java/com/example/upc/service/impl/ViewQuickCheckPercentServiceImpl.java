package com.example.upc.service.impl;

import com.example.upc.controller.param.QuickCheckPercentParam;
import com.example.upc.dao.*;
import com.example.upc.dataobject.*;
import com.example.upc.service.ViewQuickCheckPercentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViewQuickCheckPercentServiceImpl implements ViewQuickCheckPercentService {
    @Autowired
    ViewQuickCheckTotalPercentMapper viewQuickCheckTotalPercentMapper;
    @Autowired
    ViewQuickCheckTeamPercentResultMapper viewQuickCheckTeamPercentResultMapper;
    @Autowired
    ViewQuickCheckTeamTypeNameMoneyTotalResultMapper viewQuickCheckTeamTypeNameMoneyTotalResultMapper;
    @Autowired
    ViewQuickCheckTypePercentResultMapper viewQuickCheckTypePercentResultMapper;
    @Autowired
    ViewQuickCheckTeamMoneyResultMapper viewQuickCheckTeamMoneyResultMapper;
    @Autowired
    ViewQuickCheckTotalMoneyResultMapper viewQuickCheckTotalMoneyResultMapper;
    @Autowired
    ViewQuickCheckMarketPercentResultMapper viewQuickCheckMarketPercentResultMapper;
    @Autowired
    ViewQuickCheckSampleTopTenMapper viewQuickCheckSampleTopTenMapper;
    @Autowired
    ViewQuickCheckBuyTopTenMapper viewQuickCheckBuyTopTenMapper;
    @Autowired
    ViewQuickCheckBuyTotalMapper viewQuickCheckBuyTotalMapper;



    @Override
    public QuickCheckPercentParam getListAll()
    {
        QuickCheckPercentParam quickCheckPercentParam = new QuickCheckPercentParam();
        List<ViewQuickCheckTotalPercent> list1 = viewQuickCheckTotalPercentMapper.getListAll();
        quickCheckPercentParam.setList1(list1);
        List<ViewQuickCheckTeamPercentResult> list2 = viewQuickCheckTeamPercentResultMapper.getListAll();
        quickCheckPercentParam.setList2(list2);
        List<ViewQuickCheckTypePercentResult> list3 = viewQuickCheckTypePercentResultMapper.getListAll();
        quickCheckPercentParam.setList3(list3);
        List<ViewQuickCheckTotalMoneyResult> list4 = viewQuickCheckTotalMoneyResultMapper.getListAll();
        quickCheckPercentParam.setList4(list4);
        List<ViewQuickCheckTeamMoneyResult> list5 = viewQuickCheckTeamMoneyResultMapper.getListAll();
        quickCheckPercentParam.setList5(list5);
        List<ViewQuickCheckMarketPercentResult> list6 = viewQuickCheckMarketPercentResultMapper.getListAll();
        quickCheckPercentParam.setList6(list6);
        List<ViewQuickCheckSampleTopTen> list7 = viewQuickCheckSampleTopTenMapper.getListAll();
        quickCheckPercentParam.setList7(list7);
        List<ViewQuickCheckBuyTopTen> list8 = viewQuickCheckBuyTopTenMapper.getListAll();
        quickCheckPercentParam.setList8(list8);
        List<ViewQuickCheckBuyTotal> list9 = viewQuickCheckBuyTotalMapper.getListAll();
        quickCheckPercentParam.setList9(list9);

        return  quickCheckPercentParam;
    }

    @Override
    public List<ViewQuickCheckTotalPercent> getListAllTotalPercent()
    {
        List<ViewQuickCheckTotalPercent> list = viewQuickCheckTotalPercentMapper.getListAll();
        return list;
    }

    @Override
    public List<ViewQuickCheckTeamPercentResult> getListAllTeamPercentResult()
    {
        List<ViewQuickCheckTeamPercentResult> list = viewQuickCheckTeamPercentResultMapper.getListAll();
        return list;
    }

    @Override
    public List<ViewQuickCheckTeamNameMoneyTotalResult> getListByTeamTopTen(String team)
    {
        List<ViewQuickCheckTeamNameMoneyTotalResult> list = viewQuickCheckTeamTypeNameMoneyTotalResultMapper.getListByTeamTopTen(team);
        return list;
    }

    @Override
    public List<ViewQuickCheckTypePercentResult> getListAllTypePercentResult()
    {
        List<ViewQuickCheckTypePercentResult> list = viewQuickCheckTypePercentResultMapper.getListAll();
        return list;
    }

    @Override
    public List<ViewQuickCheckTeamNameMoneyTotalResult> getListByTypeTopTen(String type)
    {
        List<ViewQuickCheckTeamNameMoneyTotalResult> list = viewQuickCheckTeamTypeNameMoneyTotalResultMapper.getListByTypeTopTen(type);
        return list;
    }

    @Override
    public List<ViewQuickCheckTotalMoneyResult> getListAllTotalMoneyResult()
    {
        List<ViewQuickCheckTotalMoneyResult> list = viewQuickCheckTotalMoneyResultMapper.getListAll();
        return list;
    }

    @Override
    public List<ViewQuickCheckTeamMoneyResult> getListAllTeamMoneyResult()
    {
        List<ViewQuickCheckTeamMoneyResult> list = viewQuickCheckTeamMoneyResultMapper.getListAll();
        return list;
    }

    @Override
    public List<ViewQuickCheckMarketPercentResult> getListAllMarketPercentResult()
    {
        List<ViewQuickCheckMarketPercentResult> list = viewQuickCheckMarketPercentResultMapper.getListAll();
        return list;
    }

    @Override
    public List<ViewQuickCheckTeamNameMoneyTotalResult> getListByMarketTopTen(String market)
    {
        List<ViewQuickCheckTeamNameMoneyTotalResult> list = viewQuickCheckTeamTypeNameMoneyTotalResultMapper.getListByMarketTopTen(market);
        return list;
    }

    @Override
    public List<ViewQuickCheckSampleTopTen> getListAllSampleTopTen()
    {
        List<ViewQuickCheckSampleTopTen> list = viewQuickCheckSampleTopTenMapper.getListAll();
        return list;
    }
    @Override
    public List<ViewQuickCheckBuyTopTen> getListAllBuyTopTen()
    {
        List<ViewQuickCheckBuyTopTen> list = viewQuickCheckBuyTopTenMapper.getListAll();
        return list;
    }

}
