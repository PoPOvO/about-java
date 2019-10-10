package proxy.core;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import proxy.core.annotation.DeputyClass;
import proxy.core.annotation.MethodIntercepter;
import proxy.core.exception.InvalidParameterException;
import proxy.package_scanner.ClassScanner;

/**
 * 掌管待拦截方法的仓库
 *
 * @author xl
 *
 */
public class InterceptedMethodStorage {
	protected static final Map<Class<?>, List<Method>> interceptedMethodStorage;

	static {
		interceptedMethodStorage = new HashMap<>();
		initStorage();
	}

	public InterceptedMethodStorage() {
	}

	/**
	 * 初始化方法仓库。该方法会扫描包下的类，并找到@DeputyClass标记的类并将@MethodIntercepter标记的方法装入方法仓库
	 */
	protected static void initStorage() {
		String format = new SimpleDateFormat().format(Calendar.getInstance().getTime());
		try {
			new ClassScanner() {
				@Override
				public void dealClass(Class<?> clazz) {
					if (clazz.isAnnotationPresent(DeputyClass.class)) {
						Method[] methods = clazz.getDeclaredMethods();
						List<Method> methodList = new ArrayList<>();
						for (Method e: methods) {
							if (e.isAnnotationPresent(MethodIntercepter.class)) {
								methodList.add(e);
							}
						}
						interceptedMethodStorage.put(clazz, methodList);
					}
				}
			}.scanner(PropertiesReader.getProperty("base_path"));
			System.err.println(format+ ":Init Storage Succeed");
		} catch (Exception e) {
			System.err.println(format+ ":Init Storage Failed");
		}
	}

	private boolean checkMethodParameter(Class<?>[] methodOneParas, Class<?>[] methodTwoParas) {
		if (methodOneParas.length != methodTwoParas.length) {
			return false;
		}

		for (int i = 0; i < methodOneParas.length; i++) {
			if (methodOneParas[i] !=  methodTwoParas[i]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 检测方法是否存在于Storage中
	 *
	 * @param clazz
	 * @param method
	 * @return
	 * @throws InvalidParameterException
	 */
	public boolean checkMethodIsExist(Class<?> clazz, Method method) throws InvalidParameterException {
		boolean exist = false;

		if (interceptedMethodStorage.get(clazz) == null) {
			initStorage();
			if (interceptedMethodStorage.get(clazz) == null) {
				throw new InvalidParameterException("[" + clazz + "]参数异常。请检查该类是否标记了@DeputyClass");
			}
		}

		List<Method> methodList = interceptedMethodStorage.get(clazz);
		exist = methodList.contains(method);
		//因为接口代理(JDK代理)的method为接口的抽象方法，和标记的类的方法不一致，需要特殊处理
		if (!exist) {
			for (Method e: methodList) {
				if (e.getName().equals(method.getName())
						&& checkMethodParameter(e.getParameterTypes(), method.getParameterTypes())) {
					exist = true;
					break;
				}
			}
		}
		return exist;
	}
}
