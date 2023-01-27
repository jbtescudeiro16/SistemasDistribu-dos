import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SalaDeEsperaImpl  implements  SalaDeEspera{


    ArrayList<String> espera;
    int numaespera;
    boolean livre;
    private Lock l ;

    private Condition c;

    public SalaDeEsperaImpl(){
        this.l=new ReentrantLock();
        this.espera=new ArrayList<>();
        this.c= l.newCondition();
        this.numaespera=0;
        livre=true;
    }

    @Override
    public boolean espera(String nome) {
        l.lock();
        try {
            this.numaespera++;
            while (this.atende()!=nome) {
                c.await();
            }
            /*
            nao sabia como fazer
            if (this.desiste(nome)){
                return false;
            }
            */

            this.numaespera--;
            this.espera.remove(nome);
            this.livre=false;
            return true;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void desiste(String nome) {
       l.lock();
        try {
            this.numaespera--;
            if (this.espera.contains(nome)){
                this.espera.remove(nome);
            }

        }finally {
            l.unlock();
        }

    }

    @Override
    public String atende() {
        l.lock();
        try {
            if (this.numaespera>0){
                 String aux = espera.get(0);
                 return aux;
            }
        }finally {
            l.unlock();
        }
        return null;
    }
}
