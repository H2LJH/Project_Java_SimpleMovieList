package com.biz.daum;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DaumBoxoffice 
{
	public static void main(String[] args) throws IOException 
	{
		String baseUrl = "http://ticket2.movie.daum.net/Movie/MovieRankList.aspx";
		Document doc = Jsoup.connect(baseUrl).get();
		Elements movieList = doc.select("ul.list_boxthumb > li > a");
		
		String url = "";
		String titleTemp = "";
		String title ="";
		String daumHref = "";
		String moviecode = "";
		for(Element movie : movieList)
		{
			url = movie.attr("href");
			Document moviedoc = Jsoup.connect(url).get();
			
			// ======================== 타이틀 ========================
			if(moviedoc.select("span.txt_name").size() == 0)
				continue;
			titleTemp = moviedoc.select("span.txt_name").get(0).text();
			title = titleTemp.substring(0, titleTemp.lastIndexOf("("));
			
			// ===================== href =============================	
			daumHref = moviedoc.select("a.area_poster").get(0).attr("href");
			moviecode = daumHref.substring(daumHref.lastIndexOf("=")+1, daumHref.lastIndexOf("#"));
				
			// ======================= 출력 ==========================
			System.out.println(title);
			System.out.println(daumHref);
			System.out.println(moviecode);
		}
		
	}
}
