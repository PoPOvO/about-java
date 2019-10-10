package petty;

/**
 * 解决this逃逸的最好方法就是不要在构造方法中将this传递出去
 *
 * @author xl
 *
 */
public class ThisEscapeOne {
	final String a;
	static ThisEscapeOne t;

	//这里将语句1写在最后也无效，因为可能会发生重排序，仍会发生逃逸
	public ThisEscapeOne() {
		t = this;		//1

		//这里延时200ms，模拟构造方法其他字段的初始化
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		a = "ABC";		//2
	}

	public static void main(String[] args) {
		//t1进行构造对象
		Thread t1 = new Thread(() -> {
			new ThisEscapeOne();
		});

		//t2观测常量的值
		Thread t2 = new Thread(() -> {
			System.out.println(ThisEscapeOne.t.a);
		});

		t1.start();
		//尽量保证t1先启动
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
		}
		t2.start();
	}
}

