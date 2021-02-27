package org.heimdall.shield_server.network;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.heimdall.shield_server.message.MsgBean;
//import org.heimdall.shield_server.utils.LogUtil;
import java.net.SocketAddress;


public class MessageToBeanHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketAddress socketAddress = ctx.channel().remoteAddress();
        //LogUtil.log("客户端[IP:" + socketAddress.toString() + "]连上了服务端");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
        byte[] bytes = new byte[((ByteBuf)msg).readableBytes()];
        ((ByteBuf)msg).readBytes(bytes);
        if(isValidMessage(bytes)){
            //channelHandlerContext.fireChannelRead(new MsgBean(bytes));
            MsgBean msgBean = new MsgBean(bytes);
        }
    }

    private boolean isValidMessage(byte[] bytes){
        if(bytes.length < 40){
            //协议格式：长度，4字节 | 魔数，2字节 | 版本，1字节 | 上下行，1字节 | 唯一标识符，32字节 | 消息体，0 ~ (3M - 40) 字节
            return false;
        }
        if(bytes[4] != 0x25 || bytes[5] != 0x37){
            //拼接起来，对应十进制魔数：9527
            return false;
        }
        return true;
    }
}
