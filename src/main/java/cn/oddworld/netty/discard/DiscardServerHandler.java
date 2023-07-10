package cn.oddworld.netty.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class DiscardServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//         ByteBuf byteBuf =  ((ByteBuf)msg);
//         System.out.println("客户端发送的消息："+byteBuf.toString(CharsetUtil.UTF_8));
//         byteBuf.release();
            System.out.println("客户端发送的消息："+msg);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
         cause.printStackTrace();
         ctx.close();
    }
}
