package concurrent.pv;

public class Producer implements Runnable {
	private Buffer buf;
	
	public Producer(Buffer buf) {
		this.buf = buf;
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				buf.produce(new Object());
			} catch (Exception e) {
				//��Ҫ��Ӧ�ж�
				break;
			}			
		}
	}
}
