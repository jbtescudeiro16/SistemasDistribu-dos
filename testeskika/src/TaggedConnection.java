import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class TaggedConnection implements AutoCloseable {

    Socket s;
    DataInputStream in;
    DataOutputStream out;

    public static class Frame {
        public final int tag;
        public final byte[] data;

        public Frame(int tag, byte[] data) {
            this.tag = tag;
            this.data = data;
        }
    }

    public TaggedConnection(Socket socket) throws IOException {
        s=socket;
        in = new DataInputStream(s.getInputStream());
        out = new DataOutputStream(s.getOutputStream());
    }

    public void send(Frame frame) throws IOException {
        out.writeInt(frame.tag);
        out.writeInt(frame.data.length);
        out.write(frame.data);

    }

    public void send(int tag, byte[] data) throws IOException {
        out.writeInt(tag);
        out.writeInt(data.length);
        out.write(data);

    }

    public Frame receive() throws IOException {
        int t = in.readInt();
        int size = in.readInt();
        byte b[] = new byte[size];
        in.readFully(b);
        return new Frame(t,b);
    }

    public void close() throws IOException {
        s.shutdownOutput();
        s.shutdownInput();
        s.close();
    }
}
