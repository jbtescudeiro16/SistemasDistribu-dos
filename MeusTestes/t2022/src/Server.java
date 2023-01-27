import java.io.IOException;
import java.net.ServerSocket;

public class Server {

    private Reuniao reuniao;
    ServerSocket s = new ServerSocket();

    public Server() throws IOException {
        this.reuniao= new ReuniaoFeito();
        this.s=new ServerSocket(80);
    }

    public void run () throws IOException {
        while (true){
            var clientSocket = this.s.accept();
            new Thread( new ServerWorker(this.reuniao,clientSocket)).start();
        }
    }
}
