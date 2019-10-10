package concurrent;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

/**
 * -----------对强大的Unsafe的测试-------------
 *
 * @author xli
 *
 */
public class UnsafeTest {
    public static void main(String[] args) throws Exception {
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        Unsafe unsafe = (Unsafe) f.get("null");

        System.out.println(unsafe);
    }
}
