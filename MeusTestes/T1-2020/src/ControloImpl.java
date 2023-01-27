import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ControloImpl  implements  ControloTrafegoAereo{

    private final int N=10;

    int pistaslivres;
    int aviaoespera;

    List<Boolean> pistas;

    Lock l;
    Condition pistalivre;

    public ControloImpl(){
        this.pistaslivres=N;
        this.aviaoespera=0;
        this.pistas=new ArrayList<>();
        for( int i=0;i<N;i++){
            pistas.add(i,true);
        }
        l=new ReentrantLock();
        pistalivre=l.newCondition();

    }

    public int pedirParaDescolar() {
       l.lock();
       int pista=-1;
       try {
           aviaoespera++;
           while (this.pistaslivres==0){
               this.pistalivre.await();
           }
            for(int i =0;i<N;i++){
                Boolean aux = pistas.get(i);
                if (aux){
                    aux=false;
                    pistas.add(i,aux);
                    pista= i;
                    pistaslivres--;
                    aviaoespera--;
                    break;
                }
            }
       } catch (InterruptedException e) {
           throw new RuntimeException(e);
       } finally {
           l.unlock();
       }
    return pista;
    }


    public int pedirParaAterrar() {
        l.lock();
        int pista=-1;
        try {
            aviaoespera++;
            while (this.pistaslivres==0){
                this.pistalivre.await();
            }
            for(int i =0;i<N;i++){
                Boolean aux = pistas.get(i);
                if (aux){
                    aux=false;
                    pistas.add(i,aux);
                    pista= i;
                    this.aviaoespera--;
                    this.pistaslivres--;
                    break;

                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            l.unlock();
        }
        return pista;
    }

    @Override
    public void descolou(int pista) {
        l.lock();
        try {
            this.pistas.set(pista,true);
            this.pistaslivres++;
            this.pistalivre.signal();
        }finally {
            l.unlock();
        }

    }

    @Override
    public void aterrou(int pista) {
        l.lock();
        try {
            this.pistas.set(pista,true);
            this.pistaslivres++;
            this.pistalivre.signal();
        }finally {
            l.unlock();
        }

    }
}
