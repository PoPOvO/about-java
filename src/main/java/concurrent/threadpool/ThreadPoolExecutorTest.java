package concurrent.threadpool;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * 测试Java的ThreadPool
 *
 *  参数说明：
 *  corePoolSize：核心线程数，当有任务提交时，线程池都会派遣工作线程执行任务，若是无空闲线程，则会新建一个
 *  工作工作线程。而coreSizePool就是一个界限，若是池子中的线程数小于该参数，则新建工作线程并且执行，否则将任务放到任务队列。
 *	maximumPoolSize：池子允许的最大线程数，当池子的线程数到达该值时将不会创建
 *	workQueue：存放任务的组阻塞队列
 *	threadFactory：用于创建工作线程的工厂
 *	keepAliveTime：即空闲时间，当工作线程空闲时，最多存活的时间，当工作线程空闲的时间超过该值时会终止。
 *	TimeUnit：keepAliveTime的单位，在TimeUnit类中选择
 *	RejectedExecutionHandler：拒绝策略。当线程池到达最大线程数，并且任务队列已满时的拒绝策略。
 *
 * 拒绝策略：
 * ThreadPoolExecutor.AbortPolicy:丢弃任务并抛出RejectedExecutionException异常。
 * ThreadPoolExecutor.DiscardPolicy：也是丢弃任务，但是不抛出异常。
 * ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）
 * ThreadPoolExecutor.CallerRunsPolicy：由调用线程处理该任务
 *
 * @author xli
 *
 */
public class ThreadPoolExecutorTest {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		//构造线程池，配置相关参数
		ThreadPoolExecutor pool =
				new ThreadPoolExecutor(5, 10, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>(50), new ThreadPoolExecutor.AbortPolicy());
		//用于设置任务的最大执行个数，这里是两个，两个任务结束后线程池关闭
		CountDownLatch c = new CountDownLatch(2);

		pool.execute(() -> {
			System.out.println("执行任务1");
			c.countDown();
		});

		pool.execute(() -> {
			System.out.println("执行任务2");
			c.countDown();
		});

		//使用Callable接口
		Future<String> f = pool.submit(() -> {
			System.out.println("执行任务3");
			c.countDown();
			return "abc";
		});
		System.out.println(f.get());

		try {
			c.await();
			pool.shutdownNow();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
