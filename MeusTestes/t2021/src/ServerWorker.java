import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerWorker implements Runnable {

    private  ControloVacinas vacinas;
    private DataOutputStream out;
    private DataInputStream in ;
    private Socket s ;


    public ServerWorker(ControloVacinas vacinas, Socket s) throws IOException {
        this.vacinas=vacinas;
        this.s=s;
        this.in = new DataInputStream(s.getInputStream());
        this.out=new DataOutputStream(s.getOutputStream());

    }

    public void run(){
        try {
            String recebido = in.readUTF();
            if (recebido.equals("FORNECER")) {
                int frascos = in.readInt();
                this.vacinas.fornecerFrascos(frascos);
            }
            else if (recebido.equals("VACINAR")){
                this.vacinas.pedirParaVacinar();
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
