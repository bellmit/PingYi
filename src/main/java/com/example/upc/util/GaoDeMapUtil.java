package com.example.upc.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.upc.controller.param.GaodeLocation;
import com.example.upc.controller.param.Geocodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


/**
 * 高德地图测试
 *
 * @author cp
 *
 */
public class GaoDeMapUtil {
    private static final Logger logger = LoggerFactory.getLogger(GaoDeMapUtil.class);

    // 高德应用的地址
    private static String gaodeAppID = "389880a06e3f893ea46036f030c94700";

    // 地理编码地址
    private static String map_codeurl = "http://restapi.amap.com/v3/geocode/geo?parameters";

    private static String map_disurl ="https://restapi.amap.com/v3/direction/walking";
//https调用改为https
    public static void main(String[] args) {
        GaoDeMapUtil gdm = new GaoDeMapUtil();
        GaodeLocation result = gdm.getLocatoin("明湖小区东区三区1号楼");
        System.out.println(result.getGeocodes().get(0).getLocation());
    }
    /**
     * 输入地址返回识别后的信息
     *
     * @param address
     * @return 返回的类gaodelocation，详见类
     */
    public GaodeLocation getLocatoin(String address) {
        GaodeLocation location = null;
        if (address != null) {
            try {
                location = new GaodeLocation();
                String url = map_codeurl.replace("parameters", "");
                String params = "key=" + gaodeAppID + "&address=" + address+"&city=东营";
                logger.info("高德地图params:" + params);
                String result = sendPost(url, params);

                logger.info("高德地图返回结果:" + result);
                JSONObject jsonObject = JSONObject.parseObject(result);

                // 解析json
                location.setStatus(jsonObject.getString("status"));
                location.setInfo(jsonObject.getString("info"));
                location.setCount(jsonObject.getString("count"));
                List<Geocodes> geocodes = new ArrayList<>();
                JSONArray jsonArray = jsonObject.getJSONArray("geocodes");
                // 遍历解析出来的结果
                if ((jsonArray != null) && (jsonArray.size() > 0)) {
                    JSONObject jo = (JSONObject) jsonArray.get(0);
                    Geocodes go = new Geocodes();
                    go.setFormatted_address(jo.getString("formatted_address"));
                    go.setProvince(jo.getString("province"));
                    go.setCitycode(jo.getString("citycode"));
                    go.setCity(jo.getString("city"));
                    go.setDistrict(jo.getString("district"));
                    // 地址所在的乡镇
                    JSONArray ts = jo.getJSONArray("township");
                    if (ts != null && ts.size() > 0) {
                        go.setTownship(ts.getString(0));
                    }
                    // 地址编号
                    go.setAdcode(jo.getString("adcode"));
                    // 街道
//                    JSONArray jd = jo.getJSONArray("street");
//                    if (jd != null && jd.size() > 0) {
//                        go.setStreet(jd.getString(0));
//                    }
                    // 号码
//                    JSONArray hm = jo.getJSONArray("number");
//                    if (hm != null && hm.size() > 0) {
//                        go.setStreet(hm.getString(0));
//                    }
                    go.setLocation(jo.getString("location"));
                    go.setLevel(jo.getString("level"));
                    geocodes.add(go);
                }
                location.setGeocodes(geocodes);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return location;
    }

    public String getDistance(String start, String end) {
        String distance = null;
        try {
            String params = "key=" + gaodeAppID + "&origin=" + start+"&destination="+ end;
            String result = sendGet(map_disurl, params);
            JSONObject jsonResult = JSON.parseObject(result);
            JSONObject route = jsonResult.getJSONObject("route");
            distance = route.getJSONArray("paths").getJSONObject(0).getString("distance");
            } catch (Exception e) {
                e.printStackTrace();
        }
        return distance;
    }

    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            logger.info("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }


    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                logger.info(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            logger.info("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
}