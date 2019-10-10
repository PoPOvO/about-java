package concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch相对于join提供了更加丰富的功能
 *
 * @author xli
 *
 */
public class CountDownLatchTest {
	public static void main(String[] args) throws InterruptedException {
		//定义一个为3的计数器，每次调用countDown()，计数器减一
		//该计数器可以应用到同一个线程的不同步骤中，也可以应用到多个线程中
		CountDownLatch countDownLatch = new CountDownLatch(4);

		Thread t1 = new Thread(() -> {
			System.out.println("第一步");
			countDownLatch.countDown();
			System.out.println("第二步");
			countDownLatch.countDown();
			System.out.println("第三步");
			countDownLatch.countDown();
		});

		Thread t2 = new Thread(() -> {
			System.out.println("t2阻塞");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("t2阻塞结束");
			countDownLatch.countDown();
		});
		t1.start();
		t2.start();

		System.out.println("Main 等待");
		//导致当前线程等待，直到countDownLatch的计数器变为0
		countDownLatch.await();
		System.out.println("Main 进行");
	}
}
