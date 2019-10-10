package jvm;

/**
 * -------------元空间OOM测试--------------
 * <hr>
 * PermGen(永久代)是HotSpot对方法区的实现，在堆中，用来存放类的信息，及方法，常量等。但是
 * JDK 8之后，从对重移除了永久代。而是将其转移到了本地内存，称作元空间(MetaSpace)。
 * 这里使用JVM参数：-XX:MaxMetaspaceSize=1m，因为JVM启动需要加载很多系统类，会造成元空间OOM。
 * 默认情况下不设置MetaSpace，其大小自动增长，只受本地内存限制。
 * <br>
 *
 * 运行结果：<br>
 * 	Error occurred during initialization of VM 
 *	OutOfMemoryError: Metaspace
 *
 * @author xl
 *
 */
public class MetaSpaceOOMTest {
	public static void main(String[] args) {
		System.out.println("a");
	}
}
