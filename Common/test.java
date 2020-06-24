package Common;

import java.util.Scanner;

public class test 
{
	public static void main(String[] args) 
	{
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		String strN = sc.next();
		sc.close();
		
		int sum = 0;
		for(int i=0; i<n; ++i)
			sum += Integer.parseInt(strN.substring(i,i+1));
		
		System.out.println(sum);
	}
}
