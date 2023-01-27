import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {


    private  Votacao v;
    private ServerSocket s;







     public Server() throws IOException {
         this.v=new VotacaoImpl();
         this.s=new ServerSocket(80);
     }


     public void run() throws IOException {
         while (true){
             Socket soc = this.s.accept();
             new Thread(new ServerWorker(v,soc)).start();
         }
     }
}
