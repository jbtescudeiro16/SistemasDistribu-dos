import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerWorker implements Runnable {

    Socket s ;
    Votacao v;
    DataInputStream in;
    DataOutputStream out;


    public ServerWorker(Votacao v, Socket s) throws IOException {
        this.v=v;
        this.s=s;
        this.in = new DataInputStream(s.getInputStream());
        this.out=new DataOutputStream(s.getOutputStream());

    }

    public void run(){
    try {
        int id = in.readInt();
         if (v.verifica(id)){
              int cabine = v.esperaPorCabine();

              out.writeInt(cabine);
              out.flush();

              int voto= in.readInt();

              v.vota(voto);
              out.writeInt(1);
         }
         else {
             out.writeInt(-1);
             out.flush();
         }




    } catch (IOException e) {
        throw new RuntimeException(e);
    } finally {

    }
    }
}
