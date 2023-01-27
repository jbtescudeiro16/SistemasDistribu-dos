import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerWorker implements Runnable {



    private Reuniao reuniao;
    private DataInputStream in ;
    private DataOutputStream out;
     private Socket s;


     public ServerWorker(Reuniao i,Socket s ) throws IOException  {
         this.reuniao =i;
         this.s=s;
         this.out= new DataOutputStream(  s.getOutputStream());
         this.in= new DataInputStream(s.getInputStream());
     }

     public void run (){
         try{
             int lista = this.in.readInt();
             boolean exit=false;
             Runnable inputRunnable = (() ->{
                 try {
                     while (true){
                         int question = in.readInt();
                         if (question==0) this.reuniao.aEspera();
                         if (question==1) this.reuniao.naSala();
                         if (question==2) this.reuniao.elementosnaSala();


                     }
                 } catch (IOException e) {
                     throw new RuntimeException(e);
                 }


             });

             var t  = new Thread(inputRunnable);
             t.start();
             this.reuniao.participa(lista);
             t.interrupt();
             this.out.writeUTF("eNTREI");
             this.out.flush();
             boolean sair=false;
             while (!sair){
                 String answer = this.in.readUTF();
                 if (answer.equals("ABANDONEI")) sair=true;
             }
         } catch (IOException e) {
             throw new RuntimeException(e);
         }
     }

}
