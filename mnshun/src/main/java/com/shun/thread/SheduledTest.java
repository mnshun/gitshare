package com.shun.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by shun on 2021/4/2
 */
public class SheduledTest {


    public static void main(String[] args) throws InterruptedException {
//        testScheduled2Job();
//        testScheduled5Job();
        testScheduledCancelJob();


    }


    private static void testScheduledCancelJob() throws InterruptedException {
        ScheduledThreadPoolExecutor scheduledExecutor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(1);
        scheduledExecutor.setRemoveOnCancelPolicy(true);
        final long startTime = System.currentTimeMillis();
        System.out.println(startTime);

        for (int i = 1; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                final long startTime1 = System.currentTimeMillis();
                ScheduledFuture<Object> objectScheduledFuture = scheduledWork(startTime1, scheduledExecutor, 100 * (finalI + 1), "m" + finalI);
                try {
                    Thread.sleep(50 * finalI);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(System.currentTimeMillis() + "====cancel");
                if (finalI == 5) {
                    objectScheduledFuture.cancel(true);
                }
            }).start();
        }


    }


    private static void testScheduled5Job() throws InterruptedException {
        ScheduledThreadPoolExecutor scheduledExecutor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(1);
        final long startTime = System.currentTimeMillis();
        System.out.println(startTime);

        for (int i = 1; i < 10; i++) {
            final long startTime1 = System.currentTimeMillis();
            /**
             * 加入任务队列
             * 线程从任务队列取任务
             * 线程用完后阻塞，直到线程释放后在去执行任务
             */
            scheduledWork(startTime1, scheduledExecutor, 100 * (i + 1), "m" + i);
        }

    }

    private static void testScheduled2Job() throws InterruptedException {
        ScheduledThreadPoolExecutor scheduledExecutor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(2);

        final long startTime = System.currentTimeMillis();
        System.out.println(startTime);
        long startDelayMilli = 1000;
        ScheduledFuture<Object> startScheduledFutures = scheduledWork(startTime, scheduledExecutor, startDelayMilli, "m0");

        Thread.sleep(100);

        ScheduledFuture<Object> startScheduledFutures1 = scheduledWork(startTime, scheduledExecutor, startDelayMilli, "m1");
    }

    private static ScheduledFuture<Object> scheduledWork(long startTime, ScheduledThreadPoolExecutor scheduledExecutor, long startDelayMilli, String method) {
        return scheduledExecutor.schedule(() -> {
            final long endTime = System.currentTimeMillis();
            System.out.println((endTime - startTime) + ":" + method);
            Thread.sleep(1000L);
            return null;
        }, startDelayMilli, TimeUnit.MILLISECONDS);
    }
}
