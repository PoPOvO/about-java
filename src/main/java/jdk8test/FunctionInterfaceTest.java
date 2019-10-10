package jdk8test;

import java.util.function.Consumer;

/**
 * 函数式接口测试
 *
 * @author xl
 *
 */
public class FunctionInterfaceTest {
	public static void main(String[] args) {
		//接收一个参数不返回结果
		Consumer<String> printStr = str -> System.out.println(str);
		printStr.accept("abc");
	}
}
