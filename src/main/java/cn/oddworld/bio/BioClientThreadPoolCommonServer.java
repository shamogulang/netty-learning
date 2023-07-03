package cn.oddworld.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BioClientThreadPoolCommonServer {

    static ExecutorService executorService = Executors.newFixedThreadPool(3);
    public static void main(String[] args) throws IOException {
        final ServerSocket serverSocket = new ServerSocket(8080);
        while (true){
            System.out.println("waiting the request of client...");
            // it will block while no requesting
            Socket accept = serverSocket.accept();
            OutputStream outputStream = accept.getOutputStream();
            outputStream.write("connect client success!".getBytes());
            outputStream.flush();
            System.out.println("connect client success!");
            executorService.submit(() -> {
                try {
                    handler(accept, outputStream);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public static void handler( Socket accept, OutputStream outputStream) throws Exception{
        InputStream inputStream = accept.getInputStream();

        byte[] b = new byte[1024];
        int read = inputStream.read(b);
        while (read != -1){
            System.out.println("read = "+ read);
            System.out.println("receive the msgs = "+new String(b));
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
