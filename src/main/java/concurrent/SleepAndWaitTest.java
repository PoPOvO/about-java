package concurrent;

/**
 * 测试sleep()和wait()
 *
 * sleep()在临界区中Waiting不释放monitor
 * wait()在临界区内Waiting会释放monitor
 *
 * 可以使用Thread dump观察
 */

import java.util.Calendar;

public class SleepAndWaitTest{
    protected Thread thread1;
    protected Thread thread2;

    public SleepAndWaitTest() {
        thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (SleepAndWaitTest.class) {
                    try {
                        System.out.println("thread1 get Lock:[" + Calendar.getInstance().getTime().toString() + "]");
                        //执行这句话，thread1依旧保持monitor并阻塞，thread2即便执行也得thread1释放monitor
                        Thread.sleep(3000);
                        //执行这句话，thread1释放monitor并进入阻塞队列，thread2执行时可以直接得到monitor
                        //SleepAndWaitTest.class.wait(3000);
                        System.out.println("thread1:some text!!![" + Calendar.getInstance().getTime().toString() + "]");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (SleepAndWaitTest.class) {
                    System.out.println("thread2 get Lock:[" + Calendar.getInstance().getTime().toString() + "]");
                }
            }
        });
    }

    public static void main(String[] args) {
        SleepAndWaitTest tt = new SleepAndWaitTest();
        System.out.println("mainT:[" + Calendar.getInstance().getTime().toString() + "]");
        tt.thread1.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        tt.thread2.start();
    }
}
