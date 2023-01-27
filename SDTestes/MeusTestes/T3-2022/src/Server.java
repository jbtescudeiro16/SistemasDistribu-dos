import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    ControloEntrada c;
    ServerSocket s;


    public Server() throws IOException {
        this.s=new ServerSocket(8000);
        this.c=new ControloEntradaImpl();
    }

    public void run() throws IOException {
        while (true){
            Socket aux = s.accept();
            new Thread(new ServerWorker(aux,c)).start();
        }



    }

}
