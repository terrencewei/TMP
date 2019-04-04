package Util;

/**
 * Created by terrencewei on 2017/09/27.
 */
public class MyThread extends Thread {

    private String id;



    public MyThread(String id) {
        this.id = id;
    }



    public void run() {
        try {
            MyThreadTest.syncTest(id);
        } catch (InterruptedException pE) {
            pE.printStackTrace();
        }
    }
}
