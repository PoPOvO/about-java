package jvm;

/**
 * ------------虚拟机栈溢出测试-----------<hr>
 * 使用VM参数设定栈的深度：-Xss128k<br>
 *
 * 结果:<br>
 * Exception in thread "main" java.lang.StackOverflowError
 *
 * @author xl
 *
 */
public class VmStackOverflowTest {
	public void recursive(int a, int b) {
		a++;
		b++;
		recursive(a, b);
	}

	public static void main(String[] args) {
		new VmStackOverflowTest().recursive(1, 2);
	}
}

