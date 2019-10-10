package concurrent.threadpool;

/**
 * 简单线程池功能定义
 *
 * @author xli
 *
 */
public interface ThreadPool {
	/**
	 * 执行任务，任务队列为空则阻塞
	 */
	void executeTask(Task t);
	/**
	 * 线程池终止，即所有线程都终结。对于阻塞的工作线程需要中断。
	 */
	void shutdown();
	/**
	 * 增加工作线程，但是不能超过最大线程数
	 */
	void addWorkers(int num);
	/**
	 * 删除工作线程，最少为MAX_WORKER_NUM
	 */
	void removeWorker(int num);
	/**
	 * 获取工作线程数
	 */
	int getWorkersNum();
}
