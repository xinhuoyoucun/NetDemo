package com.laiyuan.io.socket.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * @author by yuanlai
 * @Date 2020/7/6 10:21 上午
 * @Description: TODO
 * @Version 1.0
 */
public class TcpServer {
    private final static Logger logger = LoggerFactory.getLogger(TcpServer.class);
    private final static int PORT = 9999;


    public static void main(String[] args) throws IOException {
//        监听端口
        ServerSocket serverSocket = new ServerSocket(PORT);

        TcpServer tcpServer = new TcpServer();
        ThreadPoolExecutor executorService = tcpServer.createThreadPool();

        while (true) {
            executorService.submit(() -> {
                try {
                    String threadName = Thread.currentThread().getName();

                    logger.info("{}等待客户端连接",threadName);
                    Socket socketAccept = serverSocket.accept();

                    logger.info("客户端连接{}成功",threadName);
                    InputStream inputStream = socketAccept.getInputStream();
                    OutputStream outputStream = socketAccept.getOutputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    PrintWriter printWriter = new PrintWriter(outputStream, true);
                    printWriter.println("服务器端连接成功....");

                    while (true) {
                        String str=bufferedReader.readLine();

                        logger.info("客户端传入:{}", str);

                        printWriter.println(threadName.concat("收到传入信息").concat(str));

                        if ("exit".equals(str)) {
                            inputStream.close();
                            outputStream.close();
                            bufferedReader.close();
                            socketAccept.close();
                            break;
                        }
                    }
                    logger.info("服务器端断开连接....");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }


//        executorService.shutdown();
    }


    private ThreadPoolExecutor createThreadPool() {
//        int poolSize = 2;
        int poolSize = Runtime.getRuntime().availableProcessors() * 2;
        logger.info("我是{}核处理器", poolSize / 2);
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(256);
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();
        RejectedExecutionHandler policy = new ThreadPoolExecutor.DiscardPolicy();
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(poolSize, poolSize,
                0, TimeUnit.SECONDS,
                queue,
                namedThreadFactory,
                policy);

        return executorService;
    }
}
