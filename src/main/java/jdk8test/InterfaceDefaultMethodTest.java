package jdk8test;

/**
 * 测试接口默认方法
 *
 * @author xl
 *
 */
public class InterfaceDefaultMethodTest implements ObjectCompara {
	@Override
	public boolean compara(Object a, Object b) {
		//使用默认方法
		return this.defaultCompara(a, b);
	}

	public static void main(String[] args) {
		String a = "a";
		String b = "a";

		System.out.println(new InterfaceDefaultMethodTest().compara(a, b));
		ObjectCompara.print();
	}
}

interface ObjectCompara {
	boolean compara(Object a, Object b);

	/**
	 * 默认比较方法
	 *
	 * @param a
	 * @param b
	 * @return
	 */
	default boolean defaultCompara(Object a, Object b) {
		return a.hashCode() == b.hashCode();
	}

	static void print() {
		System.out.println("这是一个比较对象的接口！");
	}
}
