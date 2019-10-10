package concurrent;

/**
 * synchronized特性：
 * 1. 原子性
 * 2. 可见性
 * 3. 有序性
 *
 * @author xl
 *
 */
public class AtomicTest {
	private int num;

	public AtomicTest(int num) {
		this.num = num;
	}

	public void add() {
		synchronized (this) {
			num++;
		}
	}

	public void sub() {
		synchronized (this) {
			num--;
		}
	}

	public void show() {
		System.out.println("num:" + num);
	}

	public static void main(String[] args) {
		AtomicTest atomicTest = new AtomicTest(10);
		int flag = 1000;

		Thread one = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < flag; i++) {
					atomicTest.add();
				}
			}
		});

		Thread two = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < flag; i++) {
					atomicTest.sub();
				}
			}
		});

		one.start();
		two.start();

		try {
			one.join();
			two.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		atomicTest.show();
	}
}
