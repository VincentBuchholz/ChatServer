package server;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;

public class Dispatcher implements Runnable {
    private BlockingQueue<String> messageQueue;
    private CopyOnWriteArrayList<ClientHandler> clientHandlerList;

    public Dispatcher(BlockingQueue<String> messageQueue, CopyOnWriteArrayList<ClientHandler> clientHandlerList) {
        this.messageQueue = messageQueue;
        this.clientHandlerList = clientHandlerList;
    }

    @Override
    public void run() {
        try {
            msgAll();
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
}
