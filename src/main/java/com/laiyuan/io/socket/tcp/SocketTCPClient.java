package com.laiyuan.io.socket.tcp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

/**
 * c/s架构的通讯
 * @author laiyuan
 * @date 2020-07-01
 *
 * TCP（Transmission Control Protocol，传输控制协议）提供的是面向连接，可靠的字节流服务。
 * 即客户和服务器交换数据前，必须现在双方之间建立一个TCP连接，之后才能传输数据。
 * 并且提供超时重发，丢弃重复数据，检验数据，流量控制等功能，保证数据能从一端传到另一端。
 *
 *
 * tcp的建立连接的三次握手
 * a:你好我想和你建立联系
 * b:收到，可以建立联系
 * a:收到，我要开始发数据了
 * 双方建立连接
 *
 * tcp断开连接的四次挥手过程
 * a：你好我要断开连接了
 * b：收到，我准备下，可以断开连接的时候我后告诉你
 * CLOSE_WAIT
 * b: 准备好了，可以关闭连接了
 * a：收到
 * 双方断开连接
 *
 */
public class SocketTCPClient {
    private final static Logger logger= LoggerFactory.getLogger(SocketTCPClient.class);
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 8123);

        logger.info("建立连接");

        //获得从键盘的输入流
        BufferedReader stdin=new BufferedReader(new InputStreamReader(System.in));

        //获得服务器内容的数据流
        PrintWriter out=new PrintWriter(socket.getOutputStream(),true);

        //获得服务器端发送内容的缓冲流
        BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));

        System.out.println("服务器信息:"+in.readLine());
        System.out.print("请输入");
        while(true){
            //获得从键盘输入的每行字符
            String line=stdin.readLine();
            //发送到服务器端
            out.println(line);
            if("exit".equalsIgnoreCase(line)){
                break;
            }
            System.out.println("服务器信息:"+in.readLine());
            System.out.println("请输入>");
        }
        in.close();
        out.close();
        stdin.close();
        socket.close();
    }
}
