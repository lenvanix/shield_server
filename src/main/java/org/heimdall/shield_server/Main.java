package org.heimdall.shield_server;

import org.heimdall.shield_server.network.NettyTransport;

public class Main {

    public static void main(String[] args) throws Exception {
        new NettyTransport().start();
    }
}
