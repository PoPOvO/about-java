package concurrent.aqs;

/**
 * 测试共享锁，测试时线程名成对的打印，表明同一时刻可以存在两个线程同时获取锁，
 * 因此是共享锁
 *
 * @author xli
 *
 */
public class UserSharedLockTest {
	public static void main(String[] args) {
		UserSharedLock lock = new UserSharedLock();

		Thread[] ts = new Thread[6];
		for (int i = 0; i < ts.length; i++) {
			ts[i] = new Thread(new Runnable() {
				@Override
				public void run() {
					lock.lock();
					try {
						Thread.sleep(2000);
						System.out.println(Thread.currentThread().getName() + "获得锁");
					} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {
						lock.unlock();
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			});
		}

		for (Thread t: ts)
			t.start();
	}
}
