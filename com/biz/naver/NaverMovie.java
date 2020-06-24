package com.biz.naver;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class NaverMovie 
{
	public static void main(String[] args) throws IOException
	{
		int page = 1;
		int cnt = 0;
		int score = 0;
		String reply = "";
		String userID = "";
		String day = "";
		String url = "";
		
		String index = "";
		String indextemp = "";
		Document doc = null;
		Elements movieList = null;
		
		while(true)
		{
			url = "https://movie.naver.com/movie/bi/mi/pointWriteFormList.nhn?code=189633&type=after&isActualPointWriteExecute=false&isMileageSubscriptionAlready=false&isMileageSubscriptionReject=false&page=" + page;
			doc = Jsoup.connect(url).get();
			movieList = doc.select("div.score_result ul li");
			indextemp = doc.select("input#page").attr("value");
			
			if(indextemp.equals(index))
				break;
			else
				index = indextemp;
			
			for(Element movie : movieList)
			{
				score = Integer.parseInt(movie.select("div.star_score em").text());
				reply = movie.select("div.score_reple > p > span").text();
				userID = movie.select("div.score_reple dl > dt > em").get(0).text();
				day = movie.select("div.score_reple dl > dt > em").get(1).text();
				cnt++;
				System.out.println("평점 : "   + score);
				System.out.println("작성자 : " + userID);
				System.out.println("내용 : "   + reply);
				System.out.println("날짜 : "   + day);
				System.out.println("==============================================");
			}					
			page++;
		}
		System.out.println("총 댓글 수 :" + cnt);
	}
}
