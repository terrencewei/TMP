package concurrent.semaphore;

import java.text.MessageFormat;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by terrence on 2018/11/23.
 */
public class MockLimitedService {

    private AtomicInteger triggedCount = new AtomicInteger(0);// just for test



    public void doSomething() {
        try {
            Thread.sleep(1000);
            System.out.println(MessageFormat.format("doSomething() triggedCount: {0}", triggedCount.incrementAndGet()));
        } catch (InterruptedException pE) {

        }
    }

}