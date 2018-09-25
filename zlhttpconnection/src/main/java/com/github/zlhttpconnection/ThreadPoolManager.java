package com.github.zlhttpconnection;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolManager {
    private static ThreadPoolManager instance = new ThreadPoolManager();
    private ThreadPoolExecutor threadPoolExecutor;
    private LinkedBlockingQueue<Future<?>> taskQueue = new LinkedBlockingQueue<>();

    public static ThreadPoolManager getInstance() {
        return instance;
    }
    private ThreadPoolManager(){
        threadPoolExecutor = new ThreadPoolExecutor(4, 10,
                30, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(4),
                rejectedExecutionHandler);
        threadPoolExecutor.execute(runnable);
    }

    public void excute(FutureTask futureTask) {
        try {
            taskQueue.put(futureTask);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (true) {
                FutureTask futureTask = null;
                try {
                    futureTask = (FutureTask) taskQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (futureTask!=null) {
                    threadPoolExecutor.execute(futureTask);
                }
            }
        }
    };

    private RejectedExecutionHandler rejectedExecutionHandler = new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
            try {
                taskQueue.put(new FutureTask(runnable, null) {
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
}
