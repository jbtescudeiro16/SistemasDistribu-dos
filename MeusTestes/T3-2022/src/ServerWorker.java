import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class ServerWorker implements Runnable{

    private ControloEntrada c;
    private Socket s;
    private DataOutputStream out;
     private DataInputStream in;



    public ServerWorker(Socket s,ControloEntrada c) throws IOException {
        this.c=c;
        this.s=s;
        this.out=new DataOutputStream(s.getOutputStream());
        this.in=new DataInputStream(s.getInputStream());
    }




public void run(){
    int opcao = 0;
    try {
        opcao = in.readInt();
        if (opcao==1){
        c.podeAbrirEntrada();
            out.writeUTF("abriu entrada");
            out.flush();
        }
        else if (opcao==2){
        c.podeFecharEntrada();
            out.writeUTF("Feechou entrada");
            out.flush();
        }
        else if (opcao==3){
            c.saiuPassageiro();
            out.writeUTF("Saiu Passageiro");
            out.flush();
        }
        else if (opcao==4){
            String cli =in.readUTF();
            c.entrouPassageiro(cli);
            out.writeUTF("entrou Passageiro");
            out.flush();
        }
        else if (opcao==5){

           List<String> aux = c.listarPassageiros();
            out.writeInt(aux.size());
            out.flush();
           for (int i=0;i<aux.size();i++){
            out.writeUTF(aux.get(i));
            out.flush();
           }

        }
    } catch (IOException e) {
        throw new RuntimeException(e);
    }





}

}
