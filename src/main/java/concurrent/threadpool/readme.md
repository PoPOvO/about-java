# 一.简介
 线程池其实是一种享元模式的实现，是为了能够重用线程，因为线程的创建和销毁需要开销。
 若是能够重用线程而非不断的“创建-销毁”则会提高效率。
  
# 二.线程池的流程
1. 初始化线程池，配置参数，如线程数
2. 用户执行任务，即将任务放到任务队列
3. 工作线程从任务队列去取任务，若无任务则休眠，否则被唤醒
  
# 三.线程池技术的要点
 1. TaskQueue（任务队列）：用户的任务存放的队列，这里使用阻塞队列
 2. WorkThread（工作线程）：执行任务的线程
 3. Task（任务）：用户任务
 

 ** 注意事项：这里对于ThreadPool的shutdown()以及对工作线程的remove需要考虑线程阻塞的情况，因此需要中断来终止线程，线程应该响应中断**
