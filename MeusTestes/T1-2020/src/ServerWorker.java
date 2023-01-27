import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerWorker implements Runnable {

    private DataInputStream in;
    private DataOutputStream out;
    private Socket s;
    private ControloTrafegoAereo cont;


    public ServerWorker(Socket s,ControloTrafegoAereo C) throws IOException {
        this.s=s;
        this.cont=C;
        this.in=new DataInputStream(s.getInputStream());
        this.out=new DataOutputStream(s.getOutputStream());
    }

    public void run(){
        try {
            String pedido = in.readUTF();
            if (pedido=="DESCOLOU"){
                int auxiliar= in.readInt();
                cont.descolou(auxiliar);
                out.writeUTF("DESCOLAGEM REGISTADA :");
                out.flush();

            }
            else if  (pedido == "PEDIR ATERRAR "){
              int aux=   cont.pedirParaAterrar();
              out.writeUTF("ATERRAR NA PISTA :");
               out.writeInt(aux);
               out.flush();
            }
            else if  (pedido == "PEDIR DESCOLAR"){
                int aux = cont.pedirParaDescolar();

                out.writeUTF("DESCOLAR NA PISTA :");
                out.writeInt(aux);
                out.flush();
            }
            else if  (pedido == "ATERROU"){
                int auxiliar= in.readInt();
                cont.aterrou(auxiliar);
                out.writeUTF("ATERRAGEM REGISTADA :");
                out.flush();
            }
            s.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
