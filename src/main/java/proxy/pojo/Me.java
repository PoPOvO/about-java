package proxy.pojo;


import proxy.pojo.IPerson;

/**
 * 我
 *
 * @author xl
 */
public class Me implements IPerson {
	public Me() {}

	@Override
	public boolean eat() {
		System.out.println("我吃饭了");
		return true;
	}
}
