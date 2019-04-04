package concurrent.semaphore;

import java.util.concurrent.Semaphore;

/**
 * Created by terrence on 2018/11/23.
 */
public class SemaphoreUtils {

    private static final ThreadLocal<Semaphore> ACQUIRED_SEMAPHORE = new ThreadLocal<>();



    /**
     * set acquired semaphore to thread local to be passed across methods because semaphore may be acquired in
     * one method and need try to release in another method
     *
     * @param pSemaphore
     * @return
     */
    public boolean acquireSemaphore(Semaphore pSemaphore) {
        // release first
        releaseSemaphore();
        if (pSemaphore == null) {
            return false;
        }
        if (pSemaphore.tryAcquire()) {
            ACQUIRED_SEMAPHORE.set(pSemaphore);
            return true;
        }
        return false;
    }



    /**
     * only if ACQUIRED_SEMAPHORE is not empty, do the release
     */
    public void releaseSemaphore() {
        Semaphore sema = ACQUIRED_SEMAPHORE.get();
        if (sema != null) {
            sema.release();
            ACQUIRED_SEMAPHORE.remove();
        }
    }
}