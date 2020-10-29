package com.example.upc.util;

import java.util.List;

public class ListSubUtil {
    public static <T> List<T> sub(List<T> list, Integer indexNum){
        Integer total = list.size();
        Integer start = (indexNum -1) * 10;
        Integer end = indexNum * 10;
        System.out.println(start);
        System.out.println(end);
        try {
            if (end < total){
                list = list.subList(start,end);
            }
            else if (start > total){
                list = list.subList(total - 10,total);
            }
            else{
                list = list.subList(start,total);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
