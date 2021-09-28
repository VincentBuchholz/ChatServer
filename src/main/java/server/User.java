package server;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class User {
    private String name;
    private boolean isOnline;
    private BlockingQueue<String> messageQueue;


    public User(String name){
        this.name = name;
        isOnline = false;
        this.messageQueue = new ArrayBlockingQueue<>(10);
    }

    public void setIsOnline(boolean isOnline){
        this.isOnline = isOnline;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public String getName() {
        return name;
    }

    public BlockingQueue<String> getMessageQueue() {
        return messageQueue;
    }
}
