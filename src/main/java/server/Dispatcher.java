package server;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;

public class Dispatcher implements Runnable {
    private BlockingQueue<String> messageQueue;
    private CopyOnWriteArrayList<ClientHandler> clientHandlerList;
    private ArrayList<User> usersOnline;
    private User user;
    private boolean msgAll;

    public Dispatcher(BlockingQueue<String> messageQueue, CopyOnWriteArrayList<ClientHandler> clientHandlerList, ArrayList<User> usersOnline, boolean msgAll) {
        this.messageQueue = messageQueue;
        this.clientHandlerList = clientHandlerList;
        this.usersOnline = usersOnline;
        this.msgAll = msgAll;
    }

    @Override
    public void run() {
        while (true) {
            if (msgAll) {
                try {
                    this.msgAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (!msgAll) {
                try {
                    this.msgSpecificUsers();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
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
        for (ClientHandler client: clientHandlerList) {
            msg = client.getCurrentUser().getMessageQueue().take();
            client.getPw().println(msg);
            }
        }
    }

