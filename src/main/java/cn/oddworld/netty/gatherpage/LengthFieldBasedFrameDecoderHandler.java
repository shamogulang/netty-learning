package cn.oddworld.netty.gatherpage;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.CharsetUtil;

import java.net.SocketAddress;
import java.nio.ByteBuffer;

public class LengthFieldBasedFrameDecoderHandler extends LengthFieldBasedFrameDecoder {

    public LengthFieldBasedFrameDecoderHandler() {
        /**
         *
         * @param maxFrameLength  帧的最大长度
         * @param lengthFieldOffset length字段偏移的地址
         * @param lengthFieldLength length字段所占的字节长（头部字段的偏移长度）
         * @param lengthAdjustment 修改帧数据长度字段中定义的值，可以为负数 因为
         *                         有时候我们习惯把头部记入长度,若为负数,则说明要推后多少个字段
         * @param initialBytesToStrip 解析时候跳过多少个长度
         * @param failFast 为true，当frame长度超过maxFrameLength时立即报TooLongFrameException异常，
         *                 为false，读取完整个帧再报异
         */
        super(16 * 1024 * 1024, 0, 4, 0, 4);
    }

    @Override
    public Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf frame = null;
        try {
            frame = (ByteBuf) super.decode(ctx, in);
            if (null == frame) {
                return null;
            }

            // 解码成功后的处理
            try {
                // 假设消息是字符串类型
                String message = frame.toString(CharsetUtil.UTF_8);
                return message;
            } finally {
                frame.release();  // 释放资源
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != frame) {
                frame.release();
            }
        }
        return null;
    }

}
