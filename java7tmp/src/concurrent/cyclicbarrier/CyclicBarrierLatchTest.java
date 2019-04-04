package concurrent.cyclicbarrier;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by terrence on 2018/12/12.
 */
public class CyclicBarrierLatchTest {

    private static final int CONCURRENCY_THREAD_NUMS = 200;

    private static final String TEST_API_URL          = "https://localqa.becn.com/rest/model/REST/NewRestService/v1/rest/com/becn/itemlist?accountId=501709";
    private static final String TEST_API_METHOD       = "GET";
    private static final String TEST_API_HEADER_KEY   = "Authorization";
    private static final String TEST_API_HEADER_VALUE = "Bearer 6arcpwaq5cbthkruhv2p5ok7n89lr1fe27n43u4ff8mpspk5ofdi0j8o8o78wsq1";

    private static final int TIMEOUT_SECONDS = 100;



    public static void main(String[] args) throws InterruptedException {
        Runnable taskTemp = new Runnable() {
            private int iCounter;



            @Override
            public void run() {
                // 发起请求
                //              HttpClientOp.doGet("https://www.baidu.com/");
                iCounter++;
                System.out.println(
                        System.nanoTime() + " [" + Thread.currentThread().getName() + "] iCounter = " + iCounter);
            }
        };
        CyclicBarrierLatchTest latchTest = new CyclicBarrierLatchTest();
        //        latchTest.startTaskAllInOnce(5, taskTemp);
        latchTest.startNThreadsByBarrier(CONCURRENCY_THREAD_NUMS, taskTemp);
    }



    public void startNThreadsByBarrier(int threadNums, Runnable finishTask) throws InterruptedException {
        // 设置栅栏解除时的动作，比如初始化某些值
        CyclicBarrier barrier = new CyclicBarrier(threadNums, finishTask);
        // 启动 n 个线程，与栅栏阀值一致，即当线程准备数达到要求时，栅栏刚好开启，从而达到统一控制效果
        for (int i = 0; i < threadNums; i++) {
            Thread.sleep(100);
            new Thread(new CounterTask(barrier)).start();
        }
        System.out.println(Thread.currentThread().getName() + " out over...");
    }



    private class CounterTask implements Runnable {
        // 传入栅栏，一般考虑更优雅方式
        private CyclicBarrier barrier;



        public CounterTask(final CyclicBarrier barrier) {
            this.barrier = barrier;
        }



        public void run() {
            System.out.println(Thread.currentThread().getName() + " is ready...current time milli seconds: " + System
                    .currentTimeMillis());
            try {
                // 设置栅栏，使在此等待，到达位置的线程达到要求即可开启大门
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " started... current time milli seconds: " + System
                    .currentTimeMillis());
            try {
                new HttpTestUtil().invoke();
            } catch (Exception pE) {
                System.out.println(pE);
            }
        }



        private class HttpTestUtil {
            public void invoke() throws Exception {
                BufferedWriter out = null;
                BufferedReader in = null;
                HttpURLConnection httpConn = null;
                StringBuilder resultBuilder = new StringBuilder();
                // for log
                String targetUrl = TEST_API_URL;
                try {
                    // start performance monitor here
                    URL url = new URL(targetUrl);
                    httpConn = getHttpURLConnection(url, "TLSv1.2");
                    httpConn.setConnectTimeout(TIMEOUT_SECONDS * 1000);
                    httpConn.setReadTimeout(TIMEOUT_SECONDS * 1000);
                    // enable connection output
                    httpConn.setDoOutput(true);
                    // enable connection input
                    httpConn.setDoInput(true);
                    // disable cache use
                    httpConn.setUseCaches(false);
                    // disable auto redirect when response code is 3XX
                    httpConn.setInstanceFollowRedirects(false);
                    httpConn.setRequestMethod(TEST_API_METHOD);
                    // populating request headers
                    httpConn.setRequestProperty(TEST_API_HEADER_KEY, TEST_API_HEADER_VALUE);
                    httpConn.setRequestProperty("Charset", "utf-8");
                    httpConn.connect();
                    System.out.println("HTTP response code: " + httpConn.getResponseCode());
                } finally {
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                    }
                    if (httpConn != null) {
                        httpConn.disconnect();
                    }
                }
            }



            private HttpURLConnection getHttpURLConnection(URL pUrl, String sslVersion) throws IOException {
                if ("HTTPS".equalsIgnoreCase(pUrl.getProtocol())) {
                    SSLContext sslcontext = null;
                    try {
                        if (isBlank(sslVersion)) {
                            sslcontext = SSLContext.getDefault();
                        } else {
                            sslcontext = SSLContext.getInstance(sslVersion);
                        }
                        if (sslcontext != null) {
                            //sslcontext.init(null, null, null);
                            sslcontext.init(null, new TrustManager[] { new TrustAnyTrustManager() },
                                    new java.security.SecureRandom());
                            SSLSocketFactory sslSocketFactory = sslcontext.getSocketFactory();
                            HttpsURLConnection hsc = (HttpsURLConnection) pUrl.openConnection();
                            hsc.setSSLSocketFactory(sslSocketFactory);
                            return hsc;
                        }
                    } catch (KeyManagementException | NoSuchAlgorithmException e) {
                        System.out.println(e);
                    }
                }
                return (HttpURLConnection) pUrl.openConnection();
            }



            private boolean isBlank(String pStr) {
                return pStr == null || pStr.length() < 1;
            }



            private class TrustAnyTrustManager implements X509TrustManager {
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }



                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }



                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[] {};
                }
            }

            private class TrustAnyHostnameVerifier implements HostnameVerifier {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            }
        }
    }
}