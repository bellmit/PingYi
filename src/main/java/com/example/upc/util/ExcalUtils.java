package com.example.upc.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCell;

import java.util.Date;

/**
 * @author zcc
 * @date 2019/7/28 14:55
 */
public class ExcalUtils {
    public static String handleStringXSSF(XSSFCell xssfCell){
        if(xssfCell==null){
            return "";
        }else {
            return xssfCell.getStringCellValue();
        }
    }
    public static String handleStringHSSF(HSSFCell hssfCell){
        if(hssfCell==null){
            return "";
        }else {
            return hssfCell.getStringCellValue();
        }
    }
    public static Date handleDateHSSF(HSSFCell hssfCell){
        if(hssfCell==null){
            return null;
        }else {
            return hssfCell.getDateCellValue();
        }
    }
    public static Date handleDateXSSF(XSSFCell xssfCell){
        if(xssfCell==null){
            return null;
        }else {
            return xssfCell.getDateCellValue();
        }
    }
    public static int handleIntegerXSSF(XSSFCell xssfCell){
        if(xssfCell==null){
            return 0;
        }else {
            return (int)xssfCell.getNumericCellValue();
        }
    }
    public static int handleIntegerHSSF(HSSFCell hssfCell){
        if(hssfCell==null){
            return 0;
        }else {
            return (int)hssfCell.getNumericCellValue();
        }
    }
    public static float handleFloatXSSF(XSSFCell xssfCell){
        if(xssfCell==null){
            return 0;
        }else {
            return (float)xssfCell.getNumericCellValue();
        }
    }
    public static float handleFloatHSSF(HSSFCell hssfCell){
        if(hssfCell==null){
            return 0;
        }else {
            return (float)hssfCell.getNumericCellValue();
        }
    }
}
