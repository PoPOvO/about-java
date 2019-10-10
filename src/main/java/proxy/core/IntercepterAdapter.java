package proxy.core;

/**
 * 应用于代理对象动作的适配器类
 *
 * @author xl
 *
 */
public class IntercepterAdapter implements IIntercepter {
	@Override
	public boolean before(Object[] args) {
		return false;
	}

	@Override
	public boolean after(Object result) {
		return false;
	}

	@Override
	public void error() {}

	@Override
	public void end() {}
}
