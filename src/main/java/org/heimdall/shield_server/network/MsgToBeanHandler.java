package org.heimdall.shield_server.network;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.heimdall.shield_server.message.MsgBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.heimdall.shield_server.utils.LogUtil;
import java.net.SocketAddress;

/**
 * 可以给ChannelHandler加上@Sharable注解
 * 此后所有因新连接而创建的Channel都共享这个Handler
 * 这意味着，该Handler会被任意Channel的事件触发。
 * 因此此时Channel应该是无状态的，否则会有线程安全问题
 */
@ChannelHandler.Sharable
public class MsgToBeanHandler extends SimpleChannelInboundHandler<Object> {

    private static final Logger logger = LoggerFactory.getLogger(MsgToBeanHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketAddress socketAddress = ctx.channel().remoteAddress();
        logger.info("客户端[IP:" + socketAddress.toString() + "]连上了服务端");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
        byte[] bytes = new byte[((ByteBuf)msg).readableBytes()];
        ((ByteBuf)msg).readBytes(bytes);
        if(isValidMessage(bytes)){
            //channelHandlerContext.fireChannelRead(new MsgBean(bytes));
            MsgBean msgBean = new MsgBean(bytes);
        }else{
            logger.error("消息格式不合法，直接丢弃消息。消息长度{},魔数{}", bytes.length, (short)(((bytes[4] & 0xFF) << 8) | (bytes[5] & 0xFF)));
        }
    }

    private boolean isValidMessage(byte[] bytes){
        if(bytes.length < 40){
            //协议格式：长度，4字节 | 魔数，2字节 | 业务码，2字节 | 唯一标识符，32字节 | 消息体，0 ~ (3M - 40) 字节
            return false;
        }
        if(bytes[4] != 0x25 || bytes[5] != 0x37){
            //拼接起来，对应十进制魔数：9527
            return false;
        }
        return true;
    }
}
