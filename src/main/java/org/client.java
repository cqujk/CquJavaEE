package org;
import java.net.*;
import java.io.*;
public class client {
    public static void main()throws Exception
    {
        System.out.println("Client");
        Socket s1;
        InputStream s1In;
        DataInputStream dis;
        s1=new Socket("localhost",8081);//这里指定的是期望获取请求的服务器的IP地址和端口号
        //客户端的Ip地址和端口号如何确定?转发时自动装配的吗?
        s1In=s1.getInputStream();//从socket当中获取服务器传来的输入流
        dis = new DataInputStream(s1In);//构建输入流
        System.out.println(dis.readUTF());//读取服务器传来的信息
        s1.close();
        dis.close();
        s1In.close();
    }
}
