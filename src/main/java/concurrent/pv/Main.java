package concurrent.pv;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
	//aaaaabbbbcccdde
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		String str = s.next();

		// 字母池
		int[] bool = new int[26];

		for (int i = 0; i < str.length(); i++) {
			bool[str.codePointAt(i) - "a".codePointAt(0)]++;
		}

		Arrays.sort(bool);

		for (int i = 0; i < bool.length; i++) {
			if (bool[i] != 0) {
				bool = Arrays.copyOfRange(bool, i, bool.length);
			}
		}

		if (bool.length > 1) {
			bool[bool.length-1] += bool[bool.length-2];
			bool[bool.length-2] = 0;
		}

		int value = 0;
		for (int i: bool) {
			value += (i*i);
		}

		System.out.flush();
		System.out.println(value);
		s.close();
	}
}
