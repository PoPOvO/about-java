package proxy.package_scanner;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


/**
 * 功能:扫描非JDK本身的class文件<br><br>
 *
 * 描述:扫描普通文件和Jar归档文件
 * <ol>
 * 	<li><i>普通文件:通过File类进行</i></li>
 * 	<li><i>Jar文件:根据JarURLConnection</i></li>
 * </ol>
 *
 * @author xl
 *
 */
public abstract class ClassScanner {
	public ClassScanner() {
	}

	/**
	 * 用户决定需要进行的操作
	 *
	 * @param clazz
	 */
	public abstract void dealClass(Class<?> clazz);

	private void dealDirectory(String packageName, File dir) {
		File[] children = dir.listFiles();

		for (File file: children) {
			if (file.isDirectory()) {
				dealDirectory(packageName + "." + file.getName(), file);
			} else {
				String name = file.getName();
				if (name.endsWith(".class")) {
					String simpleName = name.substring(0, name.indexOf(".class"));
					String relativePath = packageName.replace("/", ".") + "." + simpleName;
					try {
						Class<?> clazz = Class.forName(relativePath);
						dealClass(clazz);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private void dealJarClassFile(String jarEntryName) throws ClassNotFoundException {
		int dot = jarEntryName.indexOf(".class");
		String name = jarEntryName.substring(0, dot).replace("/", ".");
		Class<?> clazz = Class.forName(name);
		dealClass(clazz);
	}

	/**
	 * 根据clazz类所在的包开始扫描
	 *
	 * @param clazz
	 * @throws FilePathNotSuchException
	 * @throws IOException
	 */
	public void scanner(Class<?> clazz) throws FilePathNotSuchException {
		String packageName = clazz.getPackage().getName();
		try {
			scanner(packageName);
		} catch (IOException e) {
		}
	}

	/**
	 * 根据所提供的的包名扫描
	 *
	 * @param packageName 字符串包名eg:com.xli
	 * @throws FilePathNotSuchException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void scanner(String packageName) throws FilePathNotSuchException, IOException {
		String relativePath = packageName.replace(".", "/");
		//需要得到所有该相对路径的URL，因为有可能是Jar文件或者普通File
		Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(relativePath);
		if (urls == null || !urls.hasMoreElements()) {
			throw new FilePathNotSuchException("[" + relativePath + "]不正确!");
		}

		while (urls.hasMoreElements()) {
			URL url = urls.nextElement();
			if ("jar".equals(url.getProtocol())) {
				//若为jar协议，则通过JarURLConnection连接Jar文件
				JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
				if (jarURLConnection == null) {
					return;
				}

				JarFile jarFile = jarURLConnection.getJarFile();
				//根据Jar归档文件得到所有Jar实体，包括Directory和File
				Enumeration<JarEntry> entries = jarFile.entries();
				while (entries.hasMoreElements()) {
					JarEntry entry = entries.nextElement();
					if (entry.getName().endsWith(".class")) {
						try {
							dealJarClassFile(entry.getName());
						} catch (ClassNotFoundException e) {
						}
					}
				}
			} else {
				try {
					File root = new File(url.toURI());
					if (root != null && root.exists()) {
						dealDirectory(packageName, root);
					}
				} catch (URISyntaxException e) {
				}
			}
		}
	}
}
