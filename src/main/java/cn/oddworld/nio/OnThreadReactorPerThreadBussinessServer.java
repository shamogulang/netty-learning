package cn.oddworld.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class OnThreadReactorPerThreadBussinessServer {

    public static void main(String[] args) throws IOException {

        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        // 设置为非阻塞状态
        socketChannel.configureBlocking(false);
        socketChannel.bind(new InetSocketAddress("127.0.0.1", 8080));
        // 选择器
        Selector selector = Selector.open();
        // 注册接受连接事件，等待连接
        socketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (!Thread.currentThread().isInterrupted()){
            // 选择事件
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey keyEvent = iterator.next();
                iterator.remove();
                if(!keyEvent.isValid()){
                    continue;
                }
                if(keyEvent.isAcceptable()){
                  ServerSocketChannel serverSocketChannel =  (ServerSocketChannel)keyEvent.channel();
                  SocketChannel client = serverSocketChannel.accept();
                  client.configureBlocking(false);
                  client.register(selector, SelectionKey.OP_READ);
                  System.out.println("new client connect:"+client.getRemoteAddress());
                } else {
                    // 每个业务事件请求一个线程
                    new Thread(()->{
                        try {
                            handle(selector, keyEvent);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }).start();
                }
            }
        }
    }

    public static void handle(Selector selector, SelectionKey keyEvent) throws IOException {
        if(keyEvent.isReadable()){
            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
            SocketChannel socketChannel1 = (SocketChannel)keyEvent.channel();
            readBuffer.clear();
            int read = socketChannel1.read(readBuffer);
            System.out.println(new String(readBuffer.array(), 0 , read));
            socketChannel1.register(selector, SelectionKey.OP_WRITE);
        }else if(keyEvent.isWritable()){
            ByteBuffer sendBuffer = ByteBuffer.allocate(1024);
            SocketChannel socketChannel1 = (SocketChannel)keyEvent.channel();
            sendBuffer.clear();
            sendBuffer.put("i received the msgs...".getBytes());
            sendBuffer.flip();
            socketChannel1.write(sendBuffer);
            //socketChannel1.register(selector, SelectionKey.OP_READ);
        }
    }
}
