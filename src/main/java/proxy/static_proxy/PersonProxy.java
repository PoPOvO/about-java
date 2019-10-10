package proxy.static_proxy;

import proxy.pojo.IPerson;

/**
 * Person的代理类
 *
 * @author xl
 *
 */
public class PersonProxy implements IPerson {
	/**
	 * 目标对象
	 */
	private IPerson targetObject;

	public PersonProxy(IPerson person) {
		this.targetObject = person;
	}

	@Override
	public boolean eat() {
		System.out.println("吃饭前:准备筷子、擦桌子");
		targetObject.eat();
		System.out.println("吃饭后:洗碗");
		return true;
	}
}
