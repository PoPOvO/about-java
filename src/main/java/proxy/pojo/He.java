package proxy.pojo;

import proxy.core.annotation.DeputyClass;
import proxy.core.annotation.MethodIntercepter;

@DeputyClass
public class He {
	@MethodIntercepter
	public boolean eat() {
		System.out.println("-He�Է���");
		return true;
	}
	
	//��̬�������ɴ���
	public final boolean sleep() {
		System.out.println("˯����");
		return true;
	}
}
