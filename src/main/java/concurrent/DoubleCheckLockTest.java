package concurrent;

public class DoubleCheckLockTest {
	public static void testOne() {
		String a = "ABC";
		String b = "ABC";
		String c = new String("ABC");
		String d = new String("ABC").intern();

		System.out.println(a == b);
		System.out.println(a == c);
		System.out.println(a == d);
	}

	public static void testTwo() {
		String e = new String("ACD");
		String f = e;
		e = e.intern();					//说明将对象存储到了字符串常量池中，而非引用

		String g = "A" + "C" + "D";		//编译器做了优化，和字面量"ACD"相同
		String h = new String("A") + "C" + "D";	//在堆中重建了对象
		String l = new String("A") + new String("B") + new String("C");

		System.out.println(e == f);		//f
		System.out.println(e == g);		//t
		System.out.println(e == h);		//f
		System.out.println(e == l);		//f
	}

	public static void testThree() {
		String s = new String("1");
		s.intern();
		String s2 = "1";
		System.out.println(s == s2);

		String s3 = new String("1") + new String("1");
		s3.intern();
		String s4 = "11";
		System.out.println(s3 == s4);	//false true
	}

	//和three的比较，将intern()向下
	public static void testFour() {
		String s = new String("1");
		String s2 = "1";
		s.intern();
		System.out.println(s == s2);

		String s3 = new String("1") + new String("1");
		String s4 = "11";
		s3.intern();
		System.out.println(s3 == s4);	//false false
	}

	public static void main(String[] args) {
		DoubleCheckLockTest.testThree();
	}
}
