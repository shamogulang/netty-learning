package cn.oddworld.netty.new1;

public class CustomMsg {

    // 类型 系统编号 0xAB 表示A系统，0xBC 表示B系统
    private byte type;

    // 信息标志 0xAB 表示心跳包 0xBC 表示超时包 0xCD 业务信息包
    private byte flag;

    // 主题信息的长度
    private int length;

    // 主题信息
    private String body;

    public CustomMsg() {

    }

    public CustomMsg(byte type, byte flag, int length, String body) {
        this.type = type;
        this.flag = flag;
        this.length = length;
        this.body = body;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public byte getFlag() {
        return flag;
    }

    public void setFlag(byte flag) {
        this.flag = flag;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


    public byte[] getBytes() {
        byte[] bodyBytes = this.body.getBytes();
        int bodyLength = bodyBytes.length;
        byte[] bytes = new byte[bodyLength + 6];
        bytes[0] = this.type;
        bytes[1] = this.flag;
        bytes[2] = (byte)((bodyLength >> 24) & 0xFF);
        bytes[3] = (byte)((bodyLength >> 16) & 0xFF);
        bytes[4] = (byte)((bodyLength >> 8) & 0xFF); 
        bytes[5] = (byte)(bodyLength & 0xFF);
        for (int i = 0; i < bodyBytes.length; i++) {
            bytes[i + 6] = bodyBytes[i];
        }
        return bytes;
    }
}