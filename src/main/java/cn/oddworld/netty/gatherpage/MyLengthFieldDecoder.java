package cn.oddworld.netty.gatherpage;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.CharsetUtil;

public class MyLengthFieldDecoder extends LengthFieldBasedFrameDecoder {
    private static final int MAX_FRAME_LENGTH = 1024;  // 最大帧长度
    private static final int LENGTH_FIELD_OFFSET = 0;  // 长度字段偏移量
    private static final int LENGTH_FIELD_LENGTH = 4;  // 长度字段长度
    private static final int LENGTH_ADJUSTMENT = 0;    // 长度调整值
    private static final int INITIAL_BYTES_TO_STRIP = 0;  // 剥离长度字段的字节数

    public MyLengthFieldDecoder() {
        super(MAX_FRAME_LENGTH, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH,
                LENGTH_ADJUSTMENT, INITIAL_BYTES_TO_STRIP);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        // 使用 LengthFieldBasedFrameDecoder 进行解码
        ByteBuf frame = (ByteBuf) super.decode(ctx, in);
        if (frame == null) {
            return null;  // 如果未读取到完整的帧，等待下一次数据到达再进行解码
        }

        // 解码成功后的处理
        try {
            // 假设消息是字符串类型
            String message = frame.toString(CharsetUtil.UTF_8);
            return message;
        } finally {
            frame.release();  // 释放资源
        }
    }
}
