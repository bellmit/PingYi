package com.example.upc.controller;

import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.dataobject.SupervisionCaTransfer;
import com.example.upc.service.SupervisionCaTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zcc
 * @date 2019/6/26 20:26
 */
@Controller
@RequestMapping("/supervision/caTransfer")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class SupervisionCaTransferController {
    @Autowired
    private SupervisionCaTransferService supervisionCaTransferService;
    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(PageQuery pageQuery){
        return CommonReturnType.create(supervisionCaTransferService.getPage(pageQuery));
    }

    @RequestMapping("/getByCaId")
    @ResponseBody
    public CommonReturnType getByCaId(PageQuery pageQuery,@RequestParam("id") int id){
        return CommonReturnType.create(supervisionCaTransferService.getPageByCaId(pageQuery,id));
    }

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(SupervisionCaTransfer supervisionCaTransfer){
        supervisionCaTransferService.insert(supervisionCaTransfer);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(SupervisionCaTransfer supervisionCaTransfer){
        supervisionCaTransferService.update(supervisionCaTransfer);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id){
        supervisionCaTransferService.delete(id);
        return CommonReturnType.create(null);
    }
}
