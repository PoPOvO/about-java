package petty;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * 测试Java的四种引用方式
 *
 * 1.强引用：引用存在是JVM不会回收引用的对象
 * 2.软引用：当内存不够时JVM将回收该引用的对象
 * 3.弱引用：只有进行GC，就会回收该引用的对象
 * 4.虚引用：等于该对象没有引用，任何时候都有可能回收
 * 
 * @author xl
 *
 */
public class ReferenceTest {
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		ReferenceQueue<String> queue = new ReferenceQueue<>();
		WeakReference<String> wr = new WeakReference<String>(new String("A"), queue);
		
		System.out.println(wr.get());
		System.gc();
		System.out.println(wr.get());
		
		Reference<? extends String> re = queue.poll();
		System.out.println();
	}
}
