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

public class OnThreadServer {
    private static ByteBuffer readBuffer = ByteBuffer.allocate(1024);
    private static  ByteBuffer sendBuffer = ByteBuffer.allocate(1024);
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
                if(!keyEvent.isValid()){
                    continue;
                }
                if(keyEvent.isAcceptable()){
                  ServerSocketChannel serverSocketChannel =  (ServerSocketChannel)keyEvent.channel();
                  SocketChannel client = serverSocketChannel.accept();
                  client.configureBlocking(false);
                  client.register(selector, SelectionKey.OP_READ);
                  System.out.println("new client connect:"+client.getRemoteAddress());
                }else if(keyEvent.isReadable()){

                    SocketChannel socketChannel1 = (SocketChannel)keyEvent.channel();
                    readBuffer.clear();
                    int read = socketChannel1.read(readBuffer);
                    System.out.println(new String(readBuffer.array(), 0 , read));

                }else if(keyEvent.isWritable()){

                }
                // 已经处理的事件要移除
                iterator.remove();
            }
        }

    }
}
