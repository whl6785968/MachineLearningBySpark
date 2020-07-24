package SocketTst;


import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception {
        Socket s = new Socket("127.0.0.1",8888);

        Scanner sc = new Scanner(System.in);
        System.out.print("请输入内容: ");
        String msg = sc.nextLine();
        sc.close();

        PrintWriter pw = new PrintWriter(s.getOutputStream());
        pw.println(msg);
        pw.flush();

        BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String line = reader.readLine();
        System.out.println("服务器消息"+line);
        s.close();

    }
}
