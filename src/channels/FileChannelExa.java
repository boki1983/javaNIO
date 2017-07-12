package channels;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelExa {
    public static void main(String[] args) throws FileNotFoundException, IOException {
//        nioReadWrite();

//        scatterRead();

//        transferFrom();

        transferTo();
    }

    private static void selector() throws IOException {

    }

    private static void transferTo() throws IOException {
        RandomAccessFile fromFile = new RandomAccessFile("fromFile.txt", "rw");
        FileChannel      fromChannel = fromFile.getChannel();

        RandomAccessFile toFile = new RandomAccessFile("toFile.txt", "rw");
        FileChannel      toChannel = toFile.getChannel();

        long position = 0;
        long count    = fromChannel.size();

        fromChannel.transferTo(position, count, toChannel);
    }

    private static void transferFrom() throws IOException {
        RandomAccessFile fromFile = new RandomAccessFile("fromFile.txt", "rw");
        FileChannel      fromChannel = fromFile.getChannel();

        RandomAccessFile toFile = new RandomAccessFile("toFile.txt", "rw");
        FileChannel      toChannel = toFile.getChannel();

        long position = 0;
        long count    = fromChannel.size();

        toChannel.transferFrom(fromChannel, position, count);
    }

    private static void scatterRead() throws IOException {
        RandomAccessFile aFile = new RandomAccessFile("nio-data.txt", "rw");
        FileChannel channel = aFile.getChannel();
        ByteBuffer header = ByteBuffer.allocate(4);
        ByteBuffer body   = ByteBuffer.allocate(64);

        ByteBuffer[] bufferArray = { header, body };

        channel.read(bufferArray);

        System.out.println("header ");
        header.flip();
        while(header.hasRemaining()){
            System.out.print((char) header.get());
        }

        System.out.println("body ");
        body.flip();
        while(body.hasRemaining()){
            System.out.print((char) body.get());
        }

        //channel.position(0);
        channel.write(bufferArray);
        System.out.println("eee");
    }

    private static void nioReadWrite() throws IOException {
        RandomAccessFile aFile = new RandomAccessFile("nio-data.txt", "rw");
        /*can both read and write to a Channels. Streams are typically one-way (read or write).
          Channels can be read and written asynchronously.
          Channels always read to, or write from, a Buffer.*/
        FileChannel inChannel = aFile.getChannel();

        ByteBuffer buf = ByteBuffer.allocate(48);

        int bytesRead = inChannel.read(buf);
        while (bytesRead != -1) {

            System.out.println("Read " + bytesRead);
            // flip is used to flip the ByteBuffer from "reading" (putting) to "writing" (getting)
            buf.flip();

            while(buf.hasRemaining()){
                System.out.print((char) buf.get());
            }

            buf.clear();
            bytesRead = inChannel.read(buf);
        }
        aFile.close();
    }


}
