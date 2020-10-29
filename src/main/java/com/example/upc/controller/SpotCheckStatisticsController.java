package com.example.upc.controller;

import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.service.SpotCheckBaseService;
import com.example.upc.service.ViewSpotCheckPercentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/spotCheckStatistics")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class SpotCheckStatisticsController {
    @Autowired
    private ViewSpotCheckPercentService viewSpotCheckPercentService;
    @Autowired
    private SpotCheckBaseService spotCheckBaseService;

    //总页面返回
    @RequestMapping("/getListAll")
    @ResponseBody
    public CommonReturnType getListAll(){
        return CommonReturnType.create(viewSpotCheckPercentService.getListAll());
    }

    //总数查看
    @RequestMapping("/getPagePercentTotal")
    @ResponseBody
    public CommonReturnType getPagePercentTotal(){
        return CommonReturnType.create(viewSpotCheckPercentService.getListAllTotalPercent());
    }

    //食品分类的时候查看详情按钮接口
    @RequestMapping("/getPageFoodType")
    @ResponseBody
    public CommonReturnType getPageFoodType(PageQuery pageQuery, String type){
        return CommonReturnType.create(spotCheckBaseService.getPageFoodType(pageQuery, type));
    }


    //获取花费时的检测机构获取详情按钮调用的接口
    @RequestMapping("/getListByEnterprise")
    @ResponseBody
    public CommonReturnType getListByEnterprise(PageQuery pageQuery, String name){
        return CommonReturnType.create(spotCheckBaseService.getPageByEnterprise(pageQuery, name));
    }



    //所属所队和环节的统计
    @RequestMapping("/getListAllTeamStepResult")
    @ResponseBody
    public CommonReturnType getListAllTeamStepResult(){
        return CommonReturnType.create(viewSpotCheckPercentService.getListAllTeamStepResult());
    }

    //所属所队的全部统计，匹配下方所属所队—>环节的接口
    @RequestMapping("/getListAllTeamResult")
    @ResponseBody
    public CommonReturnType getListAllTeamResult(){
        return CommonReturnType.create(viewSpotCheckPercentService.getListAllTeamResult());
    }

    //所属所队按钮到某个对所的环节统计
    @RequestMapping("/getListTeamStepResultByTeam")
    @ResponseBody
    public CommonReturnType getListTeamStepResultByTeam(String team){
        return CommonReturnType.create(viewSpotCheckPercentService.getListTeamStepResultByTeam(team));
    }

    //环节的全部统计，匹配下方环节到以下的所有所队统计
    @RequestMapping("/getListAllStepResult")
    @ResponseBody
    public CommonReturnType getListAllStepResult(){
        return CommonReturnType.create(viewSpotCheckPercentService.getListAllStepResult());
    }

    //环节点击按钮根据此环节查看所有所属所队统计的接口
    @RequestMapping("/getListTeamStepResultByStep")
    @ResponseBody
    public CommonReturnType getListTeamStepResultByStep(String step){
        return CommonReturnType.create(viewSpotCheckPercentService.getListTeamStepResultByStep(step));
    }

    //所属食品类型和样品的统计
    @RequestMapping("/getListAllTypeNameResult")
    @ResponseBody
    public CommonReturnType getListAllTypeNameResult(){
        return CommonReturnType.create(viewSpotCheckPercentService.getListAllTypeNameResult());
    }

    //类别统计匹配下方按钮功能
    @RequestMapping("/getListAllTypeResult")
    @ResponseBody
    public CommonReturnType getListAllTypeResult(){
        return CommonReturnType.create(viewSpotCheckPercentService.getListAllTypeResult());
    }

    //类别按钮查看当前类别下的前十名不合格样品
    @RequestMapping("/getListTypeNameResultByTypeTopTen")
    @ResponseBody
    public CommonReturnType getListTypeNameResultByType(String type){
        return CommonReturnType.create(viewSpotCheckPercentService.getListTypeNameResultByTypeTopTen(type));
    }

    //机构查看总表
    @RequestMapping("/getListAllOrgResult")
    @ResponseBody
    public CommonReturnType getListAllOrgResult(){
        return CommonReturnType.create(viewSpotCheckPercentService.getListAllOrgResult());
    }

    //机构按钮到类别查看
    @RequestMapping("/getListByOrg")
    @ResponseBody
    public CommonReturnType getListByOrg(String org){
        return CommonReturnType.create(viewSpotCheckPercentService.getListByOrg(org));
    }

    //类别按钮查看当前类别下的前十名花费最高得样品
    @RequestMapping("/getListByTypeTopTen")
    @ResponseBody
    public CommonReturnType getListByTypeTopTen(String type, String org){
        return CommonReturnType.create(viewSpotCheckPercentService.getListByTypeTopTen(type, org));
    }

    //样品不合格数topten
    @RequestMapping("/getListSampleTopTen")
    @ResponseBody
    public CommonReturnType getListSampleTopTen(){
        return CommonReturnType.create(viewSpotCheckPercentService.getListSampleTopTen());
    }

    //样品总花费数topten
    @RequestMapping("/getListNameTopTen")
    @ResponseBody
    public CommonReturnType getListNameTopTen(){
        return CommonReturnType.create(viewSpotCheckPercentService.getListNameTopTen());
    }


    //所属所队按钮到某个对所的环节统计
    @RequestMapping("/getListTeamStepSingleResultByTeam")
    @ResponseBody
    public CommonReturnType getListTeamStepSingleResultByTeam(String team){
        return CommonReturnType.create(viewSpotCheckPercentService.getListTeamStepSingleResultByTeam(team));
    }

    //环节点击按钮根据此环节查看所有所属所队统计的接口
    @RequestMapping("/getListTeamStepAllResultByStep")
    @ResponseBody
    public CommonReturnType getListTeamStepAllResultByStep(String step){
        return CommonReturnType.create(viewSpotCheckPercentService.getListTeamStepAllResultByStep(step));
    }

}
