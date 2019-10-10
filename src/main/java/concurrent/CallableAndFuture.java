package concurrent;

import java.util.concurrent.Callable;

/**
 * Callable和Future都是处理带返回值的线程操作，但是他们还是存在不同
 *
 * @author xli
 *
 */
public class CallableAndFuture {
	public static void main(String[] args) {
		Runnable r = () -> {
			System.out.println("Runnable测试");
		};

		//在线程池中使用
		@SuppressWarnings("unused")
		Callable<String> c = () -> {
			System.out.println("Callable测试");
			return "Hello！";
		};

		new Thread(r).start();
	}
}
