package concurrent.aqs;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * ---------------AbstractQueuedSynchronizer测试------------------
 *
 * AQS是j.u.c下几乎很多类的基础组件，如ReentrantLock、ReadWriteLock、Semaphore
 *
 * @author xli
 *
 */
public class UserExclusiveLock implements Lock, Serializable {
	private static final long serialVersionUID = 1L;

	static final Sync sync = new Sync();

	/**
	 * 静态内部类，AQS的实现
	 *
	 * 这里构建的是一个悲观锁，即独占锁，1为加锁，0为解锁
	 *
	 * @author xli
	 *
	 */
	static class Sync extends AbstractQueuedSynchronizer {
		private static final long serialVersionUID = 1L;

		/**
		 * 同步状态为1则表明被线程占用
		 */
		@Override
		protected boolean isHeldExclusively() {
			return getState() == 1;
		}

		/**
		 * 尝试获取锁一次
		 */
		@Override
		protected boolean tryAcquire(int arg) {
			//如果cas获取锁成功，将当前线程设置为独占线程
			if (compareAndSetState(0, 1)) {
				setExclusiveOwnerThread(Thread.currentThread());
				return true;
			}
			return false;
		}

		/**
		 * 尝试释放锁
		 */
		@Override
		protected boolean tryRelease(int arg) {
			if (getState() == 0)
				throw new IllegalMonitorStateException("未获得锁，不能释放");
			setExclusiveOwnerThread(null);
			//无需CAS，因为获取锁的线程都被阻塞
			setState(0);
			return true;
		}

		/**
		 * 创建Condition对象，该类的定义在AQS中，只能在AQS的子类中访问
		 */
		Condition newCondition() {
			return new ConditionObject();
		}
	}

	/**
	 * 获取锁，未获取到线程将阻塞到同步队列
	 */
	@Override
	public void lock() {
		sync.acquire(1);
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		sync.acquireInterruptibly(1);
	}

	/**
	 * 尝试获取锁一次，若失败直接返回，不会阻塞
	 */
	@Override
	public boolean tryLock() {
		return sync.tryAcquire(1);
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		return false;
	}

	/**
	 * 释放锁
	 */
	@Override
	public void unlock() {
		sync.release(1);
	}

	@Override
	public Condition newCondition() {
		return sync.newCondition();
	}
}
