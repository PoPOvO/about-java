package bio;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * --------------------Piped I/O测试--------------------
 * <hr>
 * Piped I/O用于两个线程之间的通信，但是这两个线程必须在同一个进程内。
 *
 * <br>
 * 线程通信的方式两种：<br>
 * 1.共享变量（2个及其以上）<br>
 * 2.消息传递（2个）<br>
 * Piped I/O属于第二种
 *
 * <br>
 * <strong>注意：不要让一个线程同时进行读、写，因为read会阻塞，可能会导致死锁的发生。</strong>
 *
 * @author xl
 *
 */
public class PipedIOTest {
	public static void main(String[] args) throws IOException {
		final PipedOutputStream pos = new PipedOutputStream();
		//输出流和输入流关联
		final PipedInputStream pis = new PipedInputStream(pos);

		Thread t1 = new Thread(() -> {
			try {
				pos.write("Hello!".getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					pos.close();
				} catch (IOException e) {
				}
			}
		});

		Thread t2 = new Thread(() -> {
			byte[] buffer = new byte[1 << 10];	//1k
			int size = 0;
			try {
				size = pis.read(buffer);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					pis.close();
				} catch (IOException e) {
				}
			}
			System.out.println(new String(buffer, 0, size));
		});

		t1.start();
		t2.start();

		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
