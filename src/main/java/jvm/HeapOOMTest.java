package jvm;

import java.util.LinkedList;
import java.util.List;

/**
 * -----------堆OOM测试-----------
 * <hr>
 * 使用的JVM参数：<br>
 * -Xms10m -Xmx10m -XX:+HeapDumpOnOutOfMemoryError<br>
 * 解释:将堆的最大和最小设置为10M，并且在发生OOM时进行堆内存快照dump
 *
 * 结果：<br>
 * java.lang.OutOfMemoryError: Java heap space
 * dumping heap to java_pid19352.hprof ...
 * Heap dump file created [14945032 bytes in 0.158 secs]	
 *
 * @author xl
 *
 */
public class HeapOOMTest {
	public static void main(String[] args) {
		List<HeapOOMTest> list = new LinkedList<>();

		while (true) {
			list.add(new HeapOOMTest());
		}
	}
}
