package com.example.upc.util;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

public class WeixinUtilTest {
//	wx78b3e6073b642844
//			b2799fcf23bd07fed168d7b13297241d

	public static String getAccessToken() {
		String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx9c5553288e6c8e03&secret=f2980c5e6a6735f9011675bfcc6b47a8";
		//获取accessToken
		String accessTokenStr = accessTokenRequest(accessTokenUrl);
		System.out.println(accessTokenStr);
		JSONObject json = JSONObject.parseObject(accessTokenStr);
		System.out.println(json.getString("access_token"));
		return json.getString("access_token");
	}

	public static String accessTokenRequest(String urlStr){
		HttpURLConnection connection=null;
		String tempStr = null;
		try{
			URL url=new URL(urlStr);
			StringBuffer bankXmlBuffer=new StringBuffer();
			//创建URL连接，提交到数据，获取返回结果
			connection=(HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			connection.setRequestProperty("User-Agent","directclient");
			PrintWriter out=new PrintWriter(new OutputStreamWriter(connection.getOutputStream(),"GBK"));
			out.println();
			out.close();
			BufferedReader in=new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
			String inputLine;
			while((inputLine = in.readLine())!=null){
				bankXmlBuffer.append(inputLine);
			}
			in.close();
			tempStr = bankXmlBuffer.toString();
		}
		catch(Exception e)
		{
			System.out.println("发送GET请求出现异常！"+e);
			e.printStackTrace();
		}finally{
			if(connection!=null)
				connection.disconnect();
		}
		return tempStr;
	}


	public static String imgUpload(String filePath) throws IOException {
		String result = null;
		File file = new File(filePath);
		if (!file.exists() || !file.isFile()) {
			throw new IOException("文件不存在");
		}
		//第一部分
		String ACCESS_TOKEN = getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=" + ACCESS_TOKEN+ "&type=thumb";
		URL urlObj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
		//设置关键值
		con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false); // post方式不能使用缓存
		// 设置请求头信息
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");
		// 设置边界
		String BOUNDARY = "----------" + System.currentTimeMillis();
		con.setRequestProperty("Content-Type", "multipart/form-data; boundary="+ BOUNDARY);
		// 请求正文信息
		// 第一部分：
		StringBuilder sb = new StringBuilder();
		sb.append("--"); // 必须多两道线
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");
		byte[] head = sb.toString().getBytes("utf-8");
		// 获得输出流
		OutputStream out = new DataOutputStream(con.getOutputStream());
		// 输出表头
		out.write(head);
		// 文件正文部分
		// 把文件已流文件的方式 推入到url中
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while ((bytes = in.read(bufferOut)) != -1) {
			out.write(bufferOut, 0, bytes);
		}
		in.close();
		// 结尾部分
		byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
		out.write(foot);
		out.flush();
		out.close();
		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		try {
			// 定义BufferedReader输入流来读取URL的响应
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				//System.out.println(line);
				buffer.append(line);
			}
			if(result==null){
				result = buffer.toString();
			}
		} catch (IOException e) {
			System.out.println("发送POST请求出现异常！" + e);
			e.printStackTrace();
			throw new IOException("数据读取异常");
		} finally {
			if(reader!=null){
				reader.close();
			}
		}
		JSONObject jsonObj = JSONObject.parseObject(result);
//		String img_src = jsonObj.getString("url");
		System.out.println(jsonObj);
		return result;
	}

	public static String news() throws IOException{
		String ACCESS_TOKEN = getAccessToken();
		String uploadurl = "https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=" + ACCESS_TOKEN;

		Map map = new HashMap<String, String>();
		map.put("thumb_media_id", JSONObject.parseObject(imgUpload("upload/picture/1234567890.jpg")).getString("thumb_media_id"));
		map.put("author", "xiaojin");
		map.put("title", "上传群发测试");
		map.put("content_source_url", "www.eupwood.com");
		map.put("content", "这是测试内容1");
		map.put("digest", "测试用描述");
		map.put("show_cover_pic", "1");

		Map map1 = new HashMap<String, String>();
		map1.put("thumb_media_id", JSONObject.parseObject(imgUpload("upload/picture/1234567890.jpg")).getString("thumb_media_id"));
		map1.put("author", "xiaojin");
		map1.put("title", "上传群发测试1");
		map1.put("content_source_url", "www.eupwood.com");
		map1.put("content", "这是测试内容2");
		map1.put("digest", "测试用描述1");
		map1.put("show_cover_pic", "0");
		List list = new ArrayList<>();
		list.add(map);
		list.add(map1);
		Map allMap = new HashMap<String, String>();
		allMap.put("articles", list);
		System.out.println(allMap);
		String a= JSONUtils.toJSONString(allMap);
		JSONObject jsonObj = JSONObject.parseObject(a);
		System.out.println(jsonObj);
		String result = HttpClient.postJSONObjectClient(uploadurl,jsonObj);
		System.out.println(result);
		return result;
	}

	public static String jsonInfoUpload(String data){
		org.apache.commons.httpclient.HttpClient client = new org.apache.commons.httpclient.HttpClient();
		String ACCESS_TOKEN = getAccessToken();
		String uploadurl = "https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=" + ACCESS_TOKEN;
		String para = "media_id";
		PostMethod post = new PostMethod(uploadurl);
		post.setRequestHeader(
				"User-Agent",
				"Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:30.0) Gecko/20100101 Firefox/30.0");
		post.setRequestHeader("Host", "file.api.weixin.qq.com");
		post.setRequestHeader("Connection", "Keep-Alive");
		post.setRequestHeader("Cache-Control", "no-cache");
		post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		String receiveInfo = null;
		try {
			post.setRequestBody(data);
			int status = client.executeMethod(post);
			if (status == HttpStatus.SC_OK) {
				String responseContent = post.getResponseBodyAsString();
				JSONObject jsonobj = JSONObject.parseObject(responseContent);
				System.out.println(jsonobj.toString());
				// 正确 { "type":"news",
				// "media_id":"CsEf3ldqkAYJAU6EJeIkStVDSvffUJ54vqbThMgplD-VJXXof6ctX5fI6-aYyUiQ","created_at":1391857799}
				receiveInfo = jsonobj.getString(para);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return receiveInfo;
		}
	}


	public static String newsGo() throws IOException{
		String news = news();
		String ACCESS_TOKEN = getAccessToken();
		JSONObject jsonobj = JSONObject.parseObject(news);
		String media_id = jsonobj.getString("media_id");

		String sendurl = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token="+ ACCESS_TOKEN;

		Map map = new HashMap<String, String>();
		map.put("is_to_all", false);
		map.put("tag_id", 2);

		Map map1 = new HashMap<String, String>();
		map1.put("media_id", "hAHnERWod4VMo4Zd9vujtC0lsnAsy1M37WxbKPPngltQPIIuR-sSZ2mDs4FMA2sx");

		Map allMap = new HashMap<String, String>();
		allMap.put("filter", map);
		allMap.put("mpnews", map1);
		allMap.put("msgtype", "mpnews");
		allMap.put("send_ignore_reprint", 0);

		String a= JSONUtils.toJSONString(allMap);
		JSONObject jsonObj = JSONObject.parseObject(a);
		System.out.println(jsonObj);
		String result = HttpClient.postJSONObjectClient(sendurl,jsonObj);
		System.out.println(result);
		return result;
	}

}
