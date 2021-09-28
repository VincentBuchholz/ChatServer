package server;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;

public class Dispatcher implements Runnable {
    private BlockingQueue<String> messageQueue;
    private CopyOnWriteArrayList<ClientHandler> clientHandlerList;
    private ArrayList<User> usersOnline;
    private User user;

    public Dispatcher(BlockingQueue<String> messageQueue, CopyOnWriteArrayList<ClientHandler> clientHandlerList, ArrayList<User> usersOnline) {
        this.messageQueue = messageQueue;
        this.clientHandlerList = clientHandlerList;
        this.usersOnline = usersOnline;
    }

    @Override
    public void run() {
        try {
            msgAll();
            System.out.println("Trying to send specific user message...");
            msgSpecificUsers();
            System.out.println("Done");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void msgAll() throws InterruptedException {
        String msg = messageQueue.take();
        for (ClientHandler client:clientHandlerList) {
            client.getPw().println(msg);
        }
    }

    private void msgSpecificUsers() throws InterruptedException {
        String msg = "";
        for (ClientHandler client:clientHandlerList) {
            user = client.getCurrentUser();
            msg = client.getCurrentUser().getMessageQueue().take();
            if (user.equals(client.getCurrentUser())){
                client.getPw().println(msg);
            }
            System.out.println(user.getName());
        }
    }
}
