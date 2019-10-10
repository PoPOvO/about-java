package concurrent;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ----------测试ReadWriteLock-----------
 * <hr>
 * 读写锁用于解决多个线程读的问题，当并发时读操作多余写操作时使用ReadWriteLock比synchronized更高效
 * <br>
 * 测试：<br>
 * 1. 同时synchronized读和同时ReadWriteLock方式读，速度差了THREAD_COUNT倍。<br>
 *
 * 2. 使用synchronized进行读写同步和使用ReadWrteLock进行读写同步，ReadWriteLock速度更快。
 *
 * @author xl
 *
 */
public class ReadWriteLockTest {
    private static final byte THREAD_COUNT = 18;			//线程数
    private static boolean testFlag = true;				//测试标志，true：测试多个线程读，false：测试多个线程混合读写
    private static final int DELAY_TIME  = 3;			    //读写延时
    private static final int LIMIT = 6;						//读、写线程的比例

    private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public static synchronized void readOperBySynchronized(Thread t) {
        long start = System.currentTimeMillis();

        while ((System.currentTimeMillis() - start) < DELAY_TIME) {
            //System.out.println(t.getName() + "在读……");
        }
    }

    public static synchronized void writeOperBySynchronized(Thread t) {
        long start = System.currentTimeMillis();

        while ((System.currentTimeMillis() - start) < DELAY_TIME) {
            //System.out.println(t.getName() + "在写东西……");
        }
    }

    public static void readOperByReadWrteLock(Thread t) {
        readWriteLock.readLock().lock();
        long start = System.currentTimeMillis();

        try {
            while ((System.currentTimeMillis() - start) < DELAY_TIME) {
                //System.out.println(t.getName() + "在读……");
            }
        } catch (Exception e) {
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    public static void writeOperByReadWrteLock(Thread t) {
        readWriteLock.writeLock().lock();
        long start = System.currentTimeMillis();

        try {
            while ((System.currentTimeMillis() - start) < DELAY_TIME) {
                //System.out.println(t.getName() + "在写东西……");
            }
        } catch (Exception e) {
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public static void main(String[] args) {
        Thread[] tArrays = new Thread[THREAD_COUNT];

        if (testFlag) {
            for (int i = 0; i < THREAD_COUNT; i++) {
                (tArrays[i] = new ReadBySynchronizedThread()).setName("t" + i);
            }
        } else {
            for (int i = 0; i < THREAD_COUNT; i++) {
                if (i % LIMIT == 0) {
                    (tArrays[i] = new WriteBySynchronizedThread()).setName("t" + i);
                    continue;
                }
                (tArrays[i] = new ReadBySynchronizedThread()).setName("t" + i);
            }
        }

        long start = System.nanoTime();
        for (int i = 0; i < tArrays.length; i++) {
            tArrays[i].start();
        }

        for (Thread t: tArrays) {
            try {
                t.join();
            } catch (InterruptedException e) {
            }
        }
        System.out.println((testFlag ? "多读" : "混合读写：") + "使用synchronized总耗时：" + (System.nanoTime() - start) + "ns");

        System.out.println("————————————————————————————————————————————————————————————————————————————");
        if (testFlag) {
            for (int i = 0; i < THREAD_COUNT; i++) {
                (tArrays[i] = new ReadByReadWriteLockThread()).setName("t" + i);
            }
        } else {
            for (int i = 0; i < THREAD_COUNT; i++) {
                if (i % LIMIT == 0) {
                    (tArrays[i] = new WriteByReadWriteLockThread()).setName("t" + i);
                    continue;
                }
                (tArrays[i] = new ReadByReadWriteLockThread()).setName("t" + i);
            }
        }

        start = System.nanoTime();
        for (int i = 0; i < tArrays.length; i++) {
            tArrays[i].start();
        }

        for (Thread t: tArrays) {
            try {
                t.join();
            } catch (InterruptedException e) {
            }
        }
        System.out.println((testFlag ? "多读" : "混合读写：") + "使用ReadWriteLock总耗时：" + (System.nanoTime() - start) + "ns");;
    }
}

//通过synchronized同步的读线程
class ReadBySynchronizedThread extends Thread {
    @Override
    public void run() {
        super.run();
        ReadWriteLockTest.readOperBySynchronized(this);
    }
}

//通过synchronized同步的写线程
class WriteBySynchronizedThread extends Thread {
    @Override
    public void run() {
        super.run();
        ReadWriteLockTest.writeOperBySynchronized(this);
    }
}

//通过ReadWriteLock同步的读线程
class ReadByReadWriteLockThread extends Thread {
    @Override
    public void run() {
        super.run();
        ReadWriteLockTest.readOperByReadWrteLock(this);
    }
}

//通过ReadWriteLock同步的写线程
class WriteByReadWriteLockThread extends Thread {
    @Override
    public void run() {
        super.run();
        ReadWriteLockTest.writeOperByReadWrteLock(this);
    }
}
