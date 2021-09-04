package pkg;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

public class Main {

    private static final Resource1 resource1 = new Resource1();
    private static final Resource2 resource2 = new Resource2();

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = new ForkJoinPool();
        Future<?> submit1 = executorService.submit(Main::concurrentAccess1);
        Future<?> submit2 = executorService.submit(Main::concurrentAccess2);

        while (!submit1.isDone() && !submit2.isDone()) {
            System.out.println("Submit1 = " + submit1.isDone());
            System.out.println("Submit2 = " + submit2.isDone());
            Thread.sleep(1000);
        }
    }

    private static void concurrentAccess1() {
        synchronized(resource1) {
            resource1.method();
            synchronized (resource2) {
                resource2.method();
            }
        }
    }

    private static void concurrentAccess2() {
        synchronized(resource2) {
            resource2.method();
            synchronized (resource1) {
                resource1.method();
            }
        }
    }

    private static class Resource1 {

        public void method() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private static class Resource2 {

        public void method() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
