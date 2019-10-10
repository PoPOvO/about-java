package proxy.cglib_proxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.xli.proxy.core.DoProxyChain;
import org.xli.proxy.core.IIntercepter;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * ���������
 * 
 * @author xl
 *
 * @param <T>  Ŀ����
 */
public class CglibProxyFatcory<T> {
	private T targetObject;

	private List<IIntercepter> interpecterChain;
	
	
	public CglibProxyFatcory(T target) {
		targetObject = target;
		interpecterChain = new ArrayList<>();
	}
	/**
	 * ���ɴ������
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T getProxy() {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(targetObject.getClass());
		enhancer.setCallback(new MethodInterceptor() {
			@Override
			public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
				//ÿ�ζ���Ҫ��newһ��DoProxyChain����Ϊcount���¼���
				DoProxyChain chain = new DoProxyChain(interpecterChain, targetObject);
				chain.doChain(method, args);
				return chain.getResult();
			}
		});
		
		return (T) enhancer.create();
	}
	
	/**
	 * ���Ӵ�����
	 *
	 * @param intercepterAdapter
	 * @return
	 */
	public CglibProxyFatcory<T> attachAction(IIntercepter intercepter) {
		if (!interpecterChain.contains(intercepter)) {
			interpecterChain.add(intercepter);	
		}
		return this;
	}
	/**
	 * ɾ��������
	 * 
	 * @param intercepterAdapter
	 * @return
	 */
	public CglibProxyFatcory<T> romoveAction(IIntercepter intercepter) {
		if (interpecterChain.contains(intercepter)) {
			interpecterChain.remove(intercepter);
		}
		return this;
	}
	/**
	 * һ�����������д�����
	 * 
	 * @param chain
	 */
	public void injectActionChain(List<IIntercepter> chain) {
		this.interpecterChain = chain;
	}
}
