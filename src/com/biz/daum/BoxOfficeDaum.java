package com.biz.daum;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BoxOfficeDaum 
{
	String baseUrl = "http://ticket2.movie.daum.net/Movie/MovieRankList.aspx";
	int finalCnt = 0; // 수집을 멈추기 위한 변수
	
	public String[][] daumMovieRank(String[][] mvRank) throws IOException
	{
		Document doc = Jsoup.connect(baseUrl).get();
		Elements movieList = doc.select("div.desc_boxthumb > strong.tit_join > a");
		
		String url = "";
		String title ="";
		String daumHref = "";
		String movieCode = "";
		
		for(Element movie : movieList)
		{
			if(finalCnt == 10)
				break;
			
			// ########## 제목 가져오기
			title = movie.text();
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
			
			url = movie.attr("href");
			Document moviedoc = Jsoup.connect(url).get();
			
			if(moviedoc.select("span.txt_name").size() == 0)
				continue;
			
			
			// ===================== href =========================================================
			daumHref = moviedoc.select("a.area_poster").get(0).attr("href");
			movieCode = daumHref.substring(daumHref.lastIndexOf("=")+1, daumHref.lastIndexOf("#"));
			// ====================================================================================	
			
			mvRank[flag][11] = movieCode;
			finalCnt++;
		}
		
		return mvRank;
	}
}
