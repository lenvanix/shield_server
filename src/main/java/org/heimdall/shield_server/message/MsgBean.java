package org.heimdall.shield_server.message;

import java.util.Arrays;

public class MsgBean {

    private Integer length;

    private Short magic;

    private byte version;

    private byte updown;

    private String uuid;

    private String body;

    public MsgBean(byte[] bytes){
        //需要留意客户端与服务端，在将消息转为字节流时，字符编码问题。
        //协议行首的计算length时用的字符编码，务必要和写到socket时的字符编码一致，不然会出现接收不完整问题。
        length = ((bytes[0] & 0xFF) << 24) | ((bytes[1] & 0xFF) << 16) | ((bytes[2] & 0xFF) << 8) | (bytes[3] & 0xFF);
        magic =  (short)(((bytes[4] & 0xFF) << 8) | (bytes[5] & 0xFF));
        version = (byte) (bytes[6] & 0xFF);
        updown = (byte)(bytes[7] & 0xFF);
        uuid = new String(Arrays.copyOfRange(bytes, 8, 40));
        body = new String(Arrays.copyOfRange(bytes, 40, bytes.length));
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Short getMagic() {
        return magic;
    }

    public void setMagic(Short magic) {
        this.magic = magic;
    }

    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

    public byte getUpdown() {
        return updown;
    }

    public void setUpdown(byte updown) {
        this.updown = updown;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
