package concurrent.threadpool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 简单的线程池实现，任务存放到阻塞队列中，使用阻塞队列来控制工作线程，而非wait-notify
 *
 * @author xli
 *
 */
public class SimpleThreadPool implements ThreadPool {
	/**
	 * 最大线程数
	 */
	private static final int MAX_WORKER_NUM = 10;
	/**
	 * 默认线程数
	 */
	private static final int DEFAULT_WORKER_NUM = 4;
	/**
	 * 最小线程数
	 */
	private static final int MIN_WORKER_NUM = 1;
	/**
	 * 任务队列
	 */
	private BlockingQueue<Task> tasks;
	/**
	 * 工作线程
	 */
	private List<Worker> workers;
	/**
	 * 每个工作线程维护自己的编号
	 */
	private final ThreadLocal<Integer> tl;
	/**
	 * 线程编号
	 */
	private AtomicInteger atomInt;
	/**
	 * 控制所有工作线程运行的标志
	 */
	private volatile boolean isAllstop;


	/**
	 * 工作线程，无需暴露给用户，内部类
	 *
	 * @author xli
	 *
	 */
	class Worker extends Thread {
		boolean isStop = false;

		@Override
		public void run() {
			while (!isAllstop && !isStop) {
				Task t;
				try {
					t = tasks.take();
					t.compute();
				} catch (InterruptedException e) {
					//响应中断并结束线程
					break;
				}

				System.out.println("current worker[" + tl.get()  + "] execute task");
			}
		}
	}

	public SimpleThreadPool() {
		this(DEFAULT_WORKER_NUM);
	}

	public SimpleThreadPool(int poolSize) {
		poolSize = poolSize > MAX_WORKER_NUM ? MAX_WORKER_NUM : poolSize;
		workers = Collections.synchronizedList(new ArrayList<Worker>());
		tasks = new LinkedBlockingQueue<Task>();
		atomInt = new AtomicInteger();
		isAllstop = false;
		/**
		 * 每个线程维护一个原子自增的编号
		 */
		tl = new ThreadLocal<Integer>() {
			@Override
			protected Integer initialValue() {
				return atomInt.incrementAndGet();
			}
		};
		addWorkers(poolSize);
	}

	@Override
	public void executeTask(Task t) {
		if (t != null) {
			//这里无需同步，因为使用的是阻塞队列
			try {
				tasks.put(t);
			} catch (InterruptedException e) {
				return;
			}
		}
	}

	/**
	 * 终止所有线程
	 */
	@Override
	public void shutdown() {
		isAllstop = true;

		//若是无任务，则所有线程都被阻塞，因此需要中断结束
		workers.forEach((w) -> {
			w.interrupt();
		});
	}

	/**
	 * 增加工作线程，但是不能超过最大值
	 */
	@Override
	public void addWorkers(int num) {
		/**
		 * 需要同步，可能会产生并发问题
		 */
		synchronized (workers) {
			int cur = workers.size();
			num = (cur + num) > MAX_WORKER_NUM ? MAX_WORKER_NUM-cur : num;

			for (int i = 0; i < num; i++) {
				Worker w = new Worker();
				workers.add(w);
				w.start();
			}
		}
	}

	/**
	 * 停止一些线程，并且从worker中删除，最少剩一个工作线程
	 */
	@Override
	public void removeWorker(int num) {
		if (num < 0)
			return;

		synchronized (workers) {
			int cur = workers.size();
			num = cur-num > MIN_WORKER_NUM ? num : cur-MIN_WORKER_NUM;
			ListIterator<Worker> listIterator = workers.listIterator();

			//从列表删除线程并终止线程（需要中断阻塞的线程）
			while (num-- > 0) {
				Worker w = listIterator.next();
				w.interrupt();
				w.isStop = true;
				listIterator.remove();
			}
		}
	}

	@Override
	public int getWorkersNum() {
		synchronized (workers) {
			return workers.size();
		}
	}

	public static void main(String[] args) {
		ThreadPool pool = new SimpleThreadPool();

		Task t = ()->{};

		for (int i = 0; i < 10; i++) {
			pool.executeTask(t);
		}

		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("cur worker num:" + pool.getWorkersNum());
		pool.addWorkers(5);
		System.out.println("cur worker num:" + pool.getWorkersNum());
		pool.removeWorker(4);
		System.out.println("cur worker num:" + pool.getWorkersNum());
		pool.shutdown();
	}
}
