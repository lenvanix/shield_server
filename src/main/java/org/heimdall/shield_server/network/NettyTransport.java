package org.heimdall.shield_server.network;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.heimdall.shield_server.config.ConfigManager;

import java.net.InetSocketAddress;

public class NettyTransport implements Transport {

    private EventLoopGroup bossGroup;

    private EventLoopGroup workGroup;

    private ChannelFuture channelFuture;

    public NettyTransport(){
        bossGroup = new NioEventLoopGroup();
        workGroup = new NioEventLoopGroup();
    }

    public void start() throws Exception {
        Integer port;
        try{
            port = Integer.valueOf(ConfigManager.getInstance().getConfig("server.port"));
        }catch (Exception exception){
            port = 9527;
        }
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(port))
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new NettyServerInitializer());
        channelFuture = bootstrap.bind().sync();
    }

    public void stop() {
        if(bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
        if(workGroup != null) {
            workGroup.shutdownGracefully();
        }
        if(channelFuture != null){
            try {
                channelFuture.channel().closeFuture().sync();
            }catch (Exception exception){
                //包住异常，不抛出
            }
        }
    }
}

