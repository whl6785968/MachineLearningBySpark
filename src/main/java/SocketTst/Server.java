package SocketTst;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8888);
        System.out.println("启动服务器");
        Socket s = serverSocket.accept();
        System.out.println("客户端:"+s.getInetAddress().getHostAddress()+"已经连接到服务器");

        BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));

        String line = reader.readLine();
        System.out.println("客户端:"+line);

        PrintWriter pw = new PrintWriter(s.getOutputStream());
        pw.println("小老弟有点东西");
        pw.flush();
    }
}
