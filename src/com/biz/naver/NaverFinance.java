package com.biz.naver;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class NaverFinance 
{
	static String base = "https://finance.naver.com/item/frgn.nhn?code=005930&page=1";
	
	public static void main(String[] args) throws IOException 
	{ 
			Document doc = Jsoup.connect(base).get();
			Elements line = doc.select("table.type2 > tbody > tr");
			
			int tdCount = 9; // tbody > tr 이 겹치는부분이 있으므로 원하는 tr 태그 부분의 td 태그의 개수
			
			System.out.println("=============================================== 삼성전자 ==============================================================\n");
			System.out.print("날짜\t\t종가\t전일비\t등락률\t 거래량\t\t기관순매매량\t외국인순매매량\t 보유주수\t보유율\n");
			for(Element element : line)
			{
				Elements tds = element.select("td");
				
				if(tds.size() == tdCount)
				{
					System.out.println("=======================================================================================================================");
					for(int i=0; i<tdCount; ++i)
					{
						String regdate = tds.get(i).text();
						System.out.printf("%s\t", regdate);					
					}
					System.out.print("\n");
				}
			}
	}
	
}

