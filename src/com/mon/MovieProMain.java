package com.mon;

import java.util.HashMap;

import com.biz.UI.ClientUI;
import com.biz.daum.BoxOfficeDaum;
import com.biz.daum.ReplyCrawlerDaum;
import com.biz.naver.NaverBoxoffice;
import com.biz.naver.ReplyCrawlerNaver;
import com.biz.persistance.ReplyDAO;

public class MovieProMain 
{
	public static void main( String[] args) throws Exception
	{
		 BoxOfficeParser bParser = new BoxOfficeParser();  // 생성자 : 객체생성과 동시에 +a 작업을 하고 싶음
		 NaverBoxoffice bon = new NaverBoxoffice();
		 BoxOfficeDaum  don = new BoxOfficeDaum();
		 ReplyCrawlerNaver nCrawler = new ReplyCrawlerNaver();
		 ReplyCrawlerDaum  dCrawler = new ReplyCrawlerDaum();
		 ReplyDAO rDAO = new ReplyDAO();
		 ClientUI ui = new ClientUI();
  
		// 순위, 영화제목, 예매율, 장르, 상영시간, 개봉일자, 감독, 출연진, 누적관객수, 누적매출액, 네이버코드, 다음코드
		String[][] mvRank = new String[10][12];
		
		// 1. 박스오피스 정보 + 네이버 영화정보 + 다음 영화정보 (1-10위)
		
		// 1-1 BoxOffice Parsing 
		mvRank = bParser.getParser();
		
		// 1-2 Naver BoxOffice Crawling 
		mvRank = bon.naverRank(mvRank);
		
		// 1-3 Daum BoxOffice Crawling
		mvRank = don.daumMovieRank(mvRank);

		// View
		 int userVal = ui.userInterface(mvRank);
		
		// Naver, Daum Reply Crawling
		rDAO.deleteReply(mvRank[userVal-1][1]); // 수집하는 댓글의 영화가 MongoDB에 저장되어 있는 영화라면 해당 영화 댓글 우선 삭제 후 새로운 댓글 저장
		HashMap<String, Integer> nMap = nCrawler.naverCrawler(mvRank[userVal-1][1], mvRank[userVal-1][10]);
		HashMap<String, Integer> dMap = dCrawler.daumCrawler(mvRank[userVal-1][1], mvRank[userVal-1][11]);
		// 4. 사용자에게 결과 출력
		ui.printArr(mvRank, userVal, nMap, dMap);
	}
	
	
}
