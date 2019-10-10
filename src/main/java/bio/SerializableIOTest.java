package bio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * ---------------------Java对象序列化测试---------------------
 * <br>
 * Java对象的序列化又叫持久化，是将内存的Java对象编码程二进制数据的方式。可以用来：
 * <br>
 * 1. 保存到本地文件
 * <br>
 * 2. 将序列化对象网络传输
 * <br>
 * 如果对象的某个字段不想被序列化，可以如此：
 * 		private transient String name;
 *
 * @author xl
 *
 */
public class SerializableIOTest {
	static class Student implements Serializable {
		/**
		 * 对类的字段和接口做的一个hash，用于保证对象在序列化和反序列换的完整性。
		 * 即当对象序列化之后若数据做了改动，就会和序列号对不上，在反序列化时就
		 * 可以发现。类似于网络中报文完整性的检验。
		 */
		private static final long serialVersionUID = -9116109353314434967L;
		private String name;
		private String id;
		private boolean sex;

		public Student() {}

		public Student(String name, String id, boolean sex) {
			this.name = name;
			this.sex = sex;
			this.id = id;
		}

		@Override
		public String toString() {
			return "Student [name=" + name + ", id=" + id + ", sex=" + sex + "]";
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		//创建一个学生实例
		Student student = new Student("Alice", "001", false);
		File file = new File("serializableTest.ser");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try (FileOutputStream fos = new FileOutputStream(file);
			 ObjectOutputStream oos = new ObjectOutputStream(fos);	//将oos流输出到dos流
		) {
			//序列化对象到文件输出流中
			oos.writeObject(student);
			System.out.println("----序列化完成----");
			System.out.println("生成文件路径：" + file.getAbsolutePath());
		} catch (IOException e1) {
			e1.printStackTrace();
		}


		Student deserializableStudent = null;
		try (FileInputStream fis = new FileInputStream(file);
			 ObjectInputStream ois = new ObjectInputStream(fis);
		) {
			//从文件输入流读取对象数据并且反序列化
			deserializableStudent = (Student) ois.readObject();
			System.out.println("----反序列化完成----");
			System.out.println("目标文件路径：" + file.getAbsolutePath());
			deserializableStudent.toString();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		System.out.println(deserializableStudent.toString());
	}
}
