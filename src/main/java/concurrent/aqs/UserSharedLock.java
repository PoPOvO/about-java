package concurrent.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 构建一个同步工具类，功能如下：
 * 任意时刻只允许最多k个线程获取lock。
 * 同步状态：0，表示锁不可再持有；正数表明还可以被正数个线程同时持有
 * 每次持有锁后，state--
 *
 * @author xli
 *
 */
public class UserSharedLock implements Lock {
	static Sync sync = new Sync(2);

	/**
	 * 同步工具，使用共享模式构建锁
	 *
	 * @author xli
	 *
	 */
	static class Sync extends AbstractQueuedSynchronizer {
		private static final long serialVersionUID = 1646297866376492387L;
		int cnt;

		//初始化锁可同时被cnt个线程持有
		public Sync(int cnt) {
			if (cnt > 0) {
				setState(cnt);
				this.cnt = cnt;
			}
		}
		/**
		 * tryAcquireShared的返回值的定义：
		 * 1.负值，表示获取失败
		 * 2.0，表示在共享模式下不可再获得
		 * 3.正值，表示在共享模式下还可以获取正值次
		 */
		@Override
		protected int tryAcquireShared(int arg) {
			if (getState() <= 0)
				return -1;
			if (compareAndSetState(getState(), getState()-1))
				return getState();
			return -1;
		}

		@Override
		protected boolean tryReleaseShared(int arg) {
			if (getState() == cnt)
				throw new IllegalMonitorStateException("未获取锁，不可释放");
			if (compareAndSetState(getState(), getState()+1))
				return true;
			return false;
		}

		Condition newsCondition() {
			return new ConditionObject();
		}
	}

	@Override
	public void lock() {
		sync.acquireShared(1);
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		sync.acquireInterruptibly(1);
	}

	@Override
	public boolean tryLock() {
		return sync.tryAcquireShared(1) > 0;
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		return false;
	}

	@Override
	public void unlock() {
		sync.releaseShared(1);
	}

	@Override
	public Condition newCondition() {
		return sync.newsCondition();
	}
}
