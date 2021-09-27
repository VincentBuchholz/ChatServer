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
    private User currentUser;


    public ClientHandler(Socket socket, HashMap<String, User> users) throws IOException {
        this.socket = socket;
        this.users = users;
        this.pw = new PrintWriter(socket.getOutputStream(),true);
        this.scanner = new Scanner(socket.getInputStream());
    }

    @Override
    public void run() {

        try {
            this.protocol();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void protocol() throws IOException {
        this.connect();
        while(true){
            String input = scanner.nextLine();



            String[] split = input.split("#");
            String action = split[0];
            String receiver ="";
            String msg = " ";
            if(split.length > 1){
                receiver = split[1];
                msg = split[2];
            }


            switch (action){
                case "SEND":
                    pw.println("receiver: " + receiver);
                    pw.println("Message: " + msg);
                    break;
                case "CLOSE":
                    this.disconnect();
                    break;
                default:
                    pw.println("Incorrect command");
                    break;
            }
        }
    }

    private void connect() throws IOException {
        String username ="";
        pw.println("Username: ");
        username = scanner.nextLine();

        if (users.containsKey(username) && !users.get(username).isOnline()){
            pw.println("connected");
            users.get(username).setIsOnline(true);
            currentUser = users.get(username);
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

    private void disconnect() throws IOException {
        pw.println("Closing..");
        currentUser.setIsOnline(false);
        socket.close();

    }


}
