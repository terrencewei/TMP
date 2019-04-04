package Util;

/**
 * Created by terrencewei on 2017/10/09.
 */
public class MyThreadTest {

    public static void main(String[] args) throws Exception {
        String a1 = "1";
        String a2 = "2";
        String a3 = "2";
        String a4 = "4";
        String a5 = "5";

        Thread thread = new MyThread(a1);
        thread.start();
        Thread thread2 = new MyThread(a2);
        thread2.start();
        Thread thread3 = new MyThread(a3);
        thread3.start();
        Thread thread4 = new MyThread(a4);
        thread4.start();
        Thread thread5 = new MyThread(a5);
        thread5.start();
    }



    public static void syncTest(String id) throws InterruptedException {
        synchronized (id) {
            System.out.println("begin sleep: " + id);
            Thread.sleep(3000);
            System.out.println("end sleep: " + id);
        }
    }
}

