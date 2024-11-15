package org;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class server {
    public static void main(){

        ServerSocket s =null;
        Socket s1;
        String sendString ="Hello World!";
        OutputStream s1out;
        DataOutputStream dos;
        try{
            s = new ServerSocket(8081);
        }catch(Exception e){
            e.printStackTrace();
        }
        while(true){
            try{
                s1=s.accept();//从服务端口等待用户请求,创建出虚电路的套接字
                s1out=s1.getOutputStream();//构建套接字的交流信息
                dos=new DataOutputStream(s1out);//构建输出流
                dos.writeUTF(sendString);//构建好后写入成功信息,类似于一个资源
                s1out.close();
                s1.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
