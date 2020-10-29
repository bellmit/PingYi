package com.example.upc.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateComparedUtil {
    public int DateCompared (Date date1, Date date2){
        int compareTo = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            compareTo = date1.compareTo(date2);
            System.out.println(compareTo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return compareTo;
    }
}
