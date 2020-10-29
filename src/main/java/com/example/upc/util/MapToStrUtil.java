package com.example.upc.util;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class MapToStrUtil {
        public static String getMapToString(Map<String,String> map){
            Set<String> keySet = map.keySet();
            //将set集合转换为数组
            String[] keyArray = keySet.toArray(new String[keySet.size()]);
            //给数组排序(升序)
            Arrays.sort(keyArray);
            //因为String拼接效率会很低的，所以转用StringBuilder
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < keyArray.length; i++) {
                // 参数值为空，则不参与签名 这个方法trim()是去空格
                if ((String.valueOf(map.get(keyArray[i]))).trim().length() > 0) {
                    sb.append(keyArray[i]).append("：").append(String.valueOf(map.get(keyArray[i])).trim());
                }
                if(i != keyArray.length-1){
                    sb.append("。");
                }
            }
            return sb.toString();
    }
}
