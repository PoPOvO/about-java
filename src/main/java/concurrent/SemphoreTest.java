package concurrent;

import java.util.concurrent.Semaphore;

/**
 * Java中实现的P V操作，应用于稀缺资源的控制。
 * 例如：10个线程共享3个数据库连接
 *
 * 运行结果：
 * 资源总是3个3个的被使用
 *
 * @author xli
 *
 */
public class SemphoreTest {
	public static void main(String[] args) {
		//最大3个资源可以访问
		Semaphore s = new Semaphore(3);
		Runnable r = () -> {
			try {
				s.acquire();
				System.out.println(Thread.currentThread().getName() + "获取到资源!");
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				s.release();
			}
		};

		Thread[] ts = new Thread[15];
		for (int i = 0; i < ts.length; i++) {
			ts[i] = new Thread(r);
		}
		for (Thread t: ts)
			t.start();
	}
}
