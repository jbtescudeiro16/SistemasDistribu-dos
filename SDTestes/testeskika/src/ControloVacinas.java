import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ControloVacinas {
    ReentrantLock l =new ReentrantLock();
    int num;

    int now=0;

    int frascos = 0;

    boolean flag = true;
    Condition c1 = l.newCondition();
    List<Condition> waitingFrascos = new ArrayList<>();

    public ControloVacinas(int num){
        this.num=num;
    }
    void pedirParaVacinar() throws InterruptedException {
        l.lock();
        now++;
        if (flag && now>=num){
            while(this.frascos < 1){
                Condition c2 = l.newCondition();
                waitingFrascos.add(c2);
                c2.await();
            }
            c1.signalAll();
            flag=false;
        }
        else if (now>= num) {
            this.frascos--;
            flag=true;
            now=1;
        }
        while (now<num && this.frascos < 1) c1.await();
        l.unlock();


    };
    void fornecerFrascos(int frascos){
        l.lock();
        this.frascos+=frascos;
        for (int i=0; i<frascos && this.waitingFrascos.size()!=0;i++){
            Condition c  = waitingFrascos.remove(0);
            c.signal();
        }
        l.unlock();
    };
}
