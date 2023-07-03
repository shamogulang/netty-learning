package cn.oddworld.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class BioOneThreadCommonServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        while (true){
            System.out.println("waiting the request of client...");
            // it will block while no requesting
            Socket accept = serverSocket.accept();
            System.out.println("connect client success!");
            InputStream inputStream = accept.getInputStream();
            OutputStream outputStream = accept.getOutputStream();
            outputStream.write("connect client success!".getBytes());
            outputStream.flush();
            byte[] b = new byte[1024];
            int read = inputStream.read(b);
            while (read != -1){
                System.out.println("read = "+ read);
                System.out.println("receive the msgs = "+new java.lang.String(b));
                outputStream.write("i have received the msg, thinks".getBytes());
                outputStream.flush();
                read = inputStream.read(b);
            }
            System.out.println("read = "+ read);
            System.out.println("waiting for next request");
            inputStream.close();
            outputStream.close();
        }
    }
}
