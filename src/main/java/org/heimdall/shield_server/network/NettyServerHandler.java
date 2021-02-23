package org.heimdall.shield_server.network;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.net.SocketAddress;


public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketAddress socketAddress = ctx.channel().remoteAddress();
        System.out.println("客户端[IP:" + socketAddress.toString() + "]连上了服务端");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("客户端-->服务端讲了:" + msg);
        ctx.channel().pipeline().writeAndFlush("服务端-->客户端讲了:当前时间" + System.currentTimeMillis());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    }

}
