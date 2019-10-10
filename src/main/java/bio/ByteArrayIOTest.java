package bio;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * ------------ByteArrayIO测试-------------<hr>
 * ByteArrayI/O负责读取byte数组或写入byte数组，可以组合其他流使用。
 *
 * @author xl
 *
 */
public class ByteArrayIOTest {
	public static void main(String[] args) {
		//数据源，也可以是其他
		byte[] dataSource = "12345678".getBytes();
		ByteArrayInputStream bais = new ByteArrayInputStream(dataSource);

		int data = bais.read();
		while (data != -1) {
			System.out.println(data);
			data = bais.read();
		}

		try {
			bais.close();
		} catch (IOException e) {
		}

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		//将dos流的数据输出到baos流
		DataOutputStream dos = new DataOutputStream(baos);
		byte[] writeBytes = "1234".getBytes();
		try {
			dos.write(writeBytes);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			baos.close();
		} catch (IOException e) {
		}

		//将流中的内容输出到byte数组
		System.out.println(Arrays.toString(baos.toByteArray()));
	}
}
