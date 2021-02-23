package org.heimdall.shield_server;

import org.heimdall.shield_server.network.NetworkBuilder;

public class Application {

    public static void main(String[] args) throws Exception {
        NetworkBuilder.getInstance().start();
    }
}
