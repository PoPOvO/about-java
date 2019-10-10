package proxy.static_proxy;

import proxy.pojo.IPerson;
import proxy.pojo.Me;

public class Test {
	public static void main(String[] args) {
		//目标对象
		IPerson me = new Me();
		//代理对象
		PersonProxy proxy = new PersonProxy(me);
		//代理对象执行实际功能
		proxy.eat();
	}

}
