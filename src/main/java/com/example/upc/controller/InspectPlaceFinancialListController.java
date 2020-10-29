package com.example.upc.controller;

import com.alibaba.fastjson.JSON;
import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.InspectPlaceFinancialListParam;
import com.example.upc.dataobject.InspectPlaceFinancialList;
import com.example.upc.dataobject.InspectThingList;
import com.example.upc.service.InspectPlaceFinancialListService;
import com.example.upc.util.WordTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zcc
 * @date 2019/8/31 20:07
 * 清单
 */
@Controller
@RequestMapping("/inspect/financial")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class InspectPlaceFinancialListController {
    @Autowired
    private InspectPlaceFinancialListService inspectPlaceFinancialListService;

    @RequestMapping("/getByParentId")
    @ResponseBody
    public CommonReturnType getByParentId(int id){
        return CommonReturnType.create(inspectPlaceFinancialListService.getByParentId(id));
    }

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(@RequestBody String json){
        InspectPlaceFinancialListParam inspectPlaceFinancialListParam = JSON.parseObject(json,InspectPlaceFinancialListParam.class);
        inspectPlaceFinancialListService.insert(inspectPlaceFinancialListParam);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(@RequestBody String json){
        InspectPlaceFinancialListParam inspectPlaceFinancialListParam = JSON.parseObject(json,InspectPlaceFinancialListParam.class);
        inspectPlaceFinancialListService.update(inspectPlaceFinancialListParam);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id){
        inspectPlaceFinancialListService.delete(id);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/download")
    public void pointDownload(HttpServletResponse response, @RequestParam(name = "checkId")int checkId){
        InspectPlaceFinancialListParam inspectPlaceFinancialListParam = inspectPlaceFinancialListService.getByParentId(checkId);
        List<InspectThingList> inspectThingListList = inspectPlaceFinancialListParam.getInspectThingListList();
        Map<String, Object> wordDataMap = new HashMap<String, Object>();// 存储报表全部数据
        Map<String, Object> parametersMap = new HashMap<String, Object>();// 存储报表中不循环的数据
        Map<String, Object> picMap = new HashMap<String, Object>();// 图片
        SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy年MM月dd日" );

        List<Map<String, Object>> table = new ArrayList<Map<String, Object>>();
        int i=1;
        for(InspectThingList inspectThingList :inspectThingListList){
            Map<String, Object> map=new HashMap<>();
            map.put("id", i);
            map.put("nameAddress", inspectThingList.getNameAddress());
            map.put("satndardTypeAdress", inspectThingList.getSatndardTypeAdress());
            map.put("unit", inspectThingList.getUnit());
            map.put("number", inspectThingList.getNumber());
            map.put("remark", inspectThingList.getRemark());
            i++;
            table.add(map);
        }
        parametersMap.put("fileId", inspectPlaceFinancialListParam.getBookNumber());
        parametersMap.put("${date}", sdf.format(inspectPlaceFinancialListParam.getOperateTime()));
        picMap.put("$pic1", inspectPlaceFinancialListParam.getFirstExecutivePerson());
        picMap.put("$pic2", inspectPlaceFinancialListParam.getSecondExecutivePerson());

        wordDataMap.put("table", table);
        wordDataMap.put("parametersMap", parametersMap);
        wordDataMap.put("picMap", picMap);
        try {
            // 读取word模板
            ClassPathResource resource = new ClassPathResource("templates/FinancialList.docx");
            InputStream fileInputStream = resource.getInputStream();
            WordTemplate template = new WordTemplate(fileInputStream);
            // 替换数据
            template.replaceDocument(wordDataMap);

            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode( inspectPlaceFinancialListParam.getConcernedPerson()+"清单.docx","UTF-8"));
            OutputStream os = response.getOutputStream();
            //生成文件
            template.getDocument().write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
