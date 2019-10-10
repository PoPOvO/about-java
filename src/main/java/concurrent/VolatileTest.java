package concurrent;

/**
 * volatile好比对变量的单个读/写操作做了同步。
 *
 * 如下代码，操作volatile变量的方法和对这些方法加上synchronized关键字的效果相同。
 * 因为CPU保证单个的读/写操作是原子的，而单个读/写不牵扯到指令重排序，并且volatile
 * 保证了变量的可见性，而synchronized的hp规则也保证了变量的可见性。所以效果相同。
 */
public class VolatileTest {
    private volatile boolean stop = false;

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public boolean checkStop() {
        return this.stop;
    }
}
