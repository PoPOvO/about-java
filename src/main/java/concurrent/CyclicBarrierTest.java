package concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 循环屏障：阻塞到达屏障的线程，直到最后一个线程到达后，屏障破碎。
 *
 * @author xli
 *
 */
public class CyclicBarrierTest {
	static void test1() {
		CyclicBarrier c = new CyclicBarrier(2);

		Thread t1 = new Thread(() -> {
			try {
				System.out.println("t1阻塞");
				c.await();
				System.out.println("t1阻塞结束");
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
		});
		t1.start();

		Thread t2 = new Thread(() -> {
			try {
				System.out.println("t2阻塞");
				c.await();
				System.out.println("t2阻塞结束");
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
		});
		t2.start();
	}

	static void test2() {
		//第二个参数表示一个动作，当最后一个线程到达屏障时执行
		CyclicBarrier c = new CyclicBarrier(2, () -> {
			System.out.println("最后一个线程到达，合并任务！");
		});

		Thread t1 = new Thread(() -> {
			try {
				System.out.println("t1阻塞");
				c.await();
				System.out.println("t1阻塞结束");
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
		});
		t1.start();

		Thread t2 = new Thread(() -> {
			try {
				System.out.println("t2阻塞");
				c.await();
				System.out.println("t2阻塞结束");
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
		});
		t2.start();
	}

	public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
		test2();
	}
}
