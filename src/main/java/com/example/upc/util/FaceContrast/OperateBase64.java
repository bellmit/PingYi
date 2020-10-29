package com.example.upc.util.FaceContrast;

import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;

public class OperateBase64 {
    public static String deleteHead(String picSrc){
        String target = "data:image/.*;base64,";
        return picSrc.replaceAll(target,"");
    }

    public static byte[] base642Image(String imgStr) {
        //对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) //图像数据为空
            return "pic null".getBytes();
        BASE64Decoder decoder = new BASE64Decoder();
        try
        {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for(int i=0;i<b.length;++i)
            {
                if(b[i]<0)
                {//调整异常数据
                    b[i]+=256;
                }
            }
            //生成jpeg图片
//            String imgFilePath = "D:\\tupian\\new.jpg";//新生成的图片
//            OutputStream out = new FileOutputStream(imgFilePath);
//            out.write(b);
//            out.flush();
//            out.close();
            return b;
        }
        catch (Exception e)
        {
            return "decoder fail".getBytes();
        }
    }


    public static String imageToBase64(MultipartFile file){
        String result = null;
        byte[] data = null;
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            data = new byte[inputStream.available()];
            inputStream.read(data);
//            result = Base64.encodeToString(data);
            result = Base64.getEncoder().encodeToString(data);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null !=inputStream){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }
    public static String imageToBase64(File file){
        String result = null;
        byte[] data = null;
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            int len;
            byte[] buffer=new byte[1024];
            while ((len=inputStream.read(buffer))!=-1){
                baos.write(buffer,0,len);
            }
            data=baos.toByteArray();
            //data = new byte[inputStream.available()];
            //inputStream.read(data);
            inputStream.close();
            baos.close();
//            result = Base64.encodeToString(data);
            result = Base64.getEncoder().encodeToString(data);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null !=inputStream){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }
    public static String GetImageStr(String path)
    {
        //将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        //待处理的图片
        String imgFile = path;
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try
        {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        //返回Base64编码过的字节数组字符串
        return encoder.encode(data);
    }

    public static File MultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File toFile = null;
        if(multipartFile.equals("")||multipartFile.getSize()<=0){
            multipartFile = null;
        }else {
            InputStream ins = null;
            ins = multipartFile.getInputStream();
            toFile = new File(multipartFile.getOriginalFilename());
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }
    public static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
