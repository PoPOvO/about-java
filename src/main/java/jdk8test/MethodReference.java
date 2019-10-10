package jdk8test;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试方法引用
 *
 * @author xl
 *
 */
public class MethodReference {
	public static void copyStrAndPrint(String str) {
		System.out.println(str + str);
	}

	public static void main(String[] args) {
		List<String> ls = new ArrayList<>(5);
		ls.add("A");
		ls.add("B");
		ls.add("C");
		ls.add("D");

		//这里的参数是函数式接口Consumer<? super T> action
		ls.forEach(MethodReference :: copyStrAndPrint);
		//如不进行方法引用，可写成
		ls.forEach(str -> System.out.println(str + str));
	}
}
