package concurrent;

/**
 * 测试死锁
 *
 * 死锁发生条件：
 * 1.资源互斥
 * 2.不可剥夺
 * 3.请求与保持，当A请求另一个锁时，可以继续持有已有的锁
 * 4.循环等待
 *
 * 使用Thread Dump分析
 *
 * @author xl
 *
 */

public class DeathLockTest {
    protected Thread thread1;
    protected Thread thread2;

    public DeathLockTest() {
        thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    synchronized (thread1) {
                        synchronized (thread2) {
                            System.out.println("thread1");
                        }
                    }
                }
            }
        });

        thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    synchronized (thread2) {
                        synchronized (thread1) {
                            System.out.println("thread2");
                        }
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        DeathLockTest d = new DeathLockTest();
        d.thread1.start();
        d.thread2.start();
    }
}
