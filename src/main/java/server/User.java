package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class User {
    private String name;
    private boolean isOnline;
    private BlockingQueue<String> messageQueue;
    private Socket socket;
    private PrintWriter pw;



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
    public void setSocket(Socket socket){
        this.socket = socket;
        try {
            pw = new PrintWriter(socket.getOutputStream(),true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PrintWriter getPw() {
        return pw;
    }
}
