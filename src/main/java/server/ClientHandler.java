package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

public class ClientHandler implements Runnable {
    private Socket socket;
    private HashMap<String, User> users;
    private PrintWriter pw;
    private Scanner scanner;
    private User currentUser;
    private ArrayList<User> usersOnline;
    private BlockingQueue<String> messageQueue;


    public ClientHandler(Socket socket, HashMap<String, User> users, ArrayList<User> usersOnline, BlockingQueue<String> messageQueue) throws IOException {
        this.socket = socket;
        this.users = users;
        this.pw = new PrintWriter(socket.getOutputStream(), true);
        this.scanner = new Scanner(socket.getInputStream());
        this.usersOnline = usersOnline;
        this.messageQueue = messageQueue;
    }

    @Override
    public void run() {
        try {
            this.protocol();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void protocol() throws IOException, InterruptedException {
        this.connect();
        while (true) {
            String input = scanner.nextLine();
            String[] split = input.split("#");
            String action = split[0];
            String receiver = "";
            String[] receiverSplit = {};
            String msg = currentUser.getName() + ": ";

            if (split.length > 1) {
                receiver = split[1].toLowerCase();
                receiverSplit = receiver.split(",");
                msg += split[2];
            }

            switch (action) {
                case "USERSONLINE":
                    pw.println(this.online());
                    break;
                case "SEND":
                    if (receiver.equals("*")) {
                        messageQueue.put(msg);
                    } else if (receiverSplit.length == 1) {
                        users.get(receiver).getPw().println(msg);
                    } else if (receiverSplit.length >1) {
                        for (int i = 0; i < receiverSplit.length; i++) {
                            users.get(receiverSplit[i]).getPw().println(msg);
                        }
                    }
                    else{
                        pw.println("There was an error try again.");
                    }
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
        String username = "";
        pw.println("Username: ");
        username = scanner.nextLine().toLowerCase();

        if (users.containsKey(username) && !users.get(username).isOnline()) {
            pw.println("connected");
            users.get(username).setIsOnline(true);
            users.get(username).setSocket(socket);
            currentUser = users.get(username);
            usersOnline.add(currentUser);
        } else if (users.containsKey(username) && users.get(username).isOnline()) {
            pw.println("User already online");
            socket.close();
        } else {
            pw.println("User not found");
            socket.close();
        }
    }

    private void disconnect() throws IOException {
        pw.println("Closing..");
        currentUser.setIsOnline(false);
        socket.close();
        usersOnline.remove(currentUser);

    }

    private String online() {
        String onlineUsers = "";

        for (User user : usersOnline) {
            onlineUsers += user.getName() + "\r\n";
        }
        return onlineUsers;
    }

    public PrintWriter getPw() {
        return pw;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
