import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class VotacaoImpl implements Votacao {


     int waiting;
     int cabinesLivres;
     ArrayList<Integer> identidadesjavotaram;
    ArrayList<Integer> votos;
    ArrayList<Boolean> cabines;
    Lock l ;
    Condition c1;





 public VotacaoImpl(){
            this.cabines=new ArrayList<>();
            this.waiting=0;
            this.cabinesLivres=0;
            this.identidadesjavotaram=new ArrayList<>();
            this.l=new ReentrantLock();
            this.c1= l.newCondition();
            this.votos=new ArrayList<>();
 }


    public boolean verifica(int identidade) {
     l.lock();
     try {
         if (this.identidadesjavotaram.contains(identidade)) return false;
        return true;
     }finally {
         l.unlock();
     }
    }


    public int esperaPorCabine() {
        l.lock();
        try {
            int aux =-1;
            this.waiting++;
            while(this.cabinesLivres==0){
                c1.await();
            }
            for (int i=0;i<cabines.size()-1;i++){
                if (cabines.get(i).equals(true)) {
                    aux = i;
                    cabines.set(aux,false);
                break;
                }
            }
            this.waiting--;
            this.cabinesLivres--;
            return aux;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            l.unlock();
        }
    }

    @Override
    public void vota(int escolha) {
     l.lock();
     try {
         this.waiting--;
        // this.identidadesjavotaram.add(identidade);

         int k= this.votos.get(escolha) ;
         this.votos.set(escolha,++k);


     }finally {
         l.unlock();
     }

    }

    public void desocupaCabine(int i) {
     l.lock();
     try {
         this.cabinesLivres++;
          this.cabines.set(i,true);
          this.c1.signal();
          } finally {
         l.unlock();
     }

    }

    /*
    public int vencedor() {
     l.lock();
     try {
         int max=0;
         int winner=-1;
         this.waiting=0;
         this.cabinesLivres=0;
         for(int i =0;i<this.votos.size()-1;i++){
             this.votos.get(i).l.lock();
             if (this.votos.get(i).votos > max){
                 max=this.votos.get(i).votos;
                 winner=this.votos.get(i).nome;
             }
             this.votos.get(i).l.unlock();

         }

     }finally {
         l.unlock();
     }
        return 0;
    }*/
}
