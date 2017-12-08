package IO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by posy on 17.12.8.
 */
public class Client {

    public Client() {
        BufferedReader bufferedReader = null;
        PrintWriter printWriter = null;
        Socket socket = null;
        try {
            int PORT = 8888;
            String IP = "127.0.0.1";
            socket = new Socket(IP, PORT);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println("client request server");
            String response = bufferedReader.readLine();
            System.out.println("client:" + response);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (printWriter != null) {
                printWriter.close();
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
    }
}
