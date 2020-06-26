package com.biz.daum;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DaumMovie 
{
	public static void main(String[] args) throws Exception 
	{
		
		String userId = ""; // 작성자 이름
		String reply = "";  // 작성 내용
		String date = "";   // 날짜

		int page = 1;  //
		int score = 0; // 영화 평점
		int cnt = 0;   // 댓글 수

		Document doc = null;
		Elements replyList = null;

		while(true)
		{
			String url = "https://movie.daum.net/moviedb/grade?movieId=134684&type=netizen&page=" + page;
			doc = Jsoup.connect(url).get();
			replyList = doc.select("div.main_detail > ul > li");
			
			if(replyList.size() == 0)
				break;
			
			for(Element one : replyList)
			{
				userId = one.select("em.link_profile").text();
				score  = Integer.parseInt(one.select("em.emph_grade").text());
				reply  = one.select("p.desc_review").text();
				date   = one.select("span.info_append").text();
				cnt++;

				System.out.println("이름 : " + userId);
				System.out.println("평점 : " + score);
				System.out.println("내용 : " + reply);
				System.out.println("날짜 : " + date);
				System.out.println("========================================");
			}
			page++;
		}
		System.out.println("총 댓글 수 : " + cnt);
		
	}
}
