package concurrent.collection;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int n = s.nextInt();
		int k = s.nextInt();
		s.close();
		
		int maxk = (n % 2 == 1) ? ((n/2) * (n/2 + 1)) : ((n / 2) * (n / 2));
		if (k > maxk) {
			System.out.println("-");
			return;
		}
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) {
			if (i < (n-k)) {
				sb.append("A");
			} else {
				sb.append("B");				
			}
		}
		
		int a = (n-k);
		int b = k;
		int i = 0;
		
		while (a > 0) {
			System.out.println(sb);
			if (a * b > k) {
				a--;
				sb.replace(i, i+1, "B");
				i++;
			}
			
			if (a * b == k) {
				System.out.println(sb.toString());
				return;
			}
		}
	}
}
