package proxy.pojo;

import proxy.core.annotation.DeputyClass;
import proxy.core.annotation.MethodIntercepter;

@DeputyClass
public class She implements IPerson {
	public She() {}

	@MethodIntercepter
	@Override
	public boolean eat() {
		System.out.println("-She吃饭了");
		return true;
	}

	public boolean sleep() {
		System.out.println("-She睡觉了");
		return true;
	}
}
