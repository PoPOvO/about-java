package petty;


//对于相互包含的类，通过构造函数将自己set时可能会发生this逃逸
public class ThisEscapeTwo {
	protected final String id;

	public ThisEscapeTwo(DataSource source) {
		source.setThisEscapeTwo(this);

		//延时100ms，可能是初始化其它字段，也可能是其他耗时的操作
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}
		id = "ABC";
	}

	public void getMessage(String mess) {
		System.out.println("Source:" + mess);
	}

	@Override
	public String toString() {
		return "ThisEscapeTwo [id=" + id + "]";
	}

	public static void main(String[] args) {
		final DataSource s = new DataSource();

		Thread t = new Thread(() -> {
			new ThisEscapeTwo(s);
		});
		t.start();
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		s.postMessage();
	}
}

//数据源
class DataSource {
	private ThisEscapeTwo escapeTwo;

	public void setThisEscapeTwo(ThisEscapeTwo escapeTwo) {
		this.escapeTwo = escapeTwo;
	}

	public void postMessage() {
		if (this.escapeTwo != null) {
			escapeTwo.getMessage(escapeTwo.toString());
		}
	}
}
