package newFeture.thread;

public class ThreadA {

    public static void main(String[] args) {
        
        ThreadB b = new ThreadA().new ThreadB(); // 启动计算线程
        b.run();
        //线程A拥有b对象上的锁。线程为了调用wait()或notify()方法，该线程必须是那个对象锁的拥有者        
        synchronized (b) {          
        try {                
            System.out.println("等待对象b完成计算…");                
            //当前线程A等待              
            b.wait();          
            } catch (InterruptedException e)
           {              
               e.printStackTrace();      
           }     
    }
        
    }
    class ThreadB implements Runnable {

        public void run() {
            for(int i=0;i<100000;i++){
                
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.print("i is :"+i);
            }
        }

    }
}