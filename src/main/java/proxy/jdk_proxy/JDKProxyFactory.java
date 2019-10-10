package proxy.jdk_proxy;

import proxy.core.DoProxyChain;
import proxy.core.IIntercepter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * JDK代理工厂，用于生成JDK代理
 *
 * @author xl
 *
 */
public class JDKProxyFactory<T> {
	private T targetObject;
	private List<IIntercepter> interpecterChain;

	public JDKProxyFactory(T target) {
		targetObject = target;
		interpecterChain = new ArrayList<>();
	}
	/**
	 * 生成代理对象
	 *
	 * @return	代理对象
	 */
	@SuppressWarnings("unchecked")
	public T getProxy() {
		return (T) Proxy.newProxyInstance(targetObject.getClass().getClassLoader(),
				targetObject.getClass().getInterfaces(),
				new InvocationHandler() { 					//InvocationHandler就是一个函数指针的作用，用于回调
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						DoProxyChain chain = new DoProxyChain(interpecterChain, targetObject);
						chain.doChain(method, args);
						return chain.getResult();
					}
				});
	}
	/**
	 * 增加代理动作
	 *
	 * @param intercepter
	 * @return
	 */
	public JDKProxyFactory<T> attachIntercepter(IIntercepter intercepter) {
		if (!interpecterChain.contains(intercepter)) {
			interpecterChain.add(intercepter);
		}
		return this;
	}
	/**
	 * 删除代理动作
	 *
	 * @param intercepter
	 * @return
	 */
	public JDKProxyFactory<T> romoveIntercepter(IIntercepter intercepter) {
		if (interpecterChain.contains(intercepter)) {
			interpecterChain.remove(intercepter);
		}
		return this;
	}
	/**
	 * 一次性增加所有代理动作
	 *
	 * @param chain
	 */
	public void injectActionChain(List<IIntercepter> chain) {
		this.interpecterChain = chain;
	}
}
