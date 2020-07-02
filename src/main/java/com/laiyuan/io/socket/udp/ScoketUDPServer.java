package com.laiyuan.io.socket.udp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;


/**
 * udp server
 *
 * @author laiyuan
 * @date 2020-07-02
 *
 *
 * UDP（User Data Protocol，用户数据报协议）是一个简单的面向数据报的运输层协议。
 * 它不提供可靠性，只是把应用程序传给IP层的数据报发送出去，但是不能保证它们能到达目的地。
 * 由于UDP在传输数据报前不用再客户和服务器之间建立一个连接，且没有超时重发等机制，所以传输速度很快。
 *
 */
public class ScoketUDPServer {
    private static final Logger logger = LoggerFactory.getLogger(ScoketUDPServer.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        DatagramSocket datagramSocket = new DatagramSocket(8989);
        byte[] inBuff = new byte[1024];
        DatagramPacket packet = new DatagramPacket(inBuff, inBuff.length);

        while (true){
            packet.setData(inBuff);
            datagramSocket.receive(packet);
            String str = new String(inBuff, 0 , packet.getLength());
            System.out.println(str);

            packet.setData("返回信息:".concat(str).getBytes());
            datagramSocket.send(packet);
            if ("exit".equals(str)){
                datagramSocket.close();

                break;
            }
        }



    }
}
