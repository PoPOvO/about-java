package petty;

import java.io.IOException;
import java.io.InputStream;

/**
 * Java程序启动其他进程
 *
 * @author xli
 *
 */
public class InvokeOtherProcess {
	public static void main(String[] args) {
		Runtime run = Runtime.getRuntime();
		Process process = null;
		try {
			process = run.exec("ping www.baidu.com");
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			process.waitFor(); 						//让当前线程等待直到执行进程的终结
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		InputStream is = process.getInputStream();
		StringBuffer sb = new StringBuffer();
		byte[] buffer = new byte[1 << 10];

		try {
			while (is.available() > 0) {
				is.read(buffer);
				sb.append(new String(buffer));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("SB:" + sb.toString());
	}
}
