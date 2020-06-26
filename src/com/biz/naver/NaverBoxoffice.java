package com.biz.naver;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

public class NaverBoxoffice 
{
	public String[][] naverRank(String[][] mvRank) throws IOException 
	{
		String url = "https://movie.naver.com/movie/running/current.nhn";
		Document doc = Jsoup.connect(url).get();
		Elements movieList = doc.select("div.lst_wrap > ul > li");
		
		String title = ""; // 제목
		//String score = ""; // 평점
		//String score2 = ""; // 참여자
		String rate = ""; // 예매율
		String type = ""; // 장르
		String time = ""; // 상영시간
		String openDt = ""; // 개봉일
		String director = ""; // 감독
		String actor = ""; // 출연진
		String naverCode =""; // 네이버 영화 코드
		
		String temp = "";
		String tempDt = "";
		String naverHref = "";
		
		int finalCnt = 0; // 수집을 멈추기 위한 변수
		
		for(Element movie : movieList)
		 {
			if(finalCnt == 10)
				break;
			
			title = movie.select("dt.tit > a").text(); // 영화 제목
			int flag = 999;
			for(int i =0; i< mvRank.length; i++)
			{
				if(mvRank[i][1].equals(title))
				{
					flag = i; // 0~9값만 Input
					break;
				}
			}
			
			if(flag == 999) //flag가 0~9사이의 값이면 크롤릴 시작
				continue;
		 
				rate = "0";
				director = "";
				actor = "";
							 
				// ========================= 예매율 ==============================
				if(movie.select("span.num").size() == 2)
					rate = movie.select("div > span.num").get(0).text();
						 
				 // ======================== 네이버 영화 코드 =========================	 
				 naverHref = movie.select("dt.tit > a").attr("href");
				 naverHref.lastIndexOf("=");
				 naverCode = naverHref.substring(naverHref.lastIndexOf("=")+1);
				 
				 // ================상영시간 개봉일 문자열 자르기 ================
				 temp = movie.select("dl.info_txt1 > dd").get(0).text();
				 int beginTimeIndex = temp.indexOf("|");
				 int endTimeIndex = temp.lastIndexOf("|");
				 
				 if(beginTimeIndex == endTimeIndex)
					 time = temp.substring(0, endTimeIndex);
				 else
					 time = temp.substring(beginTimeIndex+2, endTimeIndex);
				 
				 tempDt = temp.substring(endTimeIndex+2);
				 openDt = tempDt.replace("개봉", "");
				
				 // ==================== 감독 출연진 =============================
				 int dCode = 0;
				 int aCode = 0;
				 
				 if(!movie.select("dt.tit_t2").text().equals(""))
					 dCode = 1;
				 
				 if(!movie.select("dt.tit_t3").text().equals(""))
					 aCode = 1;
				 
				 if(dCode == 1 && aCode == 0)
					 director = movie.select("dd > span.link_txt").get(1).text();
					
				 else if(dCode == 0 && aCode == 1)
					 actor =  movie.select("dd > span.link_txt").get(1).text();
					 
				 else if(dCode == 1 && aCode == 1)
				 {
					 director = movie.select("dd > span.link_txt").get(1).text();
					 actor =  movie.select("dd > span.link_txt").get(2).text(); 
				 }
				// ============================================================= 
				 
				title = movie.select("dt > a").text(); 
				type = movie.select("span.link_txt").get(0).text();
				//score = movie.select("span.num").get(0).text();
				//score2 = movie.select("span.num2 > em").get(0).text();
				 
				mvRank[flag][2] = rate;
				mvRank[flag][3] = type;
				mvRank[flag][4] = time;
				mvRank[flag][5] = openDt;
				mvRank[flag][6] = director;
				mvRank[flag][7] = actor;
				mvRank[flag][10] = naverCode;
				finalCnt++;
		 }
			
	  return mvRank;
	}
}



