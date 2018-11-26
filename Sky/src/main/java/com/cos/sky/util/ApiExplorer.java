package com.cos.sky.util;
/* Java 샘플 코드 */


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ApiExplorer {
    public static String getAirportData(String depAirportId, String arrAirportId, String depPlandTime) throws IOException {
//        depAirportId = "NAARKSS";
//        arrAirportId = "NAARKPC";
//        depPlandTime = "20181127";
    	StringBuilder urlBuilder = new StringBuilder("http://openapi.tago.go.kr/openapi/service/DmstcFlightNvgInfoService/getFlightOpratInfoList"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=MSUqUpD56Llk2cHQecsJiRCwSQdGEHS0keQ5jLeCdQjZLnNUH0i47NUuUQcpj5GK0%2Bvenq7EU4yAFWQjfNxW3w%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("200", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
        urlBuilder.append("&" + URLEncoder.encode("depAirportId","UTF-8") + "=" + URLEncoder.encode(depAirportId, "UTF-8")); /*출발공항ID*/
        urlBuilder.append("&" + URLEncoder.encode("arrAirportId","UTF-8") + "=" + URLEncoder.encode(arrAirportId, "UTF-8")); /*도착공항ID*/
        urlBuilder.append("&" + URLEncoder.encode("depPlandTime","UTF-8") + "=" + URLEncoder.encode(depPlandTime, "UTF-8")); /*출발일*/
        urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*json으로 받는 쿼리스트링 추가*/    
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        // System.out.println(sb.toString());
        return sb.toString();
    }
    
	public static List<AirVO> getAirportJson(String depAirportId, String arrAirportId, String depPlandTime) throws Exception {
    	String result = getAirportData(depAirportId, arrAirportId, depPlandTime); // json처럼 생긴 String을 받아온다.
    	// json처럼 생긴 String을 json으로 만들기 --> parser사용
    	JSONParser parser = new JSONParser();
    	JSONObject jsonObj = (JSONObject) parser.parse(result); // 오브젝트로 리턴받아서 다운케스팅 --> Json Object가 된다
    	
    	// response를 뽑아오기
    	JSONObject j_response = (JSONObject)jsonObj.get("response"); // 케스팅 -> 리턴값을 정할수 없기 때문에 모든값을 다받을수 있는 오브젝트로 받는다.
    	// System.out.println(j_response);
    	
    	JSONObject j_body = (JSONObject)j_response.get("body");  // body가져오기
    	JSONObject j_items = (JSONObject)j_body.get("items");  // body가져오기
    	JSONArray a_item = (JSONArray)j_items.get("item");  // body가져오기
    	a_item.remove(0); // index 0번지 값을 제거 ---> VO길이가 다른 데이터 삭제 : gson오류방지
    	
    	//vo에 값집어넣기 : gson이용, for문 돌리기 2가지
    	// GSON을 --> 클래스로 AirVO 봐꿈
    	// gson.fromJson(j_items.toString(), AirVO.class); // GSON을  클래스로 봐꿀때 
    	// GSON을 --> 배열로 - List<AirVO> 봐꿈
    	Gson gson = new Gson();
    	// AirVO[] arr = gson.fromJson(a_item.toString(), AirVO[].class); // 배열로 받아서 처리하는 방식
    	// List로 받아서 처리하는 방식
    	List<AirVO> list = gson.fromJson(a_item.toString(), new TypeToken<List<AirVO>>(){}.getType());
    	
    	for (AirVO airVO : list) {
			System.out.println(airVO.getArrPlandTime());
		}
    	return list;
    }
    
}
