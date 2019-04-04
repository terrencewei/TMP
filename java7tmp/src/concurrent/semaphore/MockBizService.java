package concurrent.semaphore;

import java.text.MessageFormat;
import java.util.concurrent.Semaphore;

/**
 * Created by terrence on 2018/11/23.
 */
public class MockBizService {

    private Semaphore available;



    public void init(int maxAvailable) {
        available = new Semaphore(maxAvailable);
    }



    public void doBizLogic(SemaphoreUtils utils, MockLimitedService mockService) {
        // start call mock service with limit
        try {
            if (utils.acquireSemaphore(available)) {
                System.out.println(MessageFormat
                        .format("Acquire semaphore Success! availablePermits:{0}", available.availablePermits()));
                mockService.doSomething();
            } else {
                System.out.println("Acquire semaphore Failed!");
            }
        } catch (Exception e) {
            System.out.println(MessageFormat.format("doBizLogic() error occurs.{0}", e));
        } finally {
            utils.releaseSemaphore();
        }
    }

}