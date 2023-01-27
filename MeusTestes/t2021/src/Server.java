import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

private ControloVacinas v ;
private ServerSocket s ;


public Server() throws IOException {
this.v=new VacinasImpl();
this.s=new ServerSocket(80);
}


    public void run() throws IOException {
    while (true){
        Socket aux=s.accept();
        new Thread(new ServerWorker(this.v,aux)).start();
    }
    }
}






