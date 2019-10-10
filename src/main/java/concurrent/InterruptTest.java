package concurrent;

/**
 * --------------测试线程中断--------------
 * <hr>
 * 1. isInterrupt()：判断线程中断状态<br>
 * 2. interrupt():用于设置某个线程的中断状态<br>
 * 3. Thread.interrupted()：重置中断状态为false<br>
 *
 * @author xl
 *
 */
public class InterruptTest {
    private static boolean run = true;

    //使用interrupt()和isInterrupt()搭配来终止线程，效果和在while条件中设立标志一样
    public static void interruptFlagTest(){
        Thread t1 = new Thread(() -> {
            while (true && !Thread.currentThread().isInterrupted()) {
                System.out.println("1");
            }
        });

        t1.start();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t1.interrupt();
    }

    //中断阻塞中的线程，可根据中断标志和异常来中断阻塞线程
    public static void interruptBlockingThreadTest() {
        Thread t1 = new Thread(() -> {
            while (run) {
                try {
                    Thread.sleep(100000);
                } catch (InterruptedException e) {
                    System.out.println("线程被中断！");
                    //抛出异常后，中断标志会被清除
                    System.err.println(Thread.currentThread().isInterrupted());
                    run = false;
                }
            }
        });

        t1.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        t1.interrupt();
    }

    public static void main(String[] args) {
        InterruptTest.interruptBlockingThreadTest();
    }
}
