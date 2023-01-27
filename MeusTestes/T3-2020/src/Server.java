import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    ServerSocket s ;

    SalaDeEspera sala;


    public Server() throws IOException {
        this.s = new ServerSocket(80);
        this.sala = new SalaDeEsperaImpl();
    }










    public void run() throws IOException {
        while (true){
            Socket aux = s.accept();
            new Thread(new ServerWorker(this.sala,aux)).start();


        }
    }
}
