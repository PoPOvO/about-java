package concurrent;

import java.util.Arrays;

/**
 * -------------线程组测试----------------
 * <hr>
 * 1.线程组内的线程可以随时过去组内其他线程的状态，并且可以关闭所有线程。
 * 线程组是为了多个线程配合工作而设计的。<br>
 * 2.ThreadGroup之间是树的关系，线程组可以包含其他线程组。对于一个线程组，
 * 其可以访问子线程组的关系。但是不允许子线程组访问父线程组的信息。<br>
 *
 * @author xl
 *
 */
public class ThreadGroupTest {
    /**
     * 1.main线程存在于main线程组
     * 2.在线程A启动的所有线程，若不指定，会和A处于同一个线程组，即线程组具有继承关系
     */
    public static void testMainGroup() {
        Thread t = new Thread(() -> {
            ThreadGroup group = Thread.currentThread().getThreadGroup();
            System.out.println(group.activeCount());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t.start();

        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getThreadGroup());
    }

    public static void testThreadGroupMethod() {
        //若不指定父，则默认是main
        ThreadGroup group1 = new ThreadGroup("G1");
        //指定其父为group1
        ThreadGroup group2 = new ThreadGroup(group1, "G1.1");
        System.out.println(group1.getParent());
        System.out.println(group2.getParent());

        Runnable run = () -> {
            try {
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + "延时结束……。");
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + "已经被中断……");
            }
        };
        Thread t1 = new Thread(group1, run);
        Thread t2 = new Thread(group1, run);
        Thread t3 = new Thread(group2, run);
        Thread t4 = new Thread(group2, run);

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        System.out.println();
        ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
        Thread[] threads = new Thread[currentGroup.activeCount()];
        //递归的将main线程组及其子组的所有线程复制到数组
        currentGroup.enumerate(threads, true);
        System.out.println("在main线程组活动的线程：" + Arrays.toString(threads));

        System.out.println();
        ThreadGroup[] groups = new ThreadGroup[currentGroup.activeGroupCount()];
        //递归的将main线程组及其子组复制到数组
        currentGroup.enumerate(groups, true);
        System.out.println("在main线程组活动的线程组：" + Arrays.toString(groups));

        System.out.println();
        //打印线程组信息
        System.out.println("main线程组信息如下：");
        currentGroup.list();

        //批量操作：中断组内所有线程
        System.out.println();
        group2.interrupt();

        //销毁线程组及其所有子组，线程组必须为空，即无活动线程
        //group2.destroy();
    }

    public static void main(String[] args) {
        //ThreadGroupTest.testMainGroup();
        ThreadGroupTest.testThreadGroupMethod();
    }
}
