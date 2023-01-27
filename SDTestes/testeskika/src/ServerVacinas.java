import javax.xml.crypto.Data;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.locks.Condition;

public class ServerVacinas {
    Socket socket;
    ControloVacinas c;

    DataInputStream in;
    DataOutputStream out;


    class ServerWorker implements Runnable{
        ServerWorker(Socket s, ControloVacinas cv) throws IOException {
            socket=s;
            c=cv;
            in = new DataInputStream(s.getInputStream());
            out = new DataOutputStream(s.getOutputStream());
        }

        @Override
        public void run() {
            try {
                String op =  in.readUTF();
                if (op == "FORNECER") {
                    int frascos = in.readInt();
                    c.fornecerFrascos(frascos);
                }
                else if (op=="VACINAR"){
                    c.pedirParaVacinar();
                }
            }
            catch (IOException e){

            }
            catch (InterruptedException e){

            }
        }
    }

    public void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(12345);
        ControloVacinas cv =  new ControloVacinas(10);
        while (true) {
            Socket socket = serverSocket.accept();
            Thread worker = new Thread(new ServerWorker(socket,cv));
            worker.start();
        }
    }

}
