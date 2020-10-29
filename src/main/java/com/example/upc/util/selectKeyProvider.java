package com.example.upc.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * @program: upc
 * @Date: 2019/4/15 20:12
 * @Author: Mr.Deng
 * @Description:
 */
public class selectKeyProvider {
    public String list2sql(Map<String,Object> para) {
        String tableName = (String) para.get("tableName");
        Integer page = (Integer) para.get("page");
        Integer number = (Integer) para.get("number");
        JSONArray jsonArray = (JSONArray) para.get("keyPoint");
        int len = jsonArray.size();
        String sql = "select * from "+tableName+" where";
        for (int i = 0; i < len; i++){
            JSONObject jsonObject = (JSONObject)jsonArray.get(i);
            if(i == 0){
                sql = sql+" "+jsonObject.getString("name")+"=\""+jsonObject.getString("value")+"\"";
            }else{
                sql = sql+" and "+jsonObject.getString("name")+"=\""+jsonObject.getString("value")+"\"";
            }
        }
        int startRecord = (page-1)*number;
        sql = sql+" limit "+startRecord+","+number;
        return sql;
    }
}
