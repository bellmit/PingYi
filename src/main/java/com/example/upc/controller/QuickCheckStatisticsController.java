package com.example.upc.controller;

import com.example.upc.common.CommonReturnType;
import com.example.upc.service.ViewQuickCheckPercentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/quickCheckStatistics")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class QuickCheckStatisticsController {

    @Autowired
    private ViewQuickCheckPercentService viewQuickCheckPercentService;

    //总页面返回
    @RequestMapping("/getListAll")
    @ResponseBody
    public CommonReturnType getListAll(){
        return CommonReturnType.create(viewQuickCheckPercentService.getListAll());
    }

    //总数查看
    @RequestMapping("/getListAllTotalPercent")
    @ResponseBody
    public CommonReturnType getListAllQuickCheckTotalPercent(){
        return CommonReturnType.create(viewQuickCheckPercentService.getListAllTotalPercent());
    }

    //分所队查看合格不合格总数表
    @RequestMapping("/getListAllTeamPercentResult")
    @ResponseBody
    public CommonReturnType getListAllTeamPercentResult(){
        return CommonReturnType.create(viewQuickCheckPercentService.getListAllTeamPercentResult());
    }

    //分所队查看不合格总数表前十名
    @RequestMapping("/getListByTeamTopTen")
    @ResponseBody
    public CommonReturnType getListByTeamTopTen(String team){
        return CommonReturnType.create(viewQuickCheckPercentService.getListByTeamTopTen(team));
    }

    //分类别查看合格不合格总数表
    @RequestMapping("/getListAllTypePercentResult")
    @ResponseBody
    public CommonReturnType getListAllTypePercentResult(){
        return CommonReturnType.create(viewQuickCheckPercentService.getListAllTypePercentResult());
    }

    //分类别查看不合格总数表前十名
    @RequestMapping("/getListByTypeTopTen")
    @ResponseBody
    public CommonReturnType getListByTypeTopTen(String type){
        return CommonReturnType.create(viewQuickCheckPercentService.getListByTypeTopTen(type));
    }

    //查看总花费
    @RequestMapping("/getListAllTotalMoneyResult")
    @ResponseBody
    public CommonReturnType getListAllTotalMoneyResult(){
        return CommonReturnType.create(viewQuickCheckPercentService.getListAllTotalMoneyResult());
    }

    //分所队查看花费
    @RequestMapping("/getListAllTeamMoneyResult")
    @ResponseBody
    public CommonReturnType getListAllTeamMoneyResult(){
        return CommonReturnType.create(viewQuickCheckPercentService.getListAllTeamMoneyResult());
    }

    //分所队查看花费
    @RequestMapping("/getListAllMarketPercentResult")
    @ResponseBody
    public CommonReturnType getListAllMarketPercentResult(){
        return CommonReturnType.create(viewQuickCheckPercentService.getListAllMarketPercentResult());
    }

    //分农贸市场查看不合格总数表前十名
    @RequestMapping("/getListByMarketTopTen")
    @ResponseBody
    public CommonReturnType getListByMarketTopTen(String market){
        return CommonReturnType.create(viewQuickCheckPercentService.getListByMarketTopTen(market));
    }
}
