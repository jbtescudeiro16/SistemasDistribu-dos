import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class ServerWorker implements Runnable {

    Ficheiros f;
    Socket s;

    DataInputStream in ;
    DataOutputStream out;




    public ServerWorker(Ficheiros f, Socket s) throws IOException {
        this.f=f;
        this.s=s;
        in=new DataInputStream(s.getInputStream());
        out= new DataOutputStream(s.getOutputStream());
    }

    public void run(){
    try {
        String pedido = in.readUTF();
        if (pedido.equals("USING")){
            String file = in.readUTF();
            f.using(file);
            out.writeUTF("Pedido realizado com sucesso");
            out.flush();
        }
        if (pedido.equals("NOT USING")){
            String file = in.readUTF();
            boolean changed  = in.readBoolean();
            f.notUsing(file,changed);
            out.writeUTF("Pedido realizado com sucesso");
            out.flush();
        }
        if (pedido.equals("BACKUP")){
           List<String> aux = f.startBackup();
           out.writeInt(aux.size()-1);
           out.flush();
           for (int i=0;i<aux.size()-1;i++){
               out.writeUTF(aux.get(i));
               out.flush();
           }
            out.writeUTF("Pedido realizado com sucesso");
            out.flush();
        }

    } catch (IOException e) {
        throw new RuntimeException(e);
    }


    }
}
