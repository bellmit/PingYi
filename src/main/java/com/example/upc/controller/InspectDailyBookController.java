package com.example.upc.controller;

import com.example.upc.common.CommonReturnType;
import com.example.upc.dataobject.InspectDailyBook;
import com.example.upc.service.InspectDailyBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zcc
 * @date 2019/9/20 20:17
 */
@Controller
@RequestMapping("/inspect/dailyBook")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class InspectDailyBookController {
    @Autowired
    private InspectDailyBookService inspectDailyBookService;

    @RequestMapping("/getByCheckId")
    @ResponseBody
    public CommonReturnType getByCheckId(int checkId){
        return CommonReturnType.create(inspectDailyBookService.getByDailyFoodId(checkId));
    }
    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(InspectDailyBook inspectDailyBook){
        inspectDailyBookService.insert(inspectDailyBook);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(InspectDailyBook inspectDailyBook){
        inspectDailyBookService.update(inspectDailyBook);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id){
        inspectDailyBookService.delete(id);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/getCheckBookNameList")
    @ResponseBody
    public CommonReturnType getCheckBookNameList(int dailyFoodId,String typeUrl){
        return CommonReturnType.create(inspectDailyBookService.getCheckBookNameList(dailyFoodId,typeUrl));
    }
}
