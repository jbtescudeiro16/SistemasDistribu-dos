package codigo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerWorker implements Runnable {

    private ControloVacinas business;
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;

    public ServerWorker(ControloVacinas cos, Socket socket) throws IOException {
        this.socket = socket;
        this.business = cos;
        this.dis = new DataInputStream(socket.getInputStream());
        this.dos = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        
        try {
            int pedido = this.dis.readInt();
            if(pedido == 0) {
                this.business.pedirVacinas();
            } else if(pedido > 0) {
                this.business.fornecerFrascos(pedido);
            }
        } catch(IOException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

}
