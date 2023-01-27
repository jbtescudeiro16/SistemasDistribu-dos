package codigo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    private ServerSocket socket; 
    private ControloVacinas business;

    public Server() {
        this.business = new ControloVacinasServer();
    }

    public void run() throws IOException {
        this.socket = new ServerSocket(80);

        while(true) {
            var clientSocket = this.socket.accept();
            new Thread(new ServerWorker(this.business, clientSocket)).start();;
        }
    }
}
