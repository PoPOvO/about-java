package concurrent;

/**
 *
 * 测试指令重排序及其volatile的禁止重排序特性。
 * 这里要求(x，y)不能等于(0,0)
 *
 * 因为无数据依赖性，计算重排序的话，存在：4*3*2*1 = 24种情况
 *
 * 结果1：(0,1) 例如1234
 * 结果2：(1,0) 例如3412
 * 结果3：(1,1) 例如1324
 * 结果4：(0,0) 例如2413，出现这种情况则一定发生了重排序
 *
 *
 * -----------利用hp关系分析指令之间的可见性-------------
 * JMM中hp规则的出现简化了程序员对指令重排序的判断。程序员不必记住复杂的重排序规则，而仅仅使用
 * hp的规则。
 *
 * 这里的代码分析如下：
 * 根据线程内指令规则，得到1 hp 2， 3 hp 4.
 * 而两个线程之间无任何hp关系。但是这里要求的是1对4可见，3对2可见。可以发现，可以使用volatile禁止
 * 重排序的特性。但是volatle禁止重排序的特性较为复杂，因此可以使用volatile的hp规则，即volatile变
 * 量的写 hp volatile变量的读。因此只需要volatile修饰a、b即可，而无需牢记volatile复杂的重排序规则。
 *
 *
 * 同理这里可以使用synchronized的hp规则。(被注释部分)
 *
 * @author xl
 *
 */
public class InstructReorder {
    private volatile int a = 0;
    private volatile int b = 0;
    protected int x = 0;
    protected int y = 0;

//	private Object lock1 = new Object();
//	private Object lock2 = new Object();

    public void fun1() {
//		synchronized(lock1) {
        a = 1;	//1
//		}
//		synchronized(lock2) {
        x = b;	//2
//		}
    }

    public void fun2() {
//		synchronized(lock2) {
        b = 1;	//3
//		}
//		synchronized(lock1) {
        y = a;	//4
//		}
    }

    public static void main(String[] args) {
        int i = 0;
        boolean flag = true;

        while (flag) {
            InstructReorder is = new InstructReorder();

            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    is.fun1();
                }
            });

            Thread t2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    is.fun2();
                }
            });

            t1.start();
            t2.start();

            try {
                t1.join();
                t2.join();
                i++;
                System.out.println("count：" + i + "(" + is.x + "," + is.y + ")");

                if (is.x == 0 && is.y == 0) {
                    flag = false;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
