import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static java.util.Arrays.asList;

class ServerWorker implements Runnable {
    private Socket socket;
    private ReuniaoEstudantes re = new ReuniaoEstudantes();


    public ServerWorker (Socket socket) {
        this.socket = socket;
    }

    // @TODO
    @Override
    public void run() {
        try  {
            DataInputStream in = new DataInputStream((socket.getInputStream()));
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            int lista = in.readInt();

            re.participa(lista);  // por isto a correr em paralelo
            out.writeUTF("ENTRE"); // por isto a correr em paralelo
            socket.shutdownOutput();//por isto a correr em paralelo
            String pedido = in.readUTF(); //não está bem
            while (!socket.isOutputShutdown()) {
                if (pedido == "SALA") out.writeInt(re.naSala());
                else if (pedido == "ESPERA") out.writeInt(re.aEspera());
            }
            while (pedido!="ABANDONEI"){
                pedido = in.readUTF();
            }
            re.abandona(lista);
            socket.shutdownInput();
            socket.close();
        }
        catch (Exception e){


        }

    }
}



public class Server {
    public static void main (String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(12345);
        while (true) {
            Socket socket = serverSocket.accept();
            Thread worker = new Thread(new ServerWorker(socket));
            worker.start();
        }
    }

}
