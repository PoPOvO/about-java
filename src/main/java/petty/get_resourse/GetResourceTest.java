package petty.get_resourse;

/*
 * 与Web环境对比
 * */
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;

public class GetResourceTest {
	public GetResourceTest() {
	}

	protected static void separator() {
		System.out.println("—————————————————————————————————————"
				+ "———————————————————————————————————————————————");
	}

	public static void main(String[] args) {
		URL url = GetResourceTest.class.getResource("/com/mec");
		try {
			System.out.println(URLDecoder.decode(url.toString(), "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		GetResourceTest.separator();

		try {
			Enumeration<URL> urls = ClassLoader.getSystemResources("com/mec");
			while (urls.hasMoreElements()) {
				System.out.println(URLDecoder.decode(urls.nextElement().toString(), "utf-8"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		GetResourceTest.separator();

		try {
			Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources("com/mec");
			while (urls.hasMoreElements()) {
				System.out.println(URLDecoder.decode(urls.nextElement().toString(), "utf-8"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		GetResourceTest.separator();
		System.out.println("SystemLoader:\n" + ClassLoader.getSystemClassLoader() + "\n");
		System.out.println("GetResourceTest.class.getClassLoader:\n" + GetResourceTest.class.getClassLoader() + "\n");
		System.out.println("Thread.currentThread().getContextClassLoader:\n" + Thread.currentThread().getContextClassLoader() + "\n");
	}
}
