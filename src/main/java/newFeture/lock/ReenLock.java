package newFeture.lock;

import java.util.concurrent.locks.ReentrantLock;

public class ReenLock {

    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {

        ThreadB threadB = new ThreadB();
        ThreadA threadA = new ThreadA();


        threadA.run();
        threadB.run();
    }

    public static class ThreadA implements Runnable {

        public void run() {

            System.out.println("线程a等待锁");
            lock.lock();
            System.out.println("线程a抢到锁");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            lock.unlock();
            System.out.println("线程a锁释放");

        }

    }

    public static class ThreadB implements Runnable {

        public void run() {

            System.out.println("线程B等待锁");
            lock.lock();
            System.out.println("线程B抢到锁");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            lock.unlock();
            System.out.println("线程b释放锁");
        }

    }
}
