package com.example.upc.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class HttpClient {
    public static JSONObject postJSONArrayClient(String url, JSONArray params){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> formEntity = new HttpEntity<>(params.toString(), headers);
        JSONObject response = restTemplate.postForObject(url, formEntity, JSONObject.class);
        return response;
    }

    public static String postJSONObjectClient(String url, JSONObject params){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> formEntity = new HttpEntity<>(params.toString(), headers);
        String result = restTemplate.postForObject(url, formEntity, String.class);
        return result;
    }

    public static String getClient(String url) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String result=restTemplate.exchange(url, HttpMethod.GET, entity,String.class).getBody();
        return result;
    }

    public ResponseEntity<byte[]> getFormClient(String url, MultiValueMap<String, String> map) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<byte[]> response = restTemplate.postForEntity( url, request , byte[].class );
        return response;
    }

    public JSONObject PostFormClient(String url, MultiValueMap<String, String> map) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<JSONObject> response = restTemplate.postForEntity( url, request , JSONObject.class );
        return response.getBody();
    }
}
