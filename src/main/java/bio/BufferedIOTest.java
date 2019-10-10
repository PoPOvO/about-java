package bio;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * --------------------Buffered I/O测试--------------------
 * <hr>
 * 相对于其他Stream一次读取一个字节，Buffered I/O可以一次读写一块数据，
 * 读取大数据时更加的迅速。其默认缓冲区大小：8k。
 *
 * @author xl
 *
 */
public class BufferedIOTest {
	//通过fio复制文件
	public static void copyFileByFio(File source, File target) {
		if (!target.exists()) {
			try {
				target.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		long start = System.nanoTime();

		byte[] bytes = new byte[1 << 16];
		int readByteCount = 0;
		try (
				FileInputStream fis = new FileInputStream(source);
				FileOutputStream fos = new FileOutputStream(target);
		) {
			while ((readByteCount = fis.read(bytes, 0, bytes.length)) > 0) {
				fos.write(bytes, 0, readByteCount);
				fos.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("文件复制完成！By fio，耗时：" + (System.nanoTime() - start) + "ns" );
	}

	//通过bio复制文件
	public static void copyFileByBio(File source, File target) {
		if (!target.exists()) {
			try {
				target.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		long start = System.nanoTime();

		byte[] bytes = new byte[1 << 16];
		int readByteCount = 0;
		try (
				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(source));
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(target));
		) {
			while ((readByteCount = bis.read(bytes, 0, bytes.length)) > 0) {
				bos.write(bytes, 0, readByteCount);
				bos.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("文件复制完成！By bio，耗时：" + (System.nanoTime() - start) + "ns" );
	}

	public static void main(String[] args) {
		//文件大小216770372字节
		File source = new File("abc.7z");
		//空文件
		File target1 = new File("bioCopy.7z");
		File target2 = new File("fioCopy.7z");

		BufferedIOTest.copyFileByBio(source, target1);
		BufferedIOTest.copyFileByFio(source, target2);
	}
}
