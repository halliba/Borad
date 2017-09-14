package de.itech.borad.core;

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
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();
    }


}
