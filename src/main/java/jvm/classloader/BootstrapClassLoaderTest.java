package jvm.classloader;

public class BootstrapClassLoaderTest {
	public static void main(String[] args) {
		System.out.println(System.getProperty("sun.boot.class.path").replace(";", "\n"));
	}
}
