package com.example.upc.service;

import com.example.upc.controller.param.QuickCheckPercentParam;
import com.example.upc.dataobject.*;

import java.util.List;

public interface ViewQuickCheckPercentService {
    QuickCheckPercentParam getListAll();
    List<ViewQuickCheckTotalPercent> getListAllTotalPercent ();
    List<ViewQuickCheckTeamPercentResult> getListAllTeamPercentResult ();
    List<ViewQuickCheckTeamNameMoneyTotalResult> getListByTeamTopTen (String team);
    List<ViewQuickCheckTypePercentResult> getListAllTypePercentResult ();
    List<ViewQuickCheckTeamNameMoneyTotalResult> getListByTypeTopTen (String type);
    List<ViewQuickCheckTeamMoneyResult> getListAllTeamMoneyResult ();
    List<ViewQuickCheckTotalMoneyResult> getListAllTotalMoneyResult ();
    List<ViewQuickCheckMarketPercentResult> getListAllMarketPercentResult ();
    List<ViewQuickCheckTeamNameMoneyTotalResult> getListByMarketTopTen (String market);
    List<ViewQuickCheckSampleTopTen> getListAllSampleTopTen ();
    List<ViewQuickCheckBuyTopTen> getListAllBuyTopTen ();
}
