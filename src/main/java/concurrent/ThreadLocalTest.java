package concurrent;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * ---------------ThreadLocal测试---------------
 *<hr>
 * 如下代码开启多个线程，并且对每个线程设置序号Seq。
 *<hr>
 * ThreadLocal为线程本地变量的访问提供了一种手段。
 * <br>
 * 对应关系：<br>
 * Thread------------>ThreadLocalMap(ThreadLocal, Object)<br>
 * ThreadLocal--------->一个线程的本地变量
 * <br>
 * 方法：<br>
 * 1. protected T initialValue() //若为set，get时返回的默认值。可以覆盖。<br>
 * 2. void set(T value)	<br>
 * 3. T get()	<br>
 * 4. void remove() <br>
 *
 * 注意：由于ThreadLocalMap中的key，即ThreadLocal是使用的弱引用，如果外界无其他引用存在，gc时就会释放key，
 * 因此value就无法访问到，虽然ThreadLocalMap做出了应对，在get时会擦除key为null的Entry。但是不进行get，
 * 单线程一直存在，就仍会发生内存泄漏。因此，要注意，对于不使用的ThreadLocal变量及时进行remove，或者
 * 将ThreadLocal设置为static的。
 *
 *
 * @author xl
 *
 */
public class ThreadLocalTest {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger();

        //覆盖超类的初始值方法，设定初始值
        ThreadLocal<Integer> startSeq = new ThreadLocal<Integer>() {
            @Override
            protected Integer initialValue() {
                return atomicInteger.incrementAndGet();
            }
        };

        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "的序号为：" + startSeq.get());
        });
        Thread t2 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "的序号为：" + startSeq.get());
        });
        Thread t3 = new Thread(() -> {
            System.gc();
            System.out.println(Thread.currentThread().getName() + "的序号为：" + startSeq.get());
        });

        t1.start();
        t2.start();
        t3.start();
    }
}
