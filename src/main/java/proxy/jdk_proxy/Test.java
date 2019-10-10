package proxy.jdk_proxy;

import proxy.core.IntercepterAdapter;
import proxy.pojo.IPerson;
import proxy.pojo.She;

public class Test {
	public static void main(String[] args) {
		IPerson she = (IPerson)new She();
		JDKProxyFactory<IPerson> factory = new JDKProxyFactory<IPerson>(she);

		factory.attachIntercepter(new IntercepterAdapter() {
			@Override
			public boolean before(Object[] args) {
				System.out.println("饭前1");
				return true;
			}

			@Override
			public boolean after(Object result) {
				System.out.println("饭后1");
				return true;
			}

		}).attachIntercepter(new IntercepterAdapter() {
			@Override
			public boolean before(Object[] args) {
				System.out.println("饭前2");
				return true;
			}

			@Override
			public void end() {
				System.out.println("执行几次?");
			}
		}).attachIntercepter(new IntercepterAdapter() {
			@Override
			public boolean before(Object[] args) {
				System.out.println("饭前3");
				return true;
			}

			@Override
			public void end() {
				System.out.println("执行几次?");
			}

			@Override
			public void error() {
				System.err.println("地震啦");
			}
		});

		IPerson sheProxy = factory.getProxy();
		sheProxy.eat();
	}
}
