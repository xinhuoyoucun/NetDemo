package com.laiyuan.io;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.*;

/**
 * @author laiyuan
 * @date 2020-07-01
 */

public class Socket1 {
    private static final Logger logger = LoggerFactory.getLogger(Socket1.class);

    public static void main(String[] args) throws IOException {

        int poolSize = Runtime.getRuntime().availableProcessors() * 2;
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(256);
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();
        RejectedExecutionHandler policy = new ThreadPoolExecutor.DiscardPolicy();
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(poolSize, poolSize,
                0, TimeUnit.SECONDS,
                queue,
                namedThreadFactory,
                policy);

        final PipedOutputStream outputStream = new PipedOutputStream();
        final PipedInputStream inputStream = new PipedInputStream(outputStream);

        executorService.submit(() -> {
            try {
                logger.info("开始");
                outputStream.write("hello".getBytes());
            } catch (IOException e) {

            }finally {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        executorService.submit(() -> {
            try {
                int data = inputStream.read();
                while (data != -1) {
                    System.out.println((char) data);
                    data = inputStream.read();
                }
            } catch (IOException e) {

            }finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });


        executorService.shutdown();
    }

}
