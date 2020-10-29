package com.example.upc.controller;

import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.dataobject.InspectLllegality;
import com.example.upc.service.InspectLllegalityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zcc
 * @date 2019/9/13 13:44
 */
@Controller
@RequestMapping("/inspect/legal")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class InspectLllegalityController {
    @Autowired
    private InspectLllegalityService inspectLllegalityService;

    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(PageQuery pageQuery){
        return CommonReturnType.create(inspectLllegalityService.getPage(pageQuery));
    }

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(InspectLllegality inspectLllegality) {
        inspectLllegalityService.insert(inspectLllegality);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(InspectLllegality inspectLllegality){
        inspectLllegalityService.update(inspectLllegality);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType deleteDept(@RequestParam("id") int id){
        inspectLllegalityService.delete(id);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/importExcel")//导入excel
    @ResponseBody
    public CommonReturnType importExcel(MultipartFile file) {
        String fileName = file.getOriginalFilename();
//
            if(fileName.matches("^.+\\.(?i)(xls)$")){//03版本excel,xls
               inspectLllegalityService.importExcel(file,3);
            }else if (fileName.matches("^.+\\.(?i)(xlsx)$")){//07版本,xlsx
                inspectLllegalityService.importExcel(file,7);
            }

        return CommonReturnType.create(null);
    }
}
