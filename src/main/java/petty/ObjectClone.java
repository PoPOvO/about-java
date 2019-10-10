package petty;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * ------------对象克隆测试---------------
 * <hr>
 * Object的clone方法是浅克隆，即待克隆的类实例域中存在对象时，仅仅clone其引用。<br>
 * 一个对象想要被克隆要满足两个条件：<br>
 * 1. 实现Cloneable接口，虽然是空接口，但表明该对象可以被clone <br>
 * 2. 覆盖Object的clone方法<br>
 *
 * 深克隆的方式：<br>
 * 1. 使用实例域对象的clone方法clone自身。对于待克隆对象A的对象实例域B也实现Cloneable接口，并覆盖clone方法。
 * 先调用A的clone方法clone自身，再调用B的clone方法克隆它自身并将该值覆盖A
 * 对象的B实例域。<br>
 * 2. 使用序列化来clone对象A中的对象实例域。但是这种方式代价太高，因为序列化很慢并且不安全。
 *
 * @author xl
 *
 */
public class ObjectClone {
	public static void main(String[] args) {
		Person p1 = new Person("Alice", true, (byte)12, new Student("001"));
		System.out.println(p1);
		try {
			Person p2 = (Person) p1.clone();
			System.out.println(p2);
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
}

class Person implements Cloneable, Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -5773931460922468711L;
	protected String name;
	boolean sex;
	byte age;
	Student s;

	public Person(String name, boolean sex, byte age, Student s) {
		super();
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.s = s;
	}

//	//深克隆1：调用对象实例域的clone方法。待克隆对象和其对象字段必须实现Cloneable接口
//	@Override
//	protected Object clone() throws CloneNotSupportedException {
//		Person person = (Person)super.clone();
//		person.s = (Student) this.s.clone();
//		return person;
//	}

	//深克隆2：使用对象序列clone。待克隆对象的对象字段必须实现Serializable接口
	@Override
	protected Object clone() throws CloneNotSupportedException {
		Person person = null;
		try (
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(baos);
		) {
			//将待克隆对象序列化
			oos.writeObject(this);
			try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));) {
				//反序列化待克隆对象并在内存中重建
				person = (Person)ois.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return person;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", sex=" + sex + ", age=" + age + ", s=" + s + "]";
	}
}

class Student implements Cloneable, Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 2741427318104578724L;
	String id = "001";

	public Student(String id) {
		this.id = id;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
