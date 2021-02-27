package org.heimdall.shield_server;

import org.heimdall.shield_server.config.EmptyLoop;
import sun.misc.Signal;
import sun.misc.SignalHandler;

public class Application {

    private static EmptyLoop emptyLoop = new EmptyLoop();

    private static class ApplicationHandler implements SignalHandler{
        public void handle(Signal signal) {
            System.out.println("收到终止信号。");
            if(emptyLoop != null){
                emptyLoop.stopLoop();
            }
        }
    }

    public static void main(String[] args) {
        ShieldServer shieldServer = new ShieldServer();
        shieldServer.start();
        ApplicationHandler applicationHandler = new ApplicationHandler();
        Signal.handle(new Signal("TERM"), applicationHandler);
        emptyLoop.startLoop();
        shieldServer.stop();
    }
}
