package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;

public class Server {
    private int port;
    HashMap<String,User> users = new HashMap<>();

    public Server(int port) {
        this.port = port;
    }

    public void startServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket();

        //Create users
        User user1 = new User("Lars");
        User user2 = new User("Vincent");
        User user3 = new User("Viktor");
        User user4 = new User("Karl");
        users.put(user1.getName(),user1);
        users.put(user2.getName(),user2);
        users.put(user3.getName(),user3);
        users.put(user4.getName(),user4);



    }
}
