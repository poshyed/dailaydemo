package IO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by posy on 17.12.8.
 */
public class SocketHandle implements Runnable {
    private Socket socket;

    public SocketHandle(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true)) {
            while (true) {
                String info = bufferedReader.readLine();
                if (info == null) {
                    break;
                }
                System.out.println("client's information:" + info);
                printWriter.println("server response.....");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            socket = null;
        }
    }
}
