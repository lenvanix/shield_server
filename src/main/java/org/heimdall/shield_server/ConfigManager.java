package org.heimdall.shield_server;

import org.heimdall.shield_server.utils.PropertyUtil;

import java.util.Properties;

public class ConfigManager {

    private static volatile ConfigManager instance;

    private Properties properties;

    public static ConfigManager getInstance(){
        if(instance == null){
            synchronized (ConfigManager.class){
                if(instance == null){
                    instance = new ConfigManager();
                }
            }
        }
        return instance;
    }

    public synchronized void loadConfig(){
        properties = PropertyUtil.loadConfig("application.properties");
    }

    public synchronized void unloadConfig(){
        PropertyUtil.flushConfig(properties, "application.properties");
        properties.clear();
        properties = null;
    }

    public synchronized String getConfig(String key){
        return properties.getProperty(key, "");
    }

    public synchronized void setConfig(String key, String val){
        properties.setProperty(key, val);
        PropertyUtil.flushConfig(properties, "application.properties");
    }

}
