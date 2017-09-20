package de.itech.borad.core;

import de.itech.borad.models.Message;

public class KeepAliveManager {

    private boolean isActive;

    private MessageController controller;

    public KeepAliveManager(MessageController controller){
        this.controller = controller;
        isActive = true;
        Thread t = new Thread(() -> {
            while(true){

                if(isActive){
                    controller.sendKeepAlive();
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    public void setActive(boolean isActive){
        this.isActive = isActive;
    }


}
