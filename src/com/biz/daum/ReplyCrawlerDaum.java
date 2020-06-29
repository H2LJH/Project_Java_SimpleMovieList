package com.biz.daum;

import java.io.IOException;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.biz.domain.ReplyDTO;
import com.biz.persistance.ReplyDAO;

public class ReplyCrawlerDaum 
{
	ReplyDAO rDAO = new ReplyDAO();
	
	int page = 1;
	int cnt  = 0;
	int total = 0;
	String prePage = "";
	
	public HashMap<String, Integer> daumCrawler(String movieName, String daumCode) throws IOException
	{		
		String userID = ""; // 작성자 이름
		String content = "";  // 작성 내용
		String regdate = "";   // 날짜
		int score = 0; // 영화 평점

		Document doc = null;
		Elements replyList = null;
		
		while(true)
		{
			String url = "https://movie.daum.net/moviedb/grade?movieId=" + daumCode + "&type=netizen&page=" + page;
			doc = Jsoup.connect(url).get();
			replyList = doc.select("div.main_detail > ul > li");
			
			if(replyList.size() == 0)
				break;
			
			for(Element one : replyList)
			{
				userID  = one.select("em.link_profile").text();
				score   = Integer.parseInt(one.select("em.emph_grade").text());
				content   = one.select("p.desc_review").text();
				regdate = one.select("span.info_append").text();
				
				ReplyDTO rDTO = new ReplyDTO(movieName, content, userID, regdate, score);
				rDAO.addReply(rDTO);
				
				total += score;
				cnt++;

				System.out.println("[Daum] 이름 : " + userID);
				System.out.println("[Daum] 평점 : " + score);
				System.out.println("[Daum] 내용 : " + content);
				System.out.println("[Daum] 날짜 : " + regdate);
				System.out.println("======================================");
			}
			page++;
		}
		//System.out.println("총 댓글 수 : " + cnt);
		
		
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("cnt", cnt);
		map.put("total", total);
		return map;
	}
	
}
