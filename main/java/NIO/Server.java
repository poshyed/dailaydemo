package NIO;

import IO.ExecutorPool;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Created by posy on 17.12.8.
 */
public class Server implements Runnable {
    private Selector selector;
    private ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

    public Server(int port) {
        try {
            selector = Selector.open();
            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.configureBlocking(false);
            ssc.bind(new InetSocketAddress(port));
            ssc.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("server start,port:" + port);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        try {
            selector.select();
            Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
            while (keys.hasNext()) {
                SelectionKey key = keys.next();
                keys.remove();
                if (key.isValid()) {
                    if (key.isAcceptable()) {
                        accept(key);
                    }
                    if (key.isReadable()) {
                        read(key);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void accept(SelectionKey key) {
        try {
            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
            SocketChannel sc = ssc.accept();
            sc.configureBlocking(false);
            sc.register(selector, SelectionKey.OP_READ);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void read(SelectionKey key) {
        try {
            byteBuffer.clear();
            SocketChannel sc = (SocketChannel) key.channel();
            int count = sc.read(byteBuffer);
            if (count == -1) {
                key.channel().close();
                key.cancel();
                return;
            }
            byteBuffer.flip();
            byte[] bytes = new byte[byteBuffer.remaining()];
            byteBuffer.get(bytes);
            String body = new String(bytes).trim();
            System.out.println("server: " + body);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server(8888);
    }
}
