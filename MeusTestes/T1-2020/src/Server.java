import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {


    private ControloTrafegoAereo c;
    private ServerSocket s ;

    public Server() throws IOException {
        this.c = new ControloImpl();
        this.s= new ServerSocket(8000);
    }

    public void main() throws IOException {
        while (true){
            Socket aux= s.accept();
            new Thread(new ServerWorker(aux,c)).start();
        }

    }


}
