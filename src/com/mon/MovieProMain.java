package com.mon;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Scanner;

import com.biz.daum.BoxOfficeDaum;
import com.biz.daum.ReplyCrawlerDaum;
import com.biz.naver.NaverBoxoffice;
import com.biz.naver.ReplyCrawlerNaver;
import com.biz.persistance.ReplyDAO;

public class MovieProMain 
{
	public static void main(String[] args) throws Exception
	{
		BoxOfficeParser bParser = new BoxOfficeParser();  // 생성자 : 객체생성과 동시에 +a 작업을 하고 싶음
		NaverBoxoffice bon = new NaverBoxoffice();
		BoxOfficeDaum  don = new BoxOfficeDaum();
		ReplyCrawlerNaver nCrawler = new ReplyCrawlerNaver();
		ReplyCrawlerDaum  dCrawler = new ReplyCrawlerDaum();
		ReplyDAO rDAO = new ReplyDAO();
		
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
		int userVal = userInterface(mvRank);
		
		// Naver, Daum Reply Crawling
		rDAO.deleteReply(mvRank[userVal-1][1]); // 수집하는 댓글의 영화가 MongoDB에 저장되어 있는 영화라면 해당 영화 댓글 우선 삭제 후 새로운 댓글 저장
		HashMap<String, Integer> nMap = nCrawler.naverCrawler(mvRank[userVal-1][1], mvRank[userVal-1][10]);
		HashMap<String, Integer> dMap = dCrawler.daumCrawler(mvRank[userVal-1][1], mvRank[userVal-1][11]);  
		
		// 4. 사용자에게 결과 출력
		printArr(mvRank, userVal, nMap, dMap);
	}
	
	
	// View : 프로그램 시작 인터페이스 + 사용자 값 입력
	public static int userInterface(String[][] mvRank)
	{
		// 2. View단
		// 2-1. 유저에게 BoxOffice 예매율 1~10위 까지의 정보 제공
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd [HH:mm:ss]");
		String today = sdf.format(cal.getTime());
		System.out.println("======================================");
		System.out.println("SimpleMovie Ver1.2");
		System.out.println("======================================");
		System.out.println("Developer : H2LJH");
		System.out.println("======================================");
		System.out.println("TODAY : " + today);
		System.out.println((cal.get(Calendar.MONTH)+1) + "월" + cal.get(Calendar.DATE) + "일 한국 영화관 TOP 10");
		System.out.println("======================================");
		for(int i = 0; i<mvRank.length; i++) 
		{
			String noneCode = "";
			if(mvRank[i][10] == null)
				noneCode = " [정보 없음]";
			
			System.out.println("[" + mvRank[i][0] + "위] " + mvRank[i][1] + noneCode);
		}
		System.out.println("======================================");
		System.out.println("보고 싶은 영화 1-10 순위중 한개를 입력하세요.");
		int userVal = 0; 
		// 유효성 체크
		while(true)
		{
			System.out.print("번호 : ");
			Scanner sc = new Scanner(System.in);
			userVal = sc .nextInt();
			sc.close();
			
			if((userVal > 0 && userVal < 10) && mvRank[userVal-1][10] != null)
				break;
			
			else
				System.out.println("영화 정보 없음");
		}
		
		System.out.println("======================================");
		return userVal;
	}
	
	// Print mvRank 
	public static void printArr(String[][] mvRank, int userVal, HashMap<String, Integer> nMap, HashMap<String, Integer> dMap) 
	{
		double avgNaver = (double)(nMap.get("total")) / (double)(nMap.get("cnt"));
		double avgDaum = (double)(dMap.get("total")) / (double)(dMap.get("cnt"));
		DecimalFormat threeDot = new DecimalFormat("###,###");
		BigInteger money = new BigInteger(mvRank[userVal-1][9]); //문자열(String)만 가능 유효범위 무한대 수준
		
		System.out.println("영화 제목 : "     + mvRank[userVal-1][1] + "\n");
		System.out.println("예매율 : "        + mvRank[userVal-1][2] + "%");
		System.out.println("장르 : "          + mvRank[userVal-1][3]);
		System.out.println("상영 시간 : "     + mvRank[userVal-1][4]);
		System.out.println("개봉 일자 : "     + mvRank[userVal-1][5]);
		System.out.println("감독 : "          + mvRank[userVal-1][6]);
		System.out.println("출연진 : "        + mvRank[userVal-1][7]);
		System.out.println("누적 관객수 : "   + threeDot.format(Integer.parseInt(mvRank[userVal-1][8])) + "명");
		System.out.println("누적 매출액 : "   + threeDot.format(money) + "원");
		System.out.println("네이버 댓글수 : " + nMap.get("cnt") + "건");
		System.out.println("다음 댓글수 : "   + dMap.get("cnt") + "건");
		System.out.printf("네이버 평균 평점 : %.1f점\n", avgNaver);
		System.out.printf("다음 평균 평점 : %.1f점\n",   avgDaum);
		System.out.println("=============================================");
			
	}
	
}
