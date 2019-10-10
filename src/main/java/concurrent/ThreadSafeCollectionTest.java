package concurrent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * 线程安全的集合测试
 * <br>
 * 使用Collections中的同步容器。
 *
 * @author xl
 *
 */
public class ThreadSafeCollectionTest {
    public static void main(String[] args) {
        List<String> list = Collections.synchronizedList(new ArrayList<>());

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 3; i++) {
                    list.add("A" + i);
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 3; i++) {
                    list.add("B" + i);
                }
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(list);
    }
}
