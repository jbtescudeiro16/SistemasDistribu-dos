package g8;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FramedConnection implements AutoCloseable {
    Lock sl = new ReentrantLock();
    Lock rl = new ReentrantLock();
    DataOutputStream out;
    DataInputStream in;
    Socket s;
    public FramedConnection(Socket socket) throws IOException {
    this.s=socket;
    in=new DataInputStream(new BufferedInputStream(socket.getInputStream()));
    out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

        }
    public void send(byte[] data) throws IOException {
        sl.lock();
        try {
            out.writeInt((data.length));
            out.write(data);
            out.flush();
        } finally {
            sl.unlock();
        }

    }
    public byte[] receive() throws IOException {


        try {
            int size = in.readInt();
            byte []data= new byte[size];
            in.readFully(data);
            return data;
        }finally { rl.unlock();

                }
    }
    public void close() throws IOException {

    }
}