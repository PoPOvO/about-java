package bio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * --------------------测试文件相关的I/O--------------------
 * <hr>
 * 测试结果：
 * <br>
 * 1. 若想在文件的任意位置读数据可以使用RandomAccessFile或者FileInputStream，这里测试使用Fis更快。<br>
 * 2. 若想向文件任意位置写数据使用RandomAccessFile<br>
 * 2. 以字节方式读/写文件可采用Fis、Fos；若以字符方式读/写采用FileReader、FileWriter<br>
 *
 * <hr>
 * RandomAccessFile的model各个参数详解：
 * <ul>
 * <li>r，代表以只读方式打开指定文件</li>
 * <li>rw，以读写方式打开指定文件</li>
 * <li>rws，读写方式打开，并对内容或元数据都同步写入底层存储设备</li>
 * <li>rwd，读写方式打开，对文件内容的更新同步更新至底层存储设备</li>
 * <ul>
 *
 * @author xl
 *
 */
public class FileIOTest {
	private final static int fileLength = 1 << 24; 		//l6M
	private final static int bufferSize = 1 << 14;		//16K

	private final static int charCount = 4;				//读取的字符个数

	//向文件写入16M的数据
	public static void fileOutputStreamWriteTest(File source) {
		if (source.length() == fileLength) {
			return;
		}

		//填充缓冲区
		byte[] writeBytes = new byte[bufferSize];
		byte aByte = "a".getBytes()[0];
		for (int i = 0; i < bufferSize; i++) {
			writeBytes[i] = aByte;
		}

		int writeCount = fileLength / bufferSize;
		try (
				FileOutputStream fos = new FileOutputStream(source);
		) {
			for (int i = 0; i < writeCount; i++) {
				fos.write(writeBytes, 0, writeBytes.length);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("文件写入完成！位置：" + source.getAbsolutePath());
	}

	//向文件的最后写一个数据
	public static void randomAccessFileWriteTest(File source) {
		try (
				RandomAccessFile randomAccessFile = new RandomAccessFile(source, "rw");
		) {
			randomAccessFile.seek(randomAccessFile.length());
			randomAccessFile.write("b".getBytes()[0]);
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("RandomAccessFile写文件完成！");
	}

	//使用RandomAccessFile读取文件最后charCount个字符，使用本平台解码
	public static void randomAccessFileReadTest(File source) {
		long strar = System.nanoTime();

		try (
				RandomAccessFile randomAccessFile = new RandomAccessFile(source, "r");
		) {
			//移动文件指针
			randomAccessFile.seek(randomAccessFile.length() - charCount);
			byte[] readBytes = new byte[charCount];
			randomAccessFile.read(readBytes);
			//System.out.println(new String(readBytes));
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("RandomAccessFile读取耗时：" + (System.nanoTime() - strar) + "ns");
	}

	//使用FileInputStream读取文件最后charCount个字符，使用本平台解码
	public static void fileInputStreamReadTest(File source) {
		long strar = System.nanoTime();

		try (
				FileInputStream fis = new FileInputStream(source);
		) {
			//跳过流中的字节并丢弃跳过的字节
			fis.skip(fis.available() - charCount);
			byte[] readBytes = new byte[charCount];
			fis.read(readBytes);
			//System.out.println(new String(readBytes));
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("FileInputStream读取耗时：" + (System.nanoTime() - strar) + "ns");
	}

	public static void main(String[] args) {
		File file = new File("temp.txt");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		//写一个16M的文件
		FileIOTest.fileOutputStreamWriteTest(file);

		FileIOTest.fileInputStreamReadTest(file);
		FileIOTest.randomAccessFileReadTest(file);
		//向文件最后写一个字节的数据
		FileIOTest.randomAccessFileWriteTest(file);
	}
}
