package proxy.cglib_proxy;

import org.xli.cglib_proxy.CglibProxyFatcory;
import org.xli.proxy.core.IntercepterAdapter;
import proxy.pojo.He;

public class Test {
	public static void main(String[] args) {
		He xiaoming = new He();
		CglibProxyFatcory<He> cglibProxyFatcory = new CglibProxyFatcory<He>(xiaoming);
		cglibProxyFatcory.attachAction(new IntercepterAdapter() {
			@Override
			public boolean before(Object[] args) {
				System.out.println("�Է�ǰ1");
				return true;
			}

			@Override
			public boolean after(Object result) {
				System.out.println("�Է���1");
				return true;
			}
			
			@Override
			public void end() {
				System.out.println("������");
			}
			
		}).attachAction(new IntercepterAdapter() {
			@Override
			public boolean before(Object[] args) {
				System.out.println("�Է�ǰ2");
				return true;
			}
			@Override
			public boolean after(Object result) {
				System.out.println("�Է���2");
				return true;
			}

			@Override
			public void end() {
				System.out.println("��ɨ����");
			}
		});
		
		He xiaomingProxy = cglibProxyFatcory.getProxy();
		xiaomingProxy.eat();
	}
}
