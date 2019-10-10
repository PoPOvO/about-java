package concurrent.pv;

public class Test {
	public static void main(String[] args) {
		Buffer buf = new BufferVerII();
		
		for (int i = 0; i < 5; i++)
			new Thread(new Producer(buf)).start();;
		
		for (int i = 0; i < 10; i++)
			new Thread(new Consumer(buf)).start();;
	}
}
