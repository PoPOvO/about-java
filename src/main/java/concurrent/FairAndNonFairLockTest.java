package concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * --------------------------fairLock和non-fairLock测试--------------------------
 *
 * ReentrantLock是Lock的实现类，ReentrantReadWriteLock是ReadWriteLock的实现类。都存在fair-lock和non-fair-lock，即公平锁和非公平锁。
 * synchronized是non-fair-lock。
 * <hr>
 * 1. fairLock:尽可能让等待最久的线程获取锁，即按序的。<br>
 * 2. non-fairLock：不保证获取锁的线程的顺序，即随机的。
 *
 * @author xl
 *
 */
public class FairAndNonFairLockTest {
    static ReentrantLock nonFairLock = new ReentrantLock();
    static ReentrantLock fairLock = new ReentrantLock(true);

    private static final int THREAD_COUNT = 16;

    public static void main(String[] args) {
        TestThread[] tArrays = new TestThread[THREAD_COUNT];
        Lock currentLock = nonFairLock;

        for (int i = 0; i < THREAD_COUNT; i++) {
            (tArrays[i] = new TestThread(currentLock)).setName("t" + i);
        }

        new Thread(() -> {
            currentLock.lock();
            try {
                Thread.sleep(1500);
            } catch (Exception e) {
            } finally {
                currentLock.unlock();
            }
        }).start();

        //第一个线程启动后，其他线程启动
        for (int i = 0; i < THREAD_COUNT; i++) {
            long start = System.currentTimeMillis();
            tArrays[i].setStart(start).start();
        }

        for (Thread t: tArrays) {
            try {
                t.join();
            } catch (InterruptedException e) {
            }
        }
    }
}

class TestThread extends Thread {
    private long start = 0;		//开始时间
    private Lock lock;

    public TestThread(Lock lock) {
        this.lock = lock;
    }

    public TestThread setStart(long start) {
        this.start = start;
        return this;
    }

    @Override
    public void run() {
        lock.lock();
        try {
            Thread.sleep(100);
            System.out.println(this.getName() + "获得锁距离第一个线程启动耗费" + (System.currentTimeMillis() - start) + "ms");
        } catch (Exception e) {
        } finally {
            lock.unlock();
        }
    }
}
