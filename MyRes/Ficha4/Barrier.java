package MyRes.Ficha4;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Barrier {
    final int num ;
    Lock l = new ReentrantLock();
    Condition c = l.newCondition();
    int counter=0;

    Barrier (int N) {
       this.num= N;


    }
    void await() throws InterruptedException
    {
    l.lock();
    try {
        counter++;
        if(counter<this.num)
        {
            //este while tem de existir para depois quando o counter >= numero de threads À espera ele saltar fora, senao so ficava a espera e nunca saia
            while (counter<this.num){
                c.await();
            }
        }
        else {
            c.signalAll();
            counter=0;
        }

        }finally {
        l.unlock();
    }
    }
}