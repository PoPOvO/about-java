package concurrent.aqs;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int m = s.nextInt();
		int n = s.nextInt();
		int[] arr = new int[m];
		int[] bool = new int[4000];
		int res = Integer.MAX_VALUE;
		
		for (int i = 0; i < m; i++) {
			arr[i] = s.nextInt();
			bool[arr[i]]++;
		}
		Arrays.sort(arr);
		s.close();
		
		int curHigh = arr[arr.length-1];
		for (int i = bool.length-1; i >= 0; i--) {
			if (bool[curHigh] <= bool[i]) {
				curHigh = i;
				
				int curN = bool[curHigh];
				int cnt = 0;
				for (int j = arr.length - curN-1; j >= 0; j--) {
					if (curN == n) {
						if (cnt < res) {
							res = cnt;
						}
						break;
					}
					
					if (curHigh > arr[j]) {
						cnt += (curHigh - arr[j]);
						curN++;
					}
				}
			}
		}
		
		System.out.println(res);
	}
}
