package org.heimdall.shield_server;

import sun.misc.Signal;
import sun.misc.SignalHandler;

public class Application {

    private static class ApplicationHandler implements SignalHandler {
        public void handle(Signal signal) {
            System.out.println("收到终止信号。");
        }
    }

    public static void main(String[] args) throws Exception {
        new ShieldServer().start();
        Signal.handle(new Signal("TERM"), new ApplicationHandler());
        Signal.handle(new Signal("INT"), new ApplicationHandler());
    }
}
