package AIO;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by posy on 17.12.8.
 */
public class Server {
    private ExecutorService executorService;
    private AsynchronousChannelGroup channelGroup;
    public AsynchronousServerSocketChannel socketChannel;

    public Server(int port) {
        try {
            executorService = Executors.newCachedThreadPool();
            channelGroup = AsynchronousChannelGroup.withCachedThreadPool(executorService, 1);
            socketChannel = AsynchronousServerSocketChannel.open(channelGroup);
            socketChannel.bind(new InetSocketAddress(port));
            System.out.println("server start port:" + port);
            socketChannel.accept(this, new ServerCompletionHandler());
            Thread.sleep(Integer.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
