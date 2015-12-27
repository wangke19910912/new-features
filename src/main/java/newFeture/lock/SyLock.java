package newFeture.lock;

import java.util.concurrent.locks.ReentrantLock;

public class SyLock {

    private static ReentrantLock lock = new ReentrantLock();

    public static class MyString {

        String a = "123";

        private synchronized void change(String b) {
            a = b;
        }

    }
    public static void main(String[] args) {


        MyString o = new MyString();

        ThreadA threadA = new ThreadA(o);

        ThreadB threadB = new ThreadB(o);

        Thread ta = new Thread(threadA);
        Thread tb = new Thread(threadB);

        ta.start();
        tb.start();
    }


    public static class ThreadA implements Runnable {

        MyString obj;

        ThreadA(MyString a) {
            obj = a;
        }
        public void run() {

            synchronized (obj) {
                while (true) {
                    System.out.println(obj.a);
                    try {
                        Thread.sleep(2000);

                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

            }
        }

    }

    public static class ThreadB implements Runnable {

        MyString obj;

        ThreadB(MyString b) {

            obj = b;
        }
        public void run() {

            System.out.println("run this");
            obj.change("456");

            while (true)
                System.out.println(obj.a);
        }

    }

}
