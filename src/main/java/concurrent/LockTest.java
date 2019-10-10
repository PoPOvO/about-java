package concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ------------Lock相关类的测试------------
 * ReentrantLock是Lock接口的实现类
 * <hr>
 * 1. lock()-unlock()效果和synchronized一样。<br>
 * 2. trylock(time)， 超时锁。会在有限时间内尝试获取锁，防止某个线程一直等待，对于超时TryLock()锁的获取是可中断的。<br>
 * 3. lockInterruptibly()，可中断锁。可以在获取锁等待的过程中被中断。
 * <hr>
 * lock过程：CAS volatile变量，若成功，则获取锁，并且出阻塞队列；若失败，则入阻塞队列，则此线程将一直被阻塞。。<br>
 * unlock过程：CAS voltile变量，并且唤醒队首被阻塞的线程。
 *
 * @author xl
 *
 */
public class LockTest {
    private static Object lock1 = new Object();

    private static synchronized long time(long start) {
        return System.currentTimeMillis() - start;
    }

    //测试可中断锁lockInterruptibly()的加锁过程
    public static void acquireLockByLockInterruptibly() {
        Lock lock = new ReentrantLock();
        long start = System.currentTimeMillis();
        //线程1阻塞5s
        Thread t1 = new Thread(() -> {
            lock.lock();
            try {
                Thread.sleep(5000);
                System.out.println("t1阻塞结束,耗时:" + time(start) + "ms");
            } catch (Exception e) {
            } finally {
                lock.unlock();
            }
        });

        //线程2尝试使用lockInterruptibly锁，并且在获取锁而阻塞的过程中被中断
        Thread t2 = new Thread(() -> {
            try {
                lock.lockInterruptibly();
                try {
                    System.out.println("t2获得锁!,耗时:" + time(start) + "ms");
                } catch (Exception e) {
                } finally {
                    lock.unlock();
                }
            } catch (InterruptedException e1) {
                System.out.println("t2被中断！,耗时" + time(start) + "ms");
            }
        });


        t1.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }

        t2.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        //中断获取锁而阻塞的t2
        t2.interrupt();
    }

    //测试超时tryLock()的加锁过程
    public static void acquireLockByTimeOutTryLock() {
        Lock lock = new ReentrantLock();
        long start = System.currentTimeMillis();

        //线程1阻塞5s
        Thread t1 = new Thread(() -> {
            lock.lock();
            try {
                Thread.sleep(1500);
                System.out.println("t1阻塞结束,耗时:" + time(start) + "ms");
            } catch (Exception e) {
            } finally {
                lock.unlock();
            }
        });

        //线程2尝试使用tryLock()获取锁，必须要加if判断，因为tryLock不会一直等待获取锁，只会尝试一次
        Thread t2 = new Thread(() -> {
            try {
                if (lock.tryLock(2000, TimeUnit.MILLISECONDS)) {
                    try {
                        System.out.println("t2获得锁！，耗时:" + time(start) + "ms");
                    } catch (Exception e) {
                    } finally {
                        lock.unlock();
                    }
                }
            } catch (InterruptedException e) {
                System.out.println("t2被中断！,耗时" + time(start) + "ms");
            }
        });

        t1.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        t2.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        t2.interrupt();
    }

    //测试tryLock()的加锁过程
    public static void acquireLockByTryLock() {
        long start = System.currentTimeMillis();
        Lock lock = new ReentrantLock();

        //线程1阻塞5s
        Thread t1 = new Thread(() -> {
            lock.lock();
            try {
                Thread.sleep(5000);
                System.out.println("t1阻塞结束,耗时:" + time(start) + "ms");
            } catch (Exception e) {
            } finally {
                lock.unlock();
            }
        });

        //线程2尝试使用tryLock()获取锁，必须要加if判断，因为tryLock不会一直等待获取锁，只会尝试一次
        Thread t2 = new Thread(() -> {
            if (lock.tryLock()) {
                try {
                    System.out.println("t2获得锁！，耗时:" + time(start) + "ms");
                } catch (Exception e) {
                } finally {
                    lock.unlock();
                }
            } else {
                System.out.println("t2未获得锁！");
            }
        });

        t1.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }

        t2.start();
    }

    //测试线程获取synchronized锁后阻塞的情况
    public static void acquireLockBySynchronized() {
        long start = System.currentTimeMillis();
        Thread t1 = new Thread(() -> {
            synchronized (lock1) {
                try {
                    Thread.sleep(5000);
                    System.out.println("t1阻塞结束,耗时:" + time(start) + "ms");
                } catch (InterruptedException e) {
                }
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (lock1) {
                System.out.println("t2获得锁！，耗时:" + time(start) + "ms");
            }
        });

        t1.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }

        t2.start();
        try {
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //LockTest.acquireLockBySynchronized();
        //LockTest.acquireLockByTryLock();
        //LockTest.acquireLockByLockInterruptibly();
        LockTest.acquireLockByTimeOutTryLock();
    }
}
