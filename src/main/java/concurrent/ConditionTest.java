package concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition是一个接口，是和Lock搭配使用的。就像wait、notify和synchronized搭配一样。
 * Condition的实现是ConditionObject，它是AQS的内部类。因此Condition的获取需要依赖Lock。
 *
 * @author xli
 *
 */
public class ConditionTest {
	public static void main(String[] args) {
		Lock lock = new ReentrantLock();
		Condition cond = lock.newCondition();

		Thread t1 = new Thread(() -> {
			lock.lock();
			try {
				System.out.println(Thread.currentThread().getName() + "准备阻塞");
				cond.await();
				System.out.println(Thread.currentThread().getName() + "阻塞结束");
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		});
		t1.start();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Thread t2 = new Thread(() -> {
			lock.lock();
			try {
				System.out.println(Thread.currentThread().getName() + "准备唤醒阻塞的线程");
				cond.signal();
			} finally {
				lock.unlock();
			}
		});
		t2.start();
	}
}
