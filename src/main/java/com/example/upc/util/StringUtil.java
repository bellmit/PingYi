package com.example.upc.util;

import com.google.common.base.Splitter;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author zcc
 * @date 2019/4/12 11:40
 */
public class StringUtil {
    public static List<Integer> splitToListInt(String str) {
        List<String> strList = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(str);
        Pattern pattern = Pattern.compile("[0-9]*");
        List<String> stringList = strList.stream().filter(strItem ->pattern.matcher(strItem).matches()).collect(Collectors.toList());
        List<Integer> intList = stringList.stream().map(strItem -> Integer.parseInt(strItem)).collect(Collectors.toList());
        return intList;
    }
}
