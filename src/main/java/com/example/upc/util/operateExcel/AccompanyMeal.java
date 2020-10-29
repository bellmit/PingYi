package com.example.upc.util.operateExcel;

import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class AccompanyMeal {
//        public static String path = "/Users/weixj/Desktop/wph/IMDY/upload/";

//    上传到服务器用以下路径
       // public static String path = "C:\\Users\\my\\Desktop\\weixin2\\IMDY\\upload\\";

//    上传到服务器用以下路径
    public static String path = "upload/";

    public static void main(String[] args) throws Exception {

        Map<String, Object> data1 = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> picmap = new HashMap<>();

        data1.put("${date}","2020-09-27");
        data.put("${name}","***学校");
        data.put("${person}","宋");
        data.put("${meal}","午餐");
        data.put("${hygiene}","良好");
        data.put("${quality}","良好");
        data.put("${account}","良好");
        data.put("${attitude}","良好");
        data.put("${standard}","良好");
        data.put("${satisfaction}","良好");

//        picmap.put("${pic1}","/Users/weixj/Desktop/wph/IMDY/upload/picture/sign.png");
//        picmap.put("${pic2}","/Users/weixj/Desktop/wph/IMDY/upload/picture/sign.png");
//        picmap.put("${pic3}","/Users/weixj/Desktop/wph/IMDY/upload/picture/sign.png");

        picmap.put("${pic1}","");
        picmap.put("${pic2}","");
        picmap.put("${pic3}","");

        getWord(data1,data,picmap,1000);

    }

    /**
     * key值参考对应模板
     * @param data1 表格外数据，比如日期
     * @param data2 表格内数据
     * @param picmap 图片，若没有图片，则放空字符串""
     * @param businessId 企业id
     * @throws Exception
     */
    public static void getWord(Map<String, Object> data1,Map<String, Object> data2, Map<String, Object> picmap, Integer businessId) throws Exception {
        try (FileInputStream is = new FileInputStream(path+"template/校园陪餐记录表.docx"); XWPFDocument document = new XWPFDocument(is)) {
            //替换掉表格之外的文本对象(仅限文本)
            changeText(document, data1);
            //替换表格内的文本对象
            changeTableText(document, data2);

            //替换图片
//            changePic(document, picmap,270);
            changeTablePic(document, picmap);
//            changePic2(document,picmap2,270);
//            changeTableMulPic01(document,test.size(),test);



            Date now = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
            String currentTime = dateFormat.format( now );
            File filed = new File(path+"/standingBook/"+currentTime);
            if(!filed.exists()){
                filed.mkdirs();
            }

            try (FileOutputStream out = new FileOutputStream(path+"/standingBook/"+currentTime+"/校园陪餐记录表"+businessId+".docx" )) {
                document.write(out);
                System.out.println("生成完毕");
                out.flush();
            }
        }
    }

    public static void changeTableText(XWPFDocument document, Map<String, Object> data) {
        List<XWPFTable> tableList = document.getTables();

        //循环所有需要进行替换的文本，进行替换
        for (int i = 0; i < tableList.size(); i++) {
            XWPFTable table = tableList.get(i);
            if (checkText(table.getText())) {
                List<XWPFTableRow> rows = table.getRows();
                System.out.println("简单表格替换：" + rows);
                //遍历表格,并替换模板
                eachTable(document, rows, data);
            }
        }
    }

    public static void changeTablePic(XWPFDocument document, Map<String, Object> pic) throws Exception {
        List<XWPFTable> tableList = document.getTables();

        //循环所有需要进行替换的文本，进行替换
        for (int i = 0; i < tableList.size(); i++) {
            XWPFTable table = tableList.get(i);
            if (checkText(table.getText())) {
                List<XWPFTableRow> rows = table.getRows();
                System.out.println("简单表格替换：" + rows);
                //遍历表格,并替换模板
                eachTablePic(document, rows, pic);
            }
        }
    }

    public static void eachTablePic(XWPFDocument document, List<XWPFTableRow> rows, Map<String, Object> pic) throws Exception {
        for (XWPFTableRow row : rows) {
            List<XWPFTableCell> cells = row.getTableCells();
            for (XWPFTableCell cell : cells) {
                //判断单元格是否需要替换
                if (checkText(cell.getText())) {
                    //System.out.println("cell:" + cell.getText());
                    List<XWPFParagraph> paragraphs = cell.getParagraphs();
                    for (XWPFParagraph paragraph : paragraphs) {
                        List<XWPFRun> runs = paragraph.getRuns();
                        for (XWPFRun run : runs) {
                            Object ob = changeValue(run.toString(), pic);
                            if (ob instanceof String) {
//                                System.out.println("run:" + "'" + run.toString() + "'");
                                if (pic.containsKey(run.toString())) {
//                                    System.out.println("run:" + run.toString() + "替换为" + (String) ob);
                                    run.setText("", 0);
                                    if(!((String) ob).equals("")) {
                                        try (FileInputStream is = new FileInputStream((String) ob)) {
                                            run.addPicture(is, XWPFDocument.PICTURE_TYPE_PNG, (String) ob, Units.toEMU(150), Units.toEMU(100));
                                        }
                                    }
                                } else {
                                    System.out.println("'" + run.toString() + "'不匹配");
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static boolean checkText(String text) {
        boolean check = false;
        if (text.indexOf("$") != -1) {
            check = true;
        }
        return check;
    }

    public static void eachTable(XWPFDocument document, List<XWPFTableRow> rows, Map<String, Object> textMap) {
        for (XWPFTableRow row : rows) {
            List<XWPFTableCell> cells = row.getTableCells();
            for (XWPFTableCell cell : cells) {
                //判断单元格是否需要替换
                if (checkText(cell.getText())) {
                    //System.out.println("cell:" + cell.getText());
                    List<XWPFParagraph> paragraphs = cell.getParagraphs();
                    for (XWPFParagraph paragraph : paragraphs) {
                        List<XWPFRun> runs = paragraph.getRuns();
                        for (XWPFRun run : runs) {

                            Object ob = changeValue(run.toString(), textMap);
                            if (ob instanceof String) {

//                                System.out.println("run:" + "'" + run.toString() + "'");
                                if (textMap.containsKey(run.toString())) {
//                                    System.out.println("run:" + run.toString() + "替换为" + (String) ob);
                                    run.setText((String) ob, 0);
                                } else {
                                    System.out.println("'" + run.toString() + "'不匹配");
                                }

                            }
                        }
                    }
                }
            }
        }
    }

    public static Object changeValue(String value, Map<String, Object> textMap) {
        Set<Map.Entry<String, Object>> textSets = textMap.entrySet();
        Object valu = "";
        for (Map.Entry<String, Object> textSet : textSets) {
            //匹配模板与替换值 格式${key}
            String key = textSet.getKey();
            if (value.indexOf(key) != -1) {
                valu = textSet.getValue();
            }
        }
        return valu;
    }

    public static void changeText(XWPFDocument document, Map<String, Object> textMap) {
        //获取段落集合
        List<XWPFParagraph> paragraphs = document.getParagraphs();

        for (XWPFParagraph paragraph : paragraphs) {
            //判断此段落时候需要进行替换
            String text = paragraph.getText();

            if (checkText(text)) {
//                System.out.println(text);
                List<XWPFRun> runs = paragraph.getRuns();
                for (XWPFRun run : runs) {
                    //替换模板原来位置
                    Object ob = changeValue(run.toString(), textMap);
                    System.out.println("run:"+run.toString()+" ob:"+ob);
//                    System.out.println("段落：" + run.toString());
                    if (ob instanceof String) {
                        if (textMap.containsKey(run.toString())) {
                            run.setText((String) ob, 0);
                        }
                    }
                }
            }
        }
    }

}
