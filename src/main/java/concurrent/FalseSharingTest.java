package concurrent;

/**
 * 测试变量的假共享
 *
 * 1. 进行long填充的测试结果：
 *	耗时:35600429ns
 *	耗时:37350157ns
 *  耗时:37794105ns
 *	耗时:38093203ns
 *	耗时:36755805ns
 *
 * 2. 不进行long填充的测试结果：
 *  耗时:78001936ns
 * 	耗时:72313506ns
 * 	耗时:43960237ns
 * 	耗时:57980272ns
 * 	耗时:81438579ns
 *
 * 3. 使用JDK 8的@Contended注解测试结果：
 * 	耗时:35401316ns
 * 	耗时:38713192ns
 * 	耗时:38194470ns
 *	耗时:41045307ns
 *	耗时:41424735ns
 *
 * @author xl
 *
 */
public class FalseSharingTest {
    @sun.misc.Contended
    protected volatile long a = 0;
    //protected volatile long p0, p1, p2, p3, p4, p5, p6;	//jdk 7后可能会被优化
    @sun.misc.Contended
    protected volatile long b = 0;

    public void modeifyLongValueA(int count) {
        for (int i = 0; i < count; i++) {
            a = i;
        }
    }

    public void modeifyLongValueB(int count) {
        for (int i = 0; i < count; i++) {
            b = i;
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            long start = System.nanoTime();
            FalseSharingTest test = new FalseSharingTest();

            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    test.modeifyLongValueA(2000000);
                }
            });
            Thread t2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    test.modeifyLongValueB(2000000);
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

            System.out.println("耗时:" + (System.nanoTime() - start) + "ns");
        }
    }
}
