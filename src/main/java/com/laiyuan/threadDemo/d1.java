package com.laiyuan.threadDemo;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * @author laiyuan
 * @date 2020-07-01
 */
public class d1 {
    private final static Logger logger = LoggerFactory.getLogger(d1.class);
    public static void main(String[] args) {
        int poolSize = Runtime.getRuntime().availableProcessors() * 2;
        logger.info("我是{}核",poolSize/2);
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(256);
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();
        RejectedExecutionHandler policy = new ThreadPoolExecutor.DiscardPolicy();
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(poolSize, poolSize,
                0, TimeUnit.SECONDS,
                queue,
                namedThreadFactory,
                policy);
        for (int i = 0; i < 100; i++) {
            executorService.submit(() -> {
                System.out.println("thread id is: " + Thread.currentThread().getId());
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
