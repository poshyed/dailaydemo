package NIO;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by posy on 17.12.8.
 */
public class Channel {
    public void fileRead(String fileName) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(fileName, "rw");
        FileChannel inChannel = randomAccessFile.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(48);
        int byteRead = inChannel.read(byteBuffer);
        while (byteRead != -1) {
            System.out.println("Read" + byteRead);
            byteBuffer.flip();
            while (byteBuffer.remaining() != 0) {
                System.out.println(byteBuffer.get());
            }
            byteBuffer.clear();
            byteRead = inChannel.read(byteBuffer);
        }
    }
}
