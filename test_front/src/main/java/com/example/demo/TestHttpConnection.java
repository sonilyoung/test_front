package com.example.demo;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
 
public class TestHttpConnection {
    //샘플용 코드 제작
 
    public static void HttpURLConnection(String strURL, String strParams) throws JSONException {
    	Weather w = new Weather(); 
		w.setTemperature((double) 26);
		w.setAddress("구로디지털단지");
		w.setWeatherImgUrl("/home/jun/apps/riskfree/webapps/static_file/fine.png");         	
    	
        try {
            URL url = new URL(strURL + "?" + strParams); //get 방식은 parameter를 URL에 묶어서 보낸다.
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            
            conn.setRequestMethod("GET");
 
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            conn.setRequestProperty("Accept-Language", "ko-kr");
            conn.setRequestProperty("Access-Control-Allow-Origin", "*");
            conn.setRequestProperty("Content-Type", "application/json");
 
            conn.setConnectTimeout(10000); // 커넥션 timeout 10초
            conn.setReadTimeout(5000); //컨텐츠 조회시 timeout 5초
 
            Charset charset = Charset.forName("UTF-8");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
            
            String inputLine;
            StringBuffer response = new StringBuffer();
            
            while((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            
            JSONObject jsonData = new JSONObject(response.toString());
            System.out.println(jsonData);
            Map<String, Object> map =  new ObjectMapper().readValue(jsonData.toString(), Map.class);
            Map<String, Object> coord = (Map<String, Object>) map.get("coord");
            if(coord!=null) {
            	w.setLatitude((Double)coord.get("lon"));
                w.setLongitude((Double)coord.get("lat"));	
            }
            
            List<Map<String, Object>> weather = (List<Map<String, Object>>) map.get("weather");
            if(weather!=null) {
            	System.out.println(weather.get(0).get("id"));
            	/*
            	 * 맑음 fine.png -> 800
					구름 cloudy.png -> 80x
					구름낀맑음 partially_cloudy.png-> 7xx
					비 rainy.png-> 5xx, 3xx, 2xx
					눈 snowy.png-> 6xx
            	 * */
            	
            	String targetStr = weather.get(0).get("id").toString().substring(0, 1);
            	System.out.println("substring : "+targetStr);
            	
            	if(weather.get(0).get("id").equals("800")) {//맑음
            		w.setWeatherImgUrl(WeatherType.FINE.getUrl());
            	}else if(targetStr.equals("7")){//구름낀 맑음
            		w.setWeatherImgUrl(WeatherType.PARTIALLY_CLOUDY.getUrl());
            	}else if(targetStr.equals("5") || targetStr.equals("3") || targetStr.equals("2")){//비
            		w.setWeatherImgUrl(WeatherType.RAINY.getUrl());
            	}else if(targetStr.equals("6")){//눈
            		w.setWeatherImgUrl(WeatherType.SNOWY.getUrl());
            	}else {//구름
            		w.setWeatherImgUrl(WeatherType.CLOUDY.getUrl());
            	}
            }
            
            Map<String, Object> main = (Map<String, Object>) map.get("main");
            System.out.println();
            if(weather!=null) {
            	w.setTemperature((Double) main.get("temp"));
            }
            //jsonData.get("coord") // lon, lat
            //jsonData.get("weather") // id
            //jsonData.get("main") //temp 
            
            System.out.println(w);
            
        } catch (MalformedURLException e) {
            //URL
            e.printStackTrace();
        } catch (IOException e) {
            //HttpURLConnection
            e.printStackTrace();
        } catch (Exception e) {
        	e.printStackTrace();
        }
        
    }
    
    public void HttpURLConnectionPost(String strURL, String strParams) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            
            conn.setRequestMethod("POST");
            //con.setRequestProperty("Authorization", alpha);
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            conn.setRequestProperty("Accept-Language", "ko-kr");
            conn.setRequestProperty("Access-Control-Allow-Origin", "*");
            conn.setRequestProperty("Content-Type", "application/json");
            
            conn.setConnectTimeout(10000); // 커넥션 timeout 10초
            conn.setReadTimeout(5000); //컨텐츠 조회시 timeout 5초
            
            conn.setDoOutput(true); //항상 갱신된 내용 가져오도록 true로 설정
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(strParams); //파라미터 write
            wr.flush();
            wr.close();
            
            //int responseCode = conn.getResponseCode();
            
            Charset charset = Charset.forName("UTF-8");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
            
            String inputLine;
            StringBuffer response = new StringBuffer();
            
            while((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            
            in.close();
            
            System.out.println(response.toString());
            
            
        } catch (MalformedURLException e) {
            //URL
            e.printStackTrace();
        } catch (IOException e) {
            //HttpURLConnection
            e.printStackTrace();
        }
        
    }
 
    public void HttpsURLConnectionPost(String strURL, String strParams) {
        //HttpsURLConnection은 HttpURLConnection을 상속 받는다.
        //사용 방식은 동일하나 http url과 https url이 다른 부분이다.
        
        try {
            URL url = new URL(strURL);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            
            conn.setRequestMethod("POST");
            //con.setRequestProperty("Authorization", alpha);
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            conn.setRequestProperty("Accept-Language", "ko-kr");
            conn.setRequestProperty("Access-Control-Allow-Origin", "*");
            conn.setRequestProperty("Content-Type", "application/json");
            
            conn.setConnectTimeout(10000); // 커넥션 timeout 10초
            conn.setReadTimeout(5000); //컨텐츠 조회시 timeout 5초
            
            conn.setDoOutput(true); //항상 갱신된 내용 가져오도록 true로 설정
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(strParams); //파라미터 write
            wr.flush();
            wr.close();
            
            //int responseCode = conn.getResponseCode();
            
            Charset charset = Charset.forName("UTF-8");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
            
            String inputLine;
            StringBuffer response = new StringBuffer();
            
            while((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            
            in.close();
            
            System.out.println(response.toString());
            
            
        } catch (MalformedURLException e) {
            //URL
            e.printStackTrace();
        } catch (IOException e) {
            //HttpsURLConnection
            e.printStackTrace();
        }
        
    }
 
    
    public static void main(String[] args) {
        
    	String url = "https://api.openweathermap.org/data/2.5/weather";
    	String params = "lat=37.443920986679245&lon=127.39401428651476&appid=a21136b8d1cb78c63a4954cd5300c47e&lang=kor&units=metric"; 
    	try {
			HttpURLConnection(url, params);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        /*
            : 예제 만들때마다 새로 형태 만들기 귀찮아서 만들어서 사용하려고 제작하였음.
        
        
            출처
                 : https://goddaehee.tistory.com/268
                : https://bobr2.tistory.com/entry/HttpConnection-Get-Post-%EC%82%AC%EC%9A%A9%EB%B2%95
        */
    }
}