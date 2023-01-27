package g8;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TaggedConnection implements AutoCloseable {
    public static class Frame {
        public final int tag;
        public final byte[] data;


        public Frame(int tag, byte[] data) { this.tag = tag; this.data = data; }
    }
    DataOutputStream out;
    Lock sl= new ReentrantLock();
    Lock rl = new ReentrantLock();
    DataInputStream in;
    public TaggedConnection(Socket socket) throws IOException {

    }
    public void send(Frame frame) throws IOException {
        send(frame.tag,frame.data);
    }
    public void send(int tag, byte[] data) throws IOException {

        sl.lock();
        try{
            out.writeInt(data.length);
            out.writeInt(tag);
            out.write(data);
            out.flush();
            }finally {
            sl.unlock();
        }

    }
    public Frame receive() throws IOException {
        rl.lock();
        try{
            int size = in.readInt();
            int tag = in.readInt();
            byte[] data=new byte[size-4];
            in.readFully(data);
            return new Frame(tag,data);
        }finally {
            rl.unlock();
        }

    }
    public void close() throws IOException {
    }

}