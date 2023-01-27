import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReuniaoFeito implements Reuniao {
    int espera;

    int elementosnaSala;
   int ListanaSala;

    Lock l = new ReentrantLock();
    Condition salaLivre = l.newCondition();



    public ReuniaoFeito(){
        this.elementosnaSala=0;
        this.espera=0;
        this.ListanaSala=0;
    }

    public void participa(int lista){
        l.lock();
        try {
            this.espera++;
            while(this.ListanaSala!=lista &&this.elementosnaSala!=0){
                salaLivre.await();
            }
            this.espera--;
            this.ListanaSala=lista;
            this.elementosnaSala++;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
    public void abandona(int lista){
        l.lock();
        try {
            if (this.ListanaSala==lista){
                if (this.elementosnaSala>0){
                    this.elementosnaSala--;
                    if (this.elementosnaSala==0){
                        this.ListanaSala=0;
                        this.salaLivre.signalAll();
                    }
                }

            }
        } finally {
            l.unlock();
        }

    }
    public int naSala(){
    l.lock();
        try {
        return this.ListanaSala;
    }finally {
            l.unlock();
        }
    }
    public int aEspera(){

        l.lock();
        try {
            return this.espera;
        }finally {
            l.unlock();
        }

    }

    public int elementosnaSala(){
        l.lock();
        try {
            return this.elementosnaSala;
        }finally {
            l.unlock();
        }

    }
}
