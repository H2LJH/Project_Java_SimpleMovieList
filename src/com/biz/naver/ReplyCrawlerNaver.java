package com.biz.naver;

import java.io.IOException;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.biz.domain.ReplyDTO;
import com.biz.persistance.ReplyDAO;

public class ReplyCrawlerNaver 
{
	int page = 1;
	int cnt = 0;
	int total = 0;
	String currentPage = "";
	ReplyDAO rDAO = new ReplyDAO();
	
	public HashMap<String, Integer> naverCrawler(String movieName, String naverCode) throws IOException
	{
		int score = 0;
		String content = "";
		String userID = "";
		String date = "";
		String url = "";
		String checkPage = "";
		Document doc = null;
		Elements movieList = null;

		rDAO.deleteReply(movieName); // 수집하는 댓글의 영화가 MongoDB에 저장되어 있는 영화라면 해당 영화 댓글 우선 삭제 후 새로운 댓글 저장
		
		while(true)
		{
			url = "https://movie.naver.com/movie/bi/mi/pointWriteFormList.nhn?code=" + naverCode + "&type=after&isActualPointWriteExecute=false&isMileageSubscriptionAlready=false&isMileageSubscriptionReject=false&page=" + page;
			doc = Jsoup.connect(url).get();
			movieList = doc.select("div.score_result ul li");
			currentPage = doc.select("input#page").attr("value");
			
			if(currentPage.equals(checkPage))
				break;
			else
				checkPage = currentPage;
			
			for(Element movie : movieList)
			{
				score = Integer.parseInt(movie.select("div.star_score em").text());
				content = movie.select("div.score_reple > p > span").text();
				userID = movie.select("div.score_reple dl > dt > em").get(0).text();
				date = movie.select("div.score_reple dl > dt > em").get(1).text();
				
				// MongoDB insert
				ReplyDTO rDTO = new ReplyDTO(movieName, content, userID, date, score);
				//System.out.println(rDTO.toString());
				rDAO.addReply(rDTO);
				total += score;
				cnt++;
				
				System.out.println("평점 : "   + score);
				System.out.println("작성자 : " + userID);
				System.out.println("내용 : "   + content);
				System.out.println("날짜 : "   + date);
				System.out.println("==============================================");
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
