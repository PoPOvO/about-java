package petty;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


/**
 * Map转为JavaBean
 *
 * 1.使用反射
 * 最好使用set和get方法访问。因为只需要操作bean中存在get和set方法的属性。
 *
 * @author xli
 *
 */
public class JavaBeanTransform {
	/**
	 * Map转为Bean：使用反射
	 */
	public static Object mapToJavaBean(Map<String, Object> map, Class<?> beanClass) throws Exception {
		if (map == null || beanClass == null)
			return null;

		Object res = beanClass.newInstance();
		Field[] fields = beanClass.getDeclaredFields();

		for (Field f: fields) {
			Method m = beanClass.getDeclaredMethod("set" + f.getName().substring(0, 1).toUpperCase()
					+ f.getName().substring(1), f.getType());

			if (m == null)
				continue;

			m.invoke(res, map.get(f.getName()));
		}

		return res;
	}

	/**
	 * Bean转化为Map：使用反射
	 */
	public static Map<String, Object> javaBeanToMap(Object bean) throws Exception {
		if (bean == null)
			return null;

		Map<String, Object> map = new HashMap<String, Object>();
		Class<?> clazz = bean.getClass();
		Field[] fields = clazz.getDeclaredFields();

		for (Field f: fields) {
			Method m =  clazz.getDeclaredMethod((f.getType() == Boolean.class ? "is" : "get")
					+ f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1), f.getType());

			if (m == null)
				continue;

			m.invoke(bean, map.get(f.getName()));
		}

		return map;
	}

	public static void main(String[] args) {
		StudentModel model  = null;
		Map<String, Object> map = new HashMap<>();
		map.put("name", "网吧");
		map.put("id", "123");
		map.put("sex", true);

		try {
			model = (StudentModel) mapToJavaBean(map, StudentModel.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(model);
	}
}

class StudentModel {
	private String name;
	private String id;
	private boolean sex;

	public StudentModel() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isSex() {
		return sex;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}

	@Override
	public String toString() {
		return "StudentModel [name=" + name + ", id=" + id + ", sex=" + sex + "]";
	}
}
