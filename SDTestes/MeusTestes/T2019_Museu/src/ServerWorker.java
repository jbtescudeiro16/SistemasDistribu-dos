import java.io.*;
import java.net.Socket;

public class ServerWorker implements Runnable{
        DataOutputStream out;
        DataInputStream in;
        Socket socket ;
        Museu m ;





    public ServerWorker(Socket socket,Museu m) throws IOException {
        this.socket=socket;
        this.m=m;
        this.in=new DataInputStream( new BufferedInputStream(socket.getInputStream()));
        this.out=new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));


    }




    public void run(){
        try {
            String pedido = in.readUTF();
            if (pedido.equals("ENTER PT")){
                m.enterPT();
                out.writeUTF("REGISTO DE PORTUGUES EFETUADO");
                out.flush();

            }
           else if( pedido.equals("ENTER EN")){
                m.enterEN();
                out.writeUTF("REGISTO DE INGLES EFETUADO");
                out.flush();
            }
           else if (pedido.equals("ENTER GUIDE")){
               m.enterGuide();
               out.writeUTF("REGISTO DE GUIA EFETUADO");
               out.flush();
            }
            else if (pedido.equals("ENTER POLY")){
                m.enterGuide();
                out.writeUTF("REGISTO DE POLIGLOTA EFETUADO");
                out.flush();
            }

    socket.close();
        }catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
