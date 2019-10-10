package proxy.indirect_recursion;

/*
 * 我将递归分为两类：
 * 		(1)A-A递归
 * 		(2)A-B-A递归
 *
 * 		对于(1)式递归，是经常使用的。如汉诺塔递归、一般的XML文件读取递归。都是(1)式递归，这种递归是一个“函数”递归。
 * 意思是一个函数调用自身。
 * 		对于(2)式递归，不经常使用。但是在完成Proxy的链式调用时，使用这种递归非常方便。这种递归一般为两个函数相互递归，即A调用B，
 * B再调用A。而A可以根据递归终止条件执行一些操作。
 *
 * 		(2):因为递归就是堆栈入出栈，进入函数和退出函数就是入栈、出栈的过程。采用(2)式递归方式，可以很简单的形成Proxy chain的包裹形式。
 * 即对目标对象的实际调用包裹多层控制访问的函数。
 *
 * 链式插入interceptor的测试类
 *
 * @author xl
 *
 */
public class ChainInterceptorTest {
	private int flag;

	public ChainInterceptorTest() {
		flag = 0;
	}

	//主体
	public void doChain() {
		if (flag++ < 3) {
			this.surroundPart();
		} else {
			System.out.println("--事务执行--");
		}
	}

	//包裹部分
	private void surroundPart() {
		System.out.println("实际事务之前");
		this.doChain();
		System.out.println("实际事务之后");
	}

	public static void main(String[] args) {
		new ChainInterceptorTest().doChain();
	}
}
