package Util;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by terrencewei on 2017/10/26.
 */
public class BatchTaskExecuteTest {

    private static int             totalTaskCount       = 10;
    private static int             finishTaskCount      = 0;
    private static ExecutorService normalTaskThreadPool = Executors.newFixedThreadPool(11);



    public static void main(String[] xxxxx) throws Exception {

        List<Future<GenericTaskResult>> submitTasks = new ArrayList<Future<GenericTaskResult>>();

        System.out.println("Begin submit task");
        for (int i = 1; i <= totalTaskCount; i++) {
            submitTasks.add(submitTask(new GenericTask(i)));
        }
        System.out.println("End submit task");

        List<GenericTaskResult> results = new ArrayList<GenericTaskResult>();
        for (Future<GenericTaskResult> submitTask : submitTasks) {
            GenericTaskResult taskResult = submitTask.get();
            results.add(taskResult);
            System.out.println("task " + taskResult.getB() + " end: " + ++finishTaskCount + "/" + totalTaskCount);
        }

        System.out.println("Begin log task results.");
        results.get(0).setEmptyTask(true);
        results.get(2).setEmptyTask(true);
        results.get(3).setEmptyTask(true);
        System.out.println(printTaskReport(results));
        System.out.println("End log task results.");

        shutDownExec(normalTaskThreadPool);
    }



    private static String printTaskReport(List<GenericTaskResult> pResults) {
        Formatter success = new Formatter();
        Formatter failed = new Formatter();
        String rowSeperate = "---------------------------------------------------------------------------------------\n";
        int successCount = 0;
        int failedCount = 0;
        success.format(rowSeperate);
        success.format("%-1s %-5s %-1s %-34s %-1s %-40s %-1s\n", "|", "No.", "|", "Login name", "|", "UUID", "|");
        failed.format(rowSeperate);
        failed.format("%-1s %-5s %-1s %-34s %-1s %-40s %-1s\n", "|", "No.", "|", "Login name", "|", "UUID", "|");
        for (GenericTaskResult result : pResults) {
            if (!result.isEmptyTask()) {
                success.format("%-1s %-5s %-1s %-34s %-1s %-40s %-1s\n", "|", successCount++, "|", result.getB(), "|",
                        result.getB(), "|");
            } else {
                failed.format("%-1s %-5s %-1s %-34s %-1s %-40s %-1s\n", "|", failedCount++, "|", result.getB(), "|",
                        result.getB(), "|");
            }
        }

        success.format(rowSeperate);
        success.format("%-1s %-81s %-1s\n", "|", "Success Task Count:" + successCount, "|");
        success.format(rowSeperate);

        failed.format(rowSeperate);
        failed.format("%-1s %-81s %-1s\n", "|", "Failed Task Count:" + failedCount, "|");
        failed.format(rowSeperate);
        return new StringBuilder(success.toString()).append(failed.toString()).toString();
    }



    private static Future<GenericTaskResult> submitTask(GenericTask pGenericTask) {
        FutureTask<GenericTaskResult> futureTask = new FutureTask<GenericTaskResult>(pGenericTask);
        normalTaskThreadPool.submit(futureTask);
        return futureTask;
    }



    private static void shutDownExec(ExecutorService exec) throws Exception {
        if (exec != null) {
            exec.shutdown();
            try {
                // block until task queue shutdown finished
                exec.awaitTermination(30, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                throw new Exception(e);
            }
            if (exec.isShutdown()) {
                System.out.println("AsyncTaskExecutor shutdown success....");
                // reset to null
                exec = null;
            }
        }
    }

    public static class GenericTask implements Callable {
        private int a;



        public GenericTask(int pA) {
            a = pA;
        }



        public int getA() {
            return a;
        }



        public void setA(int pA) {
            a = pA;
        }



        @Override
        public GenericTaskResult call() throws Exception {
            System.out.println("task " + this.getA() + " Running...");
            Thread.sleep(3000);
            return new GenericTaskResult(this.getA());
        }

    }

    public static class GenericTaskResult {
        private int     b;
        private boolean isEmptyTask;



        public GenericTaskResult(int pB) {
            b = pB;
        }



        public int getB() {
            return b;
        }



        public void setB(int pB) {
            b = pB;
        }



        public boolean isEmptyTask() {
            return isEmptyTask;
        }



        public void setEmptyTask(boolean pEmptyTask) {
            isEmptyTask = pEmptyTask;
        }
    }

}