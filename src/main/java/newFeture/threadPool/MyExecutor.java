package newFeture.threadPool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MyExecutor {

    public static void main(String[] args) throws InterruptedException {

        //线程池维护线程的最少数量
        int corePoolSize = 5;
        //线程池维护线程的最大数量
        int maximunPoolSize = 10;
        //线程池维护线程所允许的空闲时间 
        long keepAliveTime = 200;
        //线程池维护线程所允许的空闲时间的单位
        TimeUnit unit  = TimeUnit.SECONDS;
        //线程池所使用的缓冲队列
        BlockingQueue<Runnable> queue = new SynchronousQueue<Runnable>();
        // 线程创建和销毁的工厂
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        //线程对拒绝任务的处理策略
        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();
        
        
        ThreadPoolExecutor pool = new ThreadPoolExecutor(corePoolSize, maximunPoolSize, keepAliveTime, unit, queue, threadFactory, handler);

        for (int i = 1; i < 100; i++) {

            Thread.sleep(500);
            System.out.println(i + "线程放入队列中");
            pool.execute(new RunThread(i));
        }
    }
}
