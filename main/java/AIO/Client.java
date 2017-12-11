package AIO;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;

/**
 * Created by posy on 17.12.11.
 */
public class Client implements Runnable {
    private AsynchronousSocketChannel channel;

    public Client() throws IOException {
        this.channel = AsynchronousSocketChannel.open();
    }

    public void connect() {
        channel.connect(new InetSocketAddress("127.0.0.1", 8888));
    }

    public void write(String data) {
        try {
            channel.write(ByteBuffer.wrap(data.getBytes())).get();
            read();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void read() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        try {
            channel.read(buffer).get();
            buffer.flip();
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
            String data = new String(bytes, "UTF-8").trim();
            System.out.println(data);
        } catch (InterruptedException | ExecutionException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void run() {
        while (true) {

        }
    }
}
