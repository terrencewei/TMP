package concurrent.semaphore;

/**
 * Created by terrence on 2018/11/23.
 */
public class MockTest {

    public static void main(String[] args) {
        MockTest mock = new MockTest();
        mock.test();
    }



    public void test() {
        // prepare mock variables
        final SemaphoreUtils utils = new SemaphoreUtils();
        final MockLimitedService mockLimitedService = new MockLimitedService();
        final MockBizService mockBizService = new MockBizService();
        mockBizService.init(5);

        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mockBizService.doBizLogic(utils, mockLimitedService);
                }
            }).start();
        }

    }

}