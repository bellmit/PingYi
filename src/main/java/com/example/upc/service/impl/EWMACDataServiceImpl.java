package com.example.upc.service.impl;

import com.example.upc.controller.param.EWMACDataParam;
import com.example.upc.controller.param.EWMADataConfigParam;
import com.example.upc.dao.*;
import com.example.upc.dataobject.*;
import com.example.upc.service.EWMACDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EWMACDataServiceImpl implements EWMACDataService {
    @Autowired
    ViewQuickCheckTeamPercentResultMapper viewQuickCheckTeamPercentResultMapper;
    @Autowired
    ViewQuickCheckTotalPercentMapper viewQuickCheckTotalPercentMapper;
    @Autowired
    ViewSpotCheckTotalPercentMapper viewSpotCheckTotalPercentMapper;
    @Autowired
    ViewSpotCheckTeamResultMapper viewSpotCheckTeamResultMapper;
    @Autowired
    ViewQuickCheckTypePercentResultMapper viewQuickCheckTypePercentResultMapper;
    @Autowired
    ViewSpotCheckTypeResultMapper viewSpotCheckTypeResultMapper;
    @Autowired
    ViewEnterpriseTotalMapper viewEnterpriseTotalMapper;
    @Autowired
    ViewSupplierTotalMapper viewSupplierTotalMapper;
    @Autowired
    ViewTeamEnterprisePercentResultMapper viewTeamEnterprisePercentResultMapper;
    @Autowired
    FormatDisinfectionMapper formatDisinfectionMapper;
    @Autowired
    FormatOriginRecordConfigMapper formatOriginRecordConfigMapper;
    @Autowired
    FormatWasteMapper formatWasteMapper;

    @Override
    public EWMACDataParam getListAll()
    {
        EWMACDataParam ewmacDataParam = new EWMACDataParam();
        List<ViewQuickCheckTeamPercentResult> list1 = viewQuickCheckTeamPercentResultMapper.getListAll();
        ewmacDataParam.setList1(list1);
        List<ViewQuickCheckTotalPercent> list2 = viewQuickCheckTotalPercentMapper.getListAll();
        ewmacDataParam.setList2(list2);
        List<ViewSpotCheckTotalPercent> list3 = viewSpotCheckTotalPercentMapper.getListAll();
        ewmacDataParam.setList3(list3);
        List<ViewSpotCheckTeamResult> list4 = viewSpotCheckTeamResultMapper.getListAll();
        ewmacDataParam.setList4(list4);
        List<ViewQuickCheckTypePercentResult> list5 = viewQuickCheckTypePercentResultMapper.getListAll();
        ewmacDataParam.setList5(list5);
        List<ViewSpotCheckTypeResult> list6 = viewSpotCheckTypeResultMapper.getListAll();
        ewmacDataParam.setList6(list6);
        List<ViewEnterpriseTotal> list7 = viewEnterpriseTotalMapper.getListAll();
        ewmacDataParam.setList7(list7);
        List<ViewSupplierTotal> list8 = viewSupplierTotalMapper.getListAll();
        ewmacDataParam.setList8(list8);


        List<EWMADataConfigParam> list= new ArrayList<>();
        EWMADataConfigParam ewmaDataConfigParam = new EWMADataConfigParam();
        ewmaDataConfigParam.setDisinfection(formatDisinfectionMapper.countList());
        ewmaDataConfigParam.setOrigin(formatOriginRecordConfigMapper.countList());
        ewmaDataConfigParam.setWaste(formatWasteMapper.countList());
        list.add(ewmaDataConfigParam);
        ewmacDataParam.setList9(list);


        List<ViewTeamEnterprisePercentResult> list10 = viewTeamEnterprisePercentResultMapper.getListAll();
        ewmacDataParam.setList10(list10);
        return ewmacDataParam;
    }
}
