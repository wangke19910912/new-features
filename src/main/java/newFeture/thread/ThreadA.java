package newFeture.thread;

import static java.lang.System.exit;

public class ThreadA {

    public static void main(String[] args) {
        
//        ThreadB b = new ThreadA().new ThreadB(); // 启动计算线程
//        b.run();
//        //线程A拥有b对象上的锁。线程为了调用wait()或notify()方法，该线程必须是那个对象锁的拥有者
//        synchronized (b) {
//        try {
//            System.out.println("等待对象b完成计算…");
//            //当前线程A等待
//            b.wait();
//            } catch (InterruptedException e)
//           {
//               e.printStackTrace();
//           }
//    }
        Runnable b = new ThreadA().new ThreadB();
        Runnable c = new ThreadA().new ThreadC();

        Thread t1 = new Thread(b,"t1");
        Thread t2 = new Thread(c,"t2");
        System.out.println("thread begin");
        t1.start();
        t2.start();
        System.out.println("main thread exit");
        exit(0);
    }
    class ThreadB implements Runnable {

        public void run() {
            for(int i=0;i<100000;i++){

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("i is :"+i);
            }
        }

    }
    class ThreadC implements Runnable {

        public void run() {
            for(int i=0;i<100000;i++){

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("k is :"+i);
            }
        }

    }
}