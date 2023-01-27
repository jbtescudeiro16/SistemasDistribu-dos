package Pratica;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    private Reuniao reuniao; 
    private ServerSocket socket;

    public Server() throws IOException {
        this.reuniao = new ReuniaoValorizacaoImpl();
        this.socket = new ServerSocket(80);
    }

    public void run() throws IOException
    {
        while(true) {
            var clientSocket = this.socket.accept();
            new Thread(new ServerWorker(clientSocket, this.reuniao)).start();
        }
    }

}
