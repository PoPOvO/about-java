package proxy.core;

import proxy.core.exception.InvalidParameterException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 用于链式调用拦截器
 *
 * @author xl
 *
 */
public class DoProxyChain {
	private int count;
	private List<IIntercepter> interpecterChain;

	private Object result;
	private Object targetObject;

	//所有被代理类的需要拦截的方法的仓库
	private InterceptedMethodStorage storage;

	public DoProxyChain(List<IIntercepter> interpecterChain, Object targetObject) {
		this.targetObject = targetObject;
		this.interpecterChain = interpecterChain;
		this.storage = new InterceptedMethodStorage();
		count = 0;
	}
	/*
	 * 这里要小心，真正目标对象方法执行的返回值不能以递归的形式返回，不然会被覆盖。
	 * 应当只执行一次，将返回值保存，而无需在doChain方法的返回值中返回。
	 */
	/**
	 * 链式调用代理动作，目标方法的执行
	 *
	 * @param method
	 * @param args
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws InvalidParameterException
	 */
	public void doChain(Method method, Object[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InvalidParameterException {
		//如果该方法未被拦截，直接执行
		if (!storage.checkMethodIsExist(targetObject.getClass(), method)) {
			result = method.invoke(targetObject, args);
			return;
		}

		if (count < interpecterChain.size()) {
			surroundPart(method, args, interpecterChain.get(count++));
		} else {
			result = method.invoke(targetObject, args);
		}
	}

	/**
	 * 向目标对象方法切入拦截器
	 *
	 * @param method
	 * @param args
	 */
	public void surroundPart(Method method, Object[] args, IIntercepter intercepter) {
		try {
			intercepter.before(args);
			doChain(method, args);
			intercepter.after(result);
		} catch (Exception e) {
			intercepter.error();
		} finally {
			intercepter.end();
		}
	}

	/**
	 * 得到方法返回值
	 *
	 * @return
	 */
	public Object getResult() {
		return result;
	}
}
