package org.heimdall.shield_server;

import org.heimdall.shield_server.config.ConfigManager;
import org.heimdall.shield_server.network.NetworkBuilder;

public class ShieldServer {

    public void start(){
        initConfig();
        initNetwork();
    }

    private void initConfig(){
        ConfigManager.getInstance().loadConfig();
    }

    private void initNetwork(){
        try {
            NetworkBuilder.getInstance().start();
        } catch (Exception exception){
            stop();
            System.exit(-1);
        }
    }

    public void stop(){
        unInitConfig();
        unInitNetwork();
    }

    public void unInitConfig(){
        ConfigManager.getInstance().unloadConfig();
    }

    public void unInitNetwork(){
        NetworkBuilder.getInstance().stop();
    }

}
