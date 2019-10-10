package proxy.core;

/**
 * 拦截器的基本行为
 *
 * @author xl
 *
 */
public interface IIntercepter {
	/**
	 * 目标对象方法执行前，拦截器的动作
	 *
	 * @param args 目标对象方法的参数
	 * @return
	 */
	boolean before(Object[] args);
	/**
	 * 目标独对象方法执行后，拦截器的动作
	 *
	 * @param result 目标对象方法的返回值
	 * @return
	 */
	boolean after(Object result);
	/**
	 * 目标对象方法执行时产生异常后， 拦截器的动作
	 */
	void error();
	/**
	 * 无论目标对象方法是否产生异常，拦截器都必须执行的动作
	 */
	void end();
}
