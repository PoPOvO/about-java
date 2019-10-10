package concurrent.pv;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * 使用阻塞队列实现
 *
 * @author xli
 *
 */
public class BufferVerII implements Buffer {
	private static final int MAX_SIZE = 16;
	LinkedBlockingQueue<Object> queue;

	public BufferVerII() {
		//规定最大缓冲
		queue = new LinkedBlockingQueue<>(MAX_SIZE);
	}

	@Override
	public Object consume() throws Exception {
		Object o = queue.take();
		System.out.println("生产者：库存[" + queue.size());
		return o;
	}

	@Override
	public void produce(Object o) throws Exception {
		queue.put(o);
		System.out.println("生产者：库存[" + queue.size());
	}
}
