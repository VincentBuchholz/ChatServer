package server;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;

public class Dispatcher implements Runnable {
    private BlockingQueue<String> messageQueue;
    private CopyOnWriteArrayList<ClientHandler> clientHandlerList;
    private ArrayList<User> usersOnline;

    public Dispatcher(BlockingQueue<String> messageQueue, CopyOnWriteArrayList<ClientHandler> clientHandlerList, ArrayList<User> usersOnline) {
        this.messageQueue = messageQueue;
        this.clientHandlerList = clientHandlerList;
        this.usersOnline = usersOnline;
    }

    @Override
    public void run() {
        while (true) {
            try {
                this.msgAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void msgAll() throws InterruptedException {
        String msg = messageQueue.take();
        for (ClientHandler client : clientHandlerList) {
            client.getPw().println(msg);
        }
    }
}

