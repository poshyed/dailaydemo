package NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by posy on 17.12.8.
 */
public class Client {
    public Client() {
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 8888);
        SocketChannel sc = null;
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        try {
            sc = SocketChannel.open();
            sc.connect(address);
            while (true) {
                byte[] bytes = new byte[1024];
                System.in.read(bytes);
                byteBuffer.put(bytes);
                byteBuffer.flip();
                sc.write(byteBuffer);
                byteBuffer.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sc != null) {
                try {
                    sc.close();
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
