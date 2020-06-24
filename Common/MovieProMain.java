package Common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

import com.biz.daum.BoxOfficeDaum;
import com.biz.naver.NaverBoxoffice;

public class MovieProMain 
{
	public static void main(String[] args) throws Exception
	{
		BoxOfficeParser bParser = new BoxOfficeParser();  // 생성자 : 객체생성과 동시에 +a 작업을 하고 싶음
		NaverBoxoffice bon = new NaverBoxoffice();
		BoxOfficeDaum  don = new BoxOfficeDaum();
		
		// 순위, 영화제목, 예매율, 장르, 상영시간, 개봉일자, 감독, 출연진, 누적관객수, 누적매출액, 네이버코드, 다음코드
		String[][] mvRank = new String[10][12];
		
		// 1. 박스오피스 정보 + 네이버 영화정보 + 다음 영화정보 (1~10위)
		
		// 1-1 BoxOffice Parsing 
		mvRank = bParser.getParser();
		
		// 1-2 Naver BoxOffice Crawling 
		mvRank = bon.naverRank(mvRank);
		
		// 1-3 Daum BoxOffice Crawling
		mvRank = don.daumMovieRank(mvRank);

		// View
		userInterface(mvRank);
		
		
	
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
			if((userVal > 0 && userVal < 10) && mvRank[userVal-1][10] != null)
				break;
			else
				System.out.println("영화 정보 없음");
		}
		
		
		System.out.println("======================================");
				
		return userVal;
	}
	
	// mvRank 출력하는 코드
	public static void printArr(String[][] mvRank) 
	{
		for(int i = 0; i<mvRank.length; i++) 
		{
			System.out.print(mvRank[i][0] + "\t");
			System.out.print(mvRank[i][1]+ "\t");
			System.out.print(mvRank[i][2]+ "\t");
			System.out.print(mvRank[i][3]+ "\t");
			System.out.print(mvRank[i][4]+ "\t");
			System.out.print(mvRank[i][5]+ "\t");
			System.out.print(mvRank[i][6]+ "\t");
			System.out.print(mvRank[i][7]+ "\t");
			System.out.print(mvRank[i][8]+ "\t");
			System.out.print(mvRank[i][9]+ "\t");
			System.out.print(mvRank[i][10]+ "\t");
			System.out.println(mvRank[i][11]);
			System.out.println("=============================");
		}
			
	}
	
}
