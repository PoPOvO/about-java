package concurrent;

/**
 * volatile可见性测试：
 *
 * t1线程负责自选检测共享变量stop，t2线程负责更改共享变量stop。
 *
 * 这里开启了20对线程检测volatile的可见性。运行结果发现有多个线程未能成功终止。
 * 由此可见，某个线程对共享变量的修改未能及时被其它线程发现。
 *
 * 但是，为共享变量加上volatile之后，所有线程总能成功结束。这是volatile的特性
 * 导致，它保证了线程对共享变量的修改能对其他线程可见。
 *
 * 由于volatile的hp规则，线程2对stop的修改对线程1可见。
 *
 * @author xl
 *
 */
public class VolatileVisableTest {
    private volatile boolean stop = false;

    public void test(int i) {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!stop)
                    ;
            }
        });
        t1.setName("A" + i);

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                stop = true;
            }
        });
        t2.setName("B" + i);

        t1.start();
        try {
            Thread.sleep(100);		//休眠10ms，尽可能让t1线程先启动
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.start();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            new VolatileVisableTest().test(i);
        }
    }
}
