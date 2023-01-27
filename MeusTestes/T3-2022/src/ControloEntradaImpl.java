import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ControloEntradaImpl implements ControloEntrada{


    private int numFunicular;

    private List<String> bilhetes;
    private final int N=10;
    int portasabertas=0;
    Lock l ;
    Condition portanova;
    Condition funicularvazio;
    Condition funicularCheio;


    public ControloEntradaImpl(){
        this.numFunicular=0;
        this.funicularvazio=l.newCondition();
        this.portanova=l.newCondition();
        this.funicularCheio=l.newCondition();
        this.l=new ReentrantLock();
    }

    @Override
    public void podeAbrirEntrada() {
        l.lock();
        try {
            while (numFunicular>0){
                this.funicularvazio.await();
            }
            if (numFunicular==0){
                this.portasabertas++;
                this.portanova.signal();
            }


        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            l.unlock();
        }

    }

    @Override
    public void saiuPassageiro() {
        l.lock();
        try {
            this.numFunicular--;
            if (this.numFunicular==0){
               this.funicularvazio.signal();
            }
        }finally {
            l.unlock();
        }

    }

    @Override
    public void podeFecharEntrada() {
        l.lock();
        try {
            while (this.numFunicular<N){
            this.funicularCheio.await();
            }
            if (this.numFunicular==N){
                this.portasabertas--;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void entrouPassageiro(String bilhete) {
        l.lock();
        try{
            this.numFunicular++;

            if(++this.numFunicular == N) {
                this.funicularCheio.signal();
            }

        }finally {
            l.unlock();
        }
    }
    public List<String> listarPassageiros(){
        return new ArrayList<>();
    }
}
