package AIO;

import java.io.BufferedReader;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;

/**
 * Created by posy on 17.12.11.
 */
public class ServerCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, Server> {
    @Override
    public void completed(AsynchronousSocketChannel result, Server attachment) {
        attachment.socketChannel.accept(attachment, this);
        read(result);
    }

    @Override
    public void failed(Throwable exc, Server attachment) {

    }

    private void read(AsynchronousSocketChannel channel) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        channel.read(byteBuffer, byteBuffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                attachment.flip();
                System.out.println("server->" + "receive message size=" + result);
                String data = new String(byteBuffer.array()).trim();
                System.out.println("server->" + "data=" + data);
                String response = "服务器端响应了客户端。。。。。。";
                write(channel, response);
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                exc.printStackTrace();
            }
        });
    }

    private void write(AsynchronousSocketChannel channel, String response) {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put(response.getBytes());
            buffer.flip();
            channel.write(buffer).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

}
