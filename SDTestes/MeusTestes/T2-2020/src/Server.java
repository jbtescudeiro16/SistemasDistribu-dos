import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    Ficheiros f;
    ServerSocket s;



    public Server() throws IOException {
        this.f=new FicheirosImpl();
        this.s = new ServerSocket(80);
    }



    public void run() throws IOException {
        while (true){
            Socket aux=s.accept();
            new Thread(new ServerWorker(f,aux)).start();

        }
    }
}
