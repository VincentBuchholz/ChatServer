package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.*;

public class Server {
    private int port;
    private HashMap<String,User> users = new HashMap<>();
    private ArrayList<User> usersOnline = new ArrayList<>();
    private BlockingQueue<String> messageQueue = new ArrayBlockingQueue(10);
    private CopyOnWriteArrayList<ClientHandler> clientHandlerList = new CopyOnWriteArrayList<>();
    public Server(int port) {
        this.port = port;
    }

    public void startServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);

        //Create users
        User user1 = new User("Lars");
        User user2 = new User("Vincent");
        User user3 = new User("Viktor");
        User user4 = new User("Karl");
        users.put(user1.getName(),user1);
        users.put(user2.getName(),user2);
        users.put(user3.getName(),user3);
        users.put(user4.getName(),user4);

        ExecutorService es = Executors.newFixedThreadPool(10);



        while (true){
            Socket client = serverSocket.accept();
            ClientHandler cl = new ClientHandler(client,users,usersOnline,messageQueue);
            clientHandlerList.add(cl);
            Dispatcher disAll = new Dispatcher(messageQueue,clientHandlerList,usersOnline,true);
            Dispatcher disSpecific = new Dispatcher(messageQueue,clientHandlerList,usersOnline,false);
            es.execute(disAll);
            es.execute(disSpecific);
            es.execute(cl);
        }



    }
}
