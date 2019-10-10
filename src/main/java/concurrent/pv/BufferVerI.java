package concurrent.pv;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 有界缓冲，使用wait()、notify()实现。
 *
 * @author xli
 *
 */
public class BufferVerI implements Buffer {
	//可以通过配置文件修改
	private static final int MAX_SIZE = 16;
	Queue<Object> queue;

	public BufferVerI() {
		queue = new ConcurrentLinkedQueue<Object>();
	}

	@Override
	public Object consume() throws InterruptedException {
		synchronized (queue) {
			//需要使用while，因为可能发生中断，而中断后，使用if就会略过该过程，会出问题
			while (queue.size() == 0) {
				queue.wait();
			}
			//唤醒阻塞的生产者
			Object o = queue.poll();
			System.out.println("消费者：库存[" + queue.size());
			queue.notify();
			return o;
		}
	}

	@Override
	public void produce(Object o) throws InterruptedException {
		synchronized (queue) {
			while (queue.size() == MAX_SIZE) {
				queue.wait();
			}

			queue.offer(o);
			System.out.println("生产者：库存[" + queue.size());
			queue.notify();
		}
	}
}
