package com.example.upc.controller;

import com.example.upc.common.CommonReturnType;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zcc
 * @date 2019/5/14 21:20
 */
@Controller("upload")
@RequestMapping("/upload")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class UploadController {

//    @Bean
//    MultipartConfigElement multipartConfigElement() {
//        MultipartConfigFactory factory = new MultipartConfigFactory();
//        factory.setLocation("/tmp/tomcat");
//        return factory.createMultipartConfig();
//    }
    //实现图片上传
    @RequestMapping(value = "/uploadPicture",method = {RequestMethod.POST})
    @ResponseBody
    public CommonReturnType pictureUpload(@RequestParam("file") MultipartFile file) throws IOException{
        String name = uploadFile(file,"picture");
        return CommonReturnType.create(name);
    }
    //实现视频上传
    @RequestMapping(value = "/uploadVideo",method = {RequestMethod.POST})
    @ResponseBody
    public CommonReturnType videoUpload(@RequestParam("file")MultipartFile file) throws IOException{
        String name = uploadFile(file,"video");
        return CommonReturnType.create(name);
    }

    //实现报告上传
    @RequestMapping(value = "/uploadReport",method = {RequestMethod.POST} )
    @ResponseBody
    public CommonReturnType reportUpload(@RequestParam("file")MultipartFile file) throws IOException {
        String name = uploadFile(file,"report");
        return CommonReturnType.create(name);
    }

    public String uploadFile(MultipartFile file,String type) throws IOException{
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String currentTime = dateFormat.format( now );

        String fileName=file.getOriginalFilename();
        System.out.println("源文件名："+fileName);
        File filed=new File("upload/"+type+"/"+currentTime);
        if(!filed.exists()){
            filed.mkdirs();
        }
        String filename = System.currentTimeMillis()+(int)(1+Math.random()*1000)+fileName.substring(fileName.lastIndexOf("."));
        file.transferTo(new File(filed.getAbsolutePath(),filename));
        System.out.println(currentTime+"/"+filename);
        return currentTime+"/"+filename;
    }


    //实现文件下载
    @RequestMapping(value = "/downloadFile", method=RequestMethod.GET)
    public void downFileFromServer(HttpServletResponse response, @RequestParam(name = "name") String fileName){
        //response.setHeader("content-type", "application/octet-stream");

        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(new File("upload/report",fileName)));
            //            bis = new BufferedInputStream(new FileInputStream(new File("upload/report",fileName)));
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" +  java.net.URLEncoder.encode(fileName,"UTF-8"));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @RequestMapping(value = "/downloadFileEx", method=RequestMethod.GET)
    public static void downloadExcelModle(HttpServletResponse response,@RequestParam(name = "name") String fileName) {
        //下载
        File file = new File("upload/report/"+fileName);//   1.获取要下载的文件的绝对路径
//        File file = new File("upload/report/"+fileName);//   1.获取要下载的文件的绝对路径
//        File file = new File(fileName);//   1.获取要下载的文件的绝对路径
        String newDname = fileName;     //2.获取要下载的文件名
        System.out.println(fileName);
        if (file.exists()) {  //判断文件是否存在
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/xlsx");
            try {
                response.setHeader("Content-Disposition", "attachment;filename=" + new String(newDname
                        .getBytes("UTF-8"), "ISO8859-1"));  //3.设置content-disposition响应头控制浏览器以下载的形式打开文件.特别注意，在swagger中会练吗，
                // 但是其实不会乱码。
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            byte[] buff = new byte[1024];    //5.创建数据缓冲区
            BufferedInputStream bis = null;
            OutputStream os = null;
            OutputStream outputStream = null;
            try {
                FileInputStream inputStream = new FileInputStream(file);
                outputStream = response.getOutputStream();
                IOUtils.copy(inputStream, outputStream);
                response.flushBuffer();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        else
        {
            System.out.println("1");
        }
    }
}
