import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerWorker implements Runnable {


    SalaDeEspera sala;
    Socket s;
    DataOutputStream out;
    DataInputStream in;



    public ServerWorker(SalaDeEspera sala , Socket s) throws IOException {
        this.sala=sala;
        this.s=s;
        this.out = new DataOutputStream(s.getOutputStream());
        this.in=new DataInputStream(s.getInputStream());
    }

    public void run(){
        try {
            String nome = in.readUTF();
            sala.espera(nome);
            if (in.readUTF().equals("Desiste") ) {
                sala.desiste(nome);
                out.writeUTF("Desistencia Aceite");
                out.flush();
            }
            out.writeUTF("Processo Terminado");
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
