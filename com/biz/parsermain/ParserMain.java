package com.biz.parsermain;

public class ParserMain 
{
	public final static String url = "";
	public final static String key = "";
	public static String today;
	
	public static void main(String[] args) throws Exception
	{
		ParserMovie pm = new ParserMovie();
		System.out.println(pm.makeURL());
		
		String url = pm.makeURL();
		String[][] arrRank = pm.getBoxOffice(url);
		
		
		for(int i=0; i<arrRank.length; ++i)
		{
			System.out.println("==============================================================");
			System.out.println(arrRank[i][0] + "위");
			System.out.println(arrRank[i][1]);
			System.out.println(arrRank[i][2] + "명");
			System.out.println(arrRank[i][3] + "원");
		}
		
	}
}
