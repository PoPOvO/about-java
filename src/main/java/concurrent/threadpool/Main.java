package concurrent.threadpool;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		String str1 = s.next();
		String str2 = s.next();
		s.close();
		
		String[] arr1 = str1.split("\\.");
		String[] arr2 = str2.split("\\.");
		
		int i = 0;
		int j = 0;
		
		while (i < arr1.length && j < arr2.length) {
			int a = Integer.parseInt(arr1[i]);
			int b = Integer.parseInt(arr2[j]);
			
			if (a > b) {
				System.out.println("1");
				return;
			} else if (a < b) {
				System.out.println("-1");
				return;
			}
			
			i++;
			j++;
		}
		
		if (i < arr1.length)
			System.out.println("1");
		else if (j < arr2.length)
			System.out.println("-1");
		else 
			System.out.println("0");
	}
}
