package concurrent.pv;

public class Consumer implements Runnable {
	private Buffer buf;
	
	public Consumer(Buffer buf) {
		this.buf = buf;
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				buf.consume();
			} catch (Exception e) {
				break;
			}			
		}
	}
}
