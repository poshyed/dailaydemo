package IO;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by posy on 17.12.8.
 */
public class Server {
    private static int PORT = 8888;

    public Server() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("startup....");
//            Socket socket = serverSocket.accept();
//            new Thread(new SocketHandle(socket)).start();
//            ExecutorService es = Executors.newFixedThreadPool(3);
            ExecutorPool executorPool = new ExecutorPool(3, 10);
            while (true) {
                Socket socket = serverSocket.accept();
                executorPool.execute(new SocketHandle(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
    }
}
