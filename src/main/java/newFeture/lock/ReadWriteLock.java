package newFeture.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLock {

    private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public static void main(String[] args) {

        ThreadB threadB = new ThreadB();
        ThreadA threadA = new ThreadA();
        ThreadC threadC = new ThreadC();

        threadA.run();
        threadB.run();
        threadC.run();
    }

    public static class ThreadA implements Runnable {

        public void run() {

            System.out.println("线程a等待读锁");
            lock.readLock().lock();
            System.out.println("线程a抢到读锁");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            lock.readLock().unlock();
            System.out.println("线程a读锁释放");

        }

    }

    public static class ThreadB implements Runnable {

        public void run() {

            System.out.println("线程B等待读锁");
            lock.readLock().lock();
            System.out.println("线程B抢到读锁");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            lock.readLock().unlock();
            System.out.println("线程b释放读锁");
        }

    }
    public static class ThreadC implements Runnable {

        public void run() {

            System.out.println("线程C等待写锁");
            lock.writeLock().lock();
            System.out.println("线程C抢到写锁");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            lock.writeLock().lock();
            System.out.println("线程C释放写锁");
        }

    }

}
