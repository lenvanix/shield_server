package org.heimdall.shield_server.config;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class EmptyLoop {

    private volatile boolean exitFlag;

    private ReentrantLock lock;

    private Condition condition;

    public EmptyLoop(){
        exitFlag = false;
        lock = new ReentrantLock();
        condition = lock.newCondition();
    }

    public void startLoop(){
        lock.lock();
        try {
            while (!exitFlag) {
                condition.await();
            }
        }catch (InterruptedException exception){
            exception.printStackTrace();
        }
        lock.unlock();
    }

    public void stopLoop(){
        lock.lock();
        try {
            exitFlag = true;
            condition.signalAll();
        }catch (IllegalMonitorStateException exception){
            exception.printStackTrace();
        }
        lock.unlock();
    }
}
