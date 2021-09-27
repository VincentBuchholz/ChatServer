package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

public class ClientHandler implements Runnable{
    private Socket socket;
    private HashMap<String,User> users;
    private PrintWriter pw;
    private Scanner scanner;


    public ClientHandler(Socket socket, HashMap<String, User> users) throws IOException {
        this.socket = socket;
        this.users = users;
        this.pw = new PrintWriter(socket.getOutputStream(),true);
        this.scanner = new Scanner(socket.getInputStream());
    }

    @Override
    public void run() {

        try {
            this.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(true){

        }

    }

    public void connect() throws IOException {
        String username ="";
        pw.println("Username: ");
        username = scanner.nextLine();

        if (users.containsKey(username) && !users.get(username).isOnline()){
            pw.println("connected");
            users.get(username).setIsOnline(true);
        }
        else if (users.containsKey(username) && users.get(username).isOnline()) {
            pw.println("User already online");
            socket.close();
        }
        else{
            pw.println("User not found");
            socket.close();
        }
    }


}
