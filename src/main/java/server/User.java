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
    private Socket socket;
    private PrintWriter pw;

    public User(String name){
        this.name = name;
        isOnline = false;
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
