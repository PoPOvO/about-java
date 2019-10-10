package jvm.classloader;

import java.io.IOException;
import java.io.InputStream;

/**
 * 测试自定义类加载器和系统应用程序类加载器加载类的区别
 *
 * 对于任何一个类，其和加载该类的类加载器决定了该类的唯一性。
 *
 * 该类加载器加载与之在同一目录下的类
 *
 * @author xli
 *
 */
public class UserClassLoader extends ClassLoader {
	public UserClassLoader() {
		super();
	}

	//重写loadClass后，破坏了双亲委托模型
	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		String className = name.substring(name.lastIndexOf(".") + 1) + ".class";
		InputStream is = this.getClass().getResourceAsStream(className);

		if (is != null) {
			try {
				//使用vailable()得到输入流的估计字节数，并创建与之匹配的byte数组
				byte[] fileBytes = new byte[is.available()];
				//将输入流读取到字节数组
				is.read(fileBytes);
				//读取字节流生成Class
				return this.defineClass(name, fileBytes, 0, fileBytes.length);
			} catch (IOException e) {
				return super.loadClass(name);
			}
		}

		return super.loadClass(name);
	}


	public static void main(String[] args) {
		UserClassLoader myClassLoader = new UserClassLoader();

		try {
			Class<?> clazz = myClassLoader.loadClass("com.mec.about_classloader.MyClassLoader");
			Object myObj = null;
			Object sysObj = null;

			try {
				sysObj = ClassLoader.getSystemClassLoader()
						.loadClass("com.mec.about_classloader.MyClassLoader").newInstance();
				System.out.println("sysObj | currentClassLoader:" + sysObj.getClass().getClassLoader());

				myObj = clazz != null ? clazz.newInstance() : null;
				System.out.println("myObj | currentClassLoader:" + myObj.getClass().getClassLoader());

				System.out.println("myObj.getClass().equals(sysObj.getClass()):" + myObj.getClass().equals(sysObj.getClass()));
			} catch (InstantiationException | IllegalAccessException e) {
			}

			try {
				System.err.println(myClassLoader.loadClass("java.lang.Object").newInstance());
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			System.out.println("myObj instanceof MyClassLoader:" + (myObj instanceof UserClassLoader));
			System.out.println("sysObj instanceof MyClassLoader:" + (sysObj instanceof UserClassLoader));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
