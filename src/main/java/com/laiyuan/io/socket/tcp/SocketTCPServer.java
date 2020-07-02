package com.laiyuan.io.socket.tcp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * c/s架构的通讯
 * @author laiyuan
 * @date 2020-07-01
 *
 *
 * TCP（Transmission Control Protocol，传输控制协议）提供的是面向连接，可靠的字节流服务。
 * 即客户和服务器交换数据前，必须现在双方之间建立一个TCP连接，之后才能传输数据。
 * 并且提供超时重发，丢弃重复数据，检验数据，流量控制等功能，保证数据能从一端传到另一端。
 */
public class SocketTCPServer {

    private final static Logger logger = LoggerFactory.getLogger(SocketTCPServer.class);

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(8123);


        //挂起 等待客户的请求
        Socket socketAccept = serverSocket.accept();
        //读取客户端的数据流
        BufferedReader in = new BufferedReader(new InputStreamReader(socketAccept.getInputStream()));

        //获取写往客户端的输出流,true表示自动刷新
        PrintWriter out = new PrintWriter(socketAccept.getOutputStream(), true);
        out.println("服务器端连接成功....");
        while (true) {
            //读取客户端的每一行文本
            String line = in.readLine();
            //显示客户端发送的内容
            System.out.println("客户端传来的内容:" + line);
            //转换信息
            out.println("从服务器端口发送的内容:" + line);
            //退出判断
            if ("exit".equals(line.trim())) {
                break;
            }
        }
        in.close();
        out.close();
        socketAccept.close();
    }
}
