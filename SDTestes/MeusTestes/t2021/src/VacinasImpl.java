import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class VacinasImpl implements ControloVacinas{


    public class Vagas{
        int numeropacientes;
        Condition vagacheia;
        Condition hafrascos;
        boolean administrado;


     public Vagas (Condition v,Condition H){
         this.numeropacientes=0;
         this.administrado=false;
         this.vagacheia=v;
         this.hafrascos=H;
     }



    }



    private final int N=100;
    int frascos;
    Lock l = new ReentrantLock();
    Condition C1=l.newCondition();



    ArrayList<Vagas> esperandoFrascos;


    public VacinasImpl(){
        this.frascos=0;
        this.esperandoFrascos=new ArrayList<>();
    }
    public void pedirParaVacinar() {
        l.lock();
        try {

            if (this.esperandoFrascos.size()==0 || this.esperandoFrascos.get(esperandoFrascos.size()-1).numeropacientes== N  ){
                this.esperandoFrascos.add(new Vagas(l.newCondition(),l.newCondition()));
            }

            Vagas v=this.esperandoFrascos.get(esperandoFrascos.size()-1);
            v.numeropacientes++;
            if (v.numeropacientes==N){
                v.vagacheia.signalAll();
            }
            while (v.numeropacientes<N && !v.administrado){
                v.vagacheia.await();
            }
            while (this.frascos==0 && !v.administrado){
                v.hafrascos.await();
            }
            if (v.numeropacientes==N){
                v.administrado=true;
                this.frascos--;
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            l.unlock();
        }

    }


    public void fornecerFrascos(int frascos) {
    l.lock();
    try {
        this.frascos+=frascos;
        for(int i=0;i<this.esperandoFrascos.size()-1;i++){
        Vagas aux=this.esperandoFrascos.get(i);
        aux.hafrascos.signalAll();
        }
    }finally {
        l.unlock();
    }


    }
}
