package com.io.agilefast.plugins.opcua.utils;

import lombok.Data;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @Author Bernie
 * @Date 2020/9/9/009 15:11
 * socket连接服务工具类
 */
@Data
public class SocketUtil {
    /**
     * 连接服务器的IP
     */
    private String ip = null;
    /**
     * 连接服务器的端口
     */
    private Integer port = null;
    /**
     * 套节字对象
     */
    private Socket socket = null;
    /**
     * 关闭连接标志位，true表示关闭，false表示连接
     */
    private boolean close = false;
    /**
     * 超时时间，以毫秒为单位
     */
    private Integer timeOut = 5 * 10;

    public SocketUtil(String ip, Integer port){
        this.ip = ip;
        this.port = port;
        init();
    }

    /**
     * 初始化socket对象
     */
    public void init(){
        try {
            InetAddress address = InetAddress.getByName(getIp());
            socket = new Socket(address,getPort());
            socket.setKeepAlive(true);//开启保持活动状态的套接字
            socket.setSoTimeout(timeOut);//设置超时时间
            close=!Send(socket,"2");//发送初始数据，发送成功则表示已经连接上，发送失败表示已经断开
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送数据，发送失败返回false,发送成功返回true
     * @param socket
     * @param message
     * @return
     */
    public Boolean Send(Socket socket,String message){
        try{
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(message);
            return true;
        }catch(Exception se){
            se.printStackTrace();
            return false;
        }
    }

    /**
     * 读取数据，返回字符串类型
     * @param socket
     * @return
     */
    public String ReadText(Socket socket){
        try{
            socket.setSoTimeout(timeOut);
            InputStream input = socket.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            char[] sn = new char[1000];
            in.read(sn);
            String sc = new String(sn);
            return sc;
        }catch(IOException se){
            return null;
        }
    }
    /**
     * 判断是否断开连接，断开返回true,没有返回false
     * @param socket
     * @return
     */
    public Boolean isServerClose(Socket socket){
        try{
            socket.sendUrgentData(0xFF);//发送1个字节的紧急数据，默认情况下，服务器端没有开启紧急数据处理，不影响正常通信
            return false;
        }catch(Exception se){
            return true;
        }
    }
}
