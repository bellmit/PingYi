package com.example.upc.service;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.param.SpotCheckPercentParam;
import com.example.upc.dataobject.*;

import java.util.List;

public interface ViewSpotCheckPercentService {
    List<ViewSpotCheckTotalPercent> getListAllTotalPercent ();
    SpotCheckPercentParam getListAll();
    List<ViewSpotCheckTeamStepResult> getListAllTeamStepResult();
    List<ViewSpotCheckTeamStepResult> getListTeamStepResultByTeam(String team);
    List<ViewSpotCheckTeamStepResult> getListTeamStepResultByStep(String step);
    List<ViewSpotCheckTeamResult> getListAllTeamResult();
    List<ViewSpotCheckStepResult> getListAllStepResult();
    List<ViewSpotCheckTypeNameResult> getListAllTypeNameResult();
    List<ViewSpotCheckTypeNameResult> getListTypeNameResultByTypeTopTen(String type);
    List<ViewSpotCheckTypeResult> getListAllTypeResult();
    List<ViewSpotCheckOrgResult> getListAllOrgResult();
    List<ViewSpotCheckOrgTypeNameMoneyResult> getListByTypeTopTen(String type, String org);
    List<ViewSpotCheckOrgTypeMoneyResult> getListByOrg(String org);
    List<ViewSpotCheckSampleTopTen> getListSampleTopTen();
    List<ViewSpotCheckNameTopTen> getListNameTopTen();
    List<ViewSpotCheckTeamStepSingleResult> getListTeamStepSingleResultByTeam(String team);
    List<ViewSpotCheckTeamStepAllResult> getListTeamStepAllResultByStep(String step);

}
