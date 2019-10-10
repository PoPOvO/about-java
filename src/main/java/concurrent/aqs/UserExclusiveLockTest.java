package concurrent.aqs;

/**
 * 测试i++，i--，使用自定义的独占锁来实现
 */
public class UserExclusiveLockTest {
	int i;

	public void inc() {
		i++;
	}

	public void dec() {
		i--;
	}

	/**
	 * 测试结果正常，i=0
	 */
	public static void main(String[] args) {
		UserExclusiveLockTest lockTest = new UserExclusiveLockTest();
		UserExclusiveLock lock = new UserExclusiveLock();

		Thread t1 = new Thread(() -> {
			lock.lock();
			try {
				for (int i = 0; i < 10000; i++) {
					lockTest.inc();
				}
			} finally {
				lock.unlock();
			}
		});

		Thread t2 = new Thread(() -> {
			lock.lock();
			try {
				for (int i = 0; i < 10000; i++) {
					lockTest.dec();
				}
			} finally {
				lock.unlock();
			}
		});

		t1.start();
		t2.start();

		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println(lockTest.i);
	}
}
