package com.laiyuan.io.socket.udp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

/**
 * udp client
 *
 * @author laiyuan
 * @date 2020-07-02
 *
 *
 * UDP（User Data Protocol，用户数据报协议）是一个简单的面向数据报的运输层协议。
 * 它不提供可靠性，只是把应用程序传给IP层的数据报发送出去，但是不能保证它们能到达目的地。
 * 由于UDP在传输数据报前不用再客户和服务器之间建立一个连接，且没有超时重发等机制，所以传输速度很快。
 *
 *
 * UDP数据报最大长度64K（包含UDP首部），如果数据长度超过64K就需要在应用层手动分包，UDP无法保证包序，需要在应用层进行编号。
 */
public class ScoketUDPClient {
    private static final Logger logger = LoggerFactory.getLogger(ScoketUDPClient.class);

    public static void main(String[] args) throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket();
        InetAddress inetAddress=InetAddress.getByName("127.0.0.1");
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(System.in));
        while (true){

            String txt=bufferedReader.readLine();
            DatagramPacket packet = new DatagramPacket(txt.getBytes(),txt.getBytes().length,inetAddress,8989);
            DatagramPacket outPacket = new DatagramPacket(new byte[1024],1024);
            datagramSocket.send(packet);
            System.out.println("请继续输入");
            if ("exit".equals(txt)){
                bufferedReader.close();
                datagramSocket.close();
                break;
            }

//            datagramSocket.receive(outPacket);
//            System.out.println(new String(outPacket.getData(),0,outPacket.getLength()));
        }



    }
}
