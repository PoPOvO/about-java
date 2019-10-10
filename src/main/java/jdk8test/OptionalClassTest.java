package jdk8test;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * 测试Optional类
 * <br>
 * Optional类用于解决空指针问题
 *
 * @author xl
 *
 */
public class OptionalClassTest {
	public static int sum(Integer a, Integer b) {
		//不接受参数为null，若为null，抛出异常
		Optional<Integer> opa = Optional.of(a);
		//接受参数为null
		Optional<Integer> opb = Optional.ofNullable(b);

		//orElse(other)，若容器里的值为null则返回值，否则使用参数的默认值
		return opa.orElse(0) + opb.orElse(0);

		//否则写成
//		if (a == null)	return b;
//		if (b == null)	return a;
//		if (a == null && b == null) return 0;
//		return a + b;
	}

	public static int sub(Integer a, Integer b) {
		Optional<Integer> opa = Optional.ofNullable(a);
		Optional<Integer> opb = Optional.ofNullable(b);

		Supplier<Integer> spl = ()->{
			System.err.println("参数为null，使用默认值0.");
			return 0;
		};
		//若容器里的值为null，则使用函数式接口并返回默认值
		return opa.orElseGet(spl) + opb.orElseGet(spl);
	}

	public static void main(String[] args) {
		System.out.println(OptionalClassTest.sum(777, null));
		System.out.println(OptionalClassTest.sub(null, null));
	}
}
