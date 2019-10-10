package concurrent.threadpool;

/**
 * 用户任务功能定义
 *
 * @author xli
 *
 */
@FunctionalInterface
public interface Task {
	/**
	 * 任务执行体
	 */
	void compute();
}

