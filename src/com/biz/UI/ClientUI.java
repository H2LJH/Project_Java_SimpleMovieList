package com.biz.UI;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * clientUI
 */
public class ClientUI 
{

    public int userInterface(String[][] mvRank)  // View : 프로그램 시작 인터페이스 + 사용자 값 입력
	{

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd [HH:mm:ss]");
		String today = sdf.format(cal.getTime());
		String noneCode = "";
		int userVal = 0; 		

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
		 	noneCode = "";
			if(mvRank[i][10] == null)
				noneCode = " [정보 없음]";
			
			System.out.println("[" + mvRank[i][0] + "위] " + mvRank[i][1] + noneCode);
		}

		System.out.println("======================================");
		System.out.println("보고 싶은 영화 1-10 순위중 한개를 입력하세요.");
 

		while(true)// 유효성 체크
		{
			System.out.print("번호 : ");
			Scanner sc = new Scanner(System.in);
			userVal = sc .nextInt();
			sc.close();
			
			if((userVal > 0 && userVal < 10) && mvRank[userVal-1][10] != null)
				break;
			
			else
				System.out.println("영화 정보가 없거나 입력이 잘못되었습니다.");
		}
		
		System.out.println("======================================");
		return userVal;
	}

    public void printArr(String[][] mvRank, int userVal, HashMap<String, Integer> nMap, HashMap<String, Integer> dMap) 
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