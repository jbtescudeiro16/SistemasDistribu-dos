package Pratica;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class
ServerWorker implements Runnable {

    private Reuniao reuniao;
    private Socket sock;
    private DataOutputStream out;
    private DataInputStream in;


    public ServerWorker(Socket sock,  Reuniao reuniao) throws IOException {
        this.reuniao = reuniao;
        this.sock = sock;
        this.out = new DataOutputStream(sock.getOutputStream());
        this.in = new DataInputStream(sock.getInputStream());
    }

    @Override
    public void run() {
        try {
            int lista = this.in.readInt();
            boolean exit = false;
            Runnable inputRunnable = (() -> {
                try {
                    while (true) {
                        int question = this.in.readInt();
                        if (question == 0) {
                            this.reuniao.aEspera();
                        } else {
                            this.reuniao.naSala();
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException();
                }
            });
            var t = new Thread(inputRunnable);
            t.start();
            this.reuniao.participa(lista);
            t.interrupt();
            this.out.writeUTF("ENTRE");
            this.out.flush();
            boolean sair = false;
            while(!sair) {
                String answer = this.in.readUTF();
                if(answer.equals("ABANDONEI")) sair = true;
            }

            this.reuniao.abandona(lista);

        } catch (IOException ignored) {
            throw new RuntimeException();
        } catch (final InterruptedException ignored) {
            throw new RuntimeException();
        }
    }
    
}
