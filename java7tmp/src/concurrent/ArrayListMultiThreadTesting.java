package concurrent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by terrencewei on 2017/10/26.
 */
public class ArrayListMultiThreadTesting implements Runnable {

    static List<Integer> ls = new ArrayList<>();



    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new ArrayListMultiThreadTesting());
        Thread t2 = new Thread(new ArrayListMultiThreadTesting());
        Thread t3 = new Thread(new ArrayListMultiThreadTesting());
        Thread t4 = new Thread(new ArrayListMultiThreadTesting());
        Thread t5 = new Thread(new ArrayListMultiThreadTesting());
        Thread t6 = new Thread(new ArrayListMultiThreadTesting());
        Thread t7 = new Thread(new ArrayListMultiThreadTesting());
        Thread t8 = new Thread(new ArrayListMultiThreadTesting());
        Thread t9 = new Thread(new ArrayListMultiThreadTesting());
        Thread t10 = new Thread(new ArrayListMultiThreadTesting());

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        t7.start();
        t8.start();
        t9.start();
        t10.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();
        t5.join();
        t6.join();
        t7.join();
        t8.join();
        t9.join();
        t10.join();

        System.out.println("list size: " + ls.size());
        List<Integer> nullElements = new ArrayList<>();
        for (int i = 0; i < ls.size(); ++i) {
            Integer val = ls.get(i);
            // System.out.println("index: " + i + "  value: " + val);
            if (val == null) {
                nullElements.add(val);
            }
        }
        int size = nullElements.size();
        if (size > 0) {
            System.out.println("nullElements size: " + size);
        }
    }



    @Override
    public synchronized void run() {
        try {
            for (int i = 0; i < 10; ++i) {
                ls.add(i);
                Thread.sleep(5);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}