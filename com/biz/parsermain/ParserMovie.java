package com.biz.parsermain;

import java.io.BufferedInputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ParserMovie 
{
	// 1. 파싱하고 싶은 URL 생성
	
	String key = "ee84a95a06a4aae67fc4490ac2bcdb3a";
	String today = "";
	String[][] arrBoxOffice = new String[10][4];
	
	public String makeURL()
	{
		// 날짜 구하기 =========================================
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1); // 오늘날짜로부터 -1
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		today = sdf.format(cal.getTime());
		// =====================================================
		
		String url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json" +  
					 "?key=" + key + 
					 "&targetDt=" + today;
		
		return url;
	}
	
	private String readURL(String preurl) throws Exception
	{
		BufferedInputStream reader = null;
		try 
		{
			URL url = new URL(preurl);
			reader = new BufferedInputStream(url.openStream());
			StringBuffer buffer = new StringBuffer();
			byte[] b = new byte[4096]; // 보편적으로 4096 많이씀
			int i;
			
			while((i = reader.read(b)) != -1) // -1 값 없음
				buffer.append(new String(b, 0, i));
			
			return buffer.toString();
		} 
		
		finally 
		{
			if(reader != null)
				reader.close();
		} 
		
	}
	
	
	public String[][] getBoxOffice(String url) throws Exception
	{
		
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject)parser.parse(readURL(url));
		JSONObject json = (JSONObject)obj.get("boxOfficeResult");
		JSONArray array = (JSONArray)json.get("dailyBoxOfficeList");

		for(int i=0; i<array.size(); ++i)
		{
			JSONObject entity = (JSONObject)array.get(i);
			String rank 	= (String)entity.get("rank");    // 순위
			String movieNm  = (String)entity.get("movieNm"); // 영화 제목
			String audiAcc  = (String)entity.get("audiAcc"); // 누적 관객수
			String salesAcc = (String)entity.get("salesAcc"); // 누적 매출액
			
			arrBoxOffice[i][0] = rank;
			arrBoxOffice[i][1] = movieNm;
			arrBoxOffice[i][2] = audiAcc;
			arrBoxOffice[i][3] = salesAcc;
		}
		
		return arrBoxOffice;
	}
	
}
