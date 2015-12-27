package newFeture.threadPool;

public class RunThread implements Runnable {

    private int num;
    RunThread(int num) {
        this.num = num;
    }
    public void run() {

        try {
            Thread.sleep(5000);
            System.out.println("线程" + num + "正在执行....");
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
