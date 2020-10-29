package com.example.upc.controller;

import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.MorningCheckOutputParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.dataobject.SupervisionCaMorningCheck;
import com.example.upc.service.SupervisionCaMorningCheckService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author zcc
 * @date 2019/6/26 20:19
 */
@Controller
@RequestMapping("/supervision/morningCheck")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class SupervisionCaMorningCheckController {
    @Autowired
    private SupervisionCaMorningCheckService supervisionCaMorningCheckService;

    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(PageQuery pageQuery){
        return CommonReturnType.create(supervisionCaMorningCheckService.getPage(pageQuery));
    }

    @RequestMapping("/getByCaId")
    @ResponseBody
    public CommonReturnType getByCaId(PageQuery pageQuery,@RequestParam("id") int id){
        return CommonReturnType.create(supervisionCaMorningCheckService.getPageByCaId(pageQuery,id));
    }

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(SupervisionCaMorningCheck supervisionCaMorningCheck){
        supervisionCaMorningCheckService.insert(supervisionCaMorningCheck);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(SupervisionCaMorningCheck supervisionCaMorningCheck){
        supervisionCaMorningCheckService.update(supervisionCaMorningCheck);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id){
        supervisionCaMorningCheckService.delete(id);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/output")
    @ResponseBody
    public void output(HttpServletResponse response, @RequestParam(name = "start")String start, @RequestParam(name = "end")String end) throws Exception{
        List<MorningCheckOutputParam> outputList=supervisionCaMorningCheckService.output(start,end);
        ClassPathResource resource = new ClassPathResource("templates/demo.xlsx");
        InputStream fileInputStream = resource.getInputStream();
        XSSFWorkbook workBook = new XSSFWorkbook(fileInputStream);
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss" );
        try {
            XSSFSheet sheet1 = workBook.getSheetAt(0);// 获取excel表的第一个sheet:sheet1
            List<MorningCheckOutputParam> table1 = outputList; // 获取嵌套的第一个List块

            /**
             * 填入数据
             */
            int startindex = 1; // 开始的行
            for (int i = 0; i < table1.size(); i++) {
                Row row = sheet1.createRow(i + startindex);
                row.createCell(0);
                row.getCell(0).setCellValue(table1.get(i).getEnterprise());
                row.createCell(1);
                row.getCell(1).setCellValue(table1.get(i).getEmployee());
                row.createCell(2);
                row.getCell(2).setCellValue(table1.get(i).getSex());
                row.createCell(3);
                row.getCell(3).setCellValue(table1.get(i).getNumber());
                row.createCell(4);
                row.getCell(4).setCellValue(table1.get(i).getHealth());
                row.createCell(5);
                row.getCell(5).setCellValue(sdf.format(table1.get(i).getCheckDate()));
                row.createCell(6);
                row.getCell(6).setCellValue(table1.get(i).getRecord());

            }

            try {
                response.setContentType("application/xlsx");
                response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode( "职员晨检记录表"+".xlsx","UTF-8"));
                OutputStream os = response.getOutputStream();
                workBook.write(os);
                os.flush();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 所要生成的文件路径和名称
//            String b2_Copy = "C:\\Users\\web\\Desktop/【已替换】B2企业驾驶人员动态信息登记台账" + ".xlsx";
//
//            try (FileOutputStream out = new FileOutputStream(b2_Copy)) {
//                workBook.write(out);
//                System.out.println("B2生成完毕！");
//                out.flush(); // 手动刷新流
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
