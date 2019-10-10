package concurrent.pv;

/**
 * 生产者- 消费者模型
 * 分为有界缓冲以及无界缓冲模型。
 * 这里讲缓冲区定义为接口，可以有不同的实现，即无界、有界都可以。而规定的生产、消费方法也可以有不同的实现。
 *
 * 其实多个生产者和消费者共享产品的过程就好比PV操作共享的信号量。
 *
 * 实现方式（其实就是：“阻塞-唤醒”）：
 * 其实只要遵循两个规则。
 * 1.生产者在buffer满时阻塞，生产后唤醒阻塞的消费者
 * 2.消费者在buffer空时阻塞，消费后唤醒阻塞的生产者
 *
 * 因此实现可以使用：
 * 1.wait()、notify()
 * 2.阻塞队列，使用有界队列LinkedBlockQueue规定buffer大小即可。
 * 3.也可以使用支持线程挂起和唤醒的类，如LockSupport、Condition、Unsafe等。
 *
 * @author xli
 *
 */
public interface Buffer {
	/**
	 * 消费方法
	 */
	Object consume() throws Exception;
	/**
	 * 生产方法
	 */
	void produce(Object o) throws Exception;
}
