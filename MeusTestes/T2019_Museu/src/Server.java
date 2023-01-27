import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {



    private ServerSocket s;

    private Museu m;


    public Server() throws IOException {
        this.s=new ServerSocket(3306);
        this.m=new MuseuImpl();
    }


    public void main() throws IOException {
        Socket socket = s.accept();
        new Thread(new ServerWorker(socket,m));

    }
}
