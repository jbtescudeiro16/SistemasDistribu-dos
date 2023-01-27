package MyRes.Ficha4;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Barrier2 {
    final int n;
    int counter=0;
    Lock l = new ReentrantLock();
    Condition c=l.newCondition();
    boolean ready=false;

    Barrier2 (int N) {
        this.n=N;
    }
    //nao tenho a certeza se esta bem
    void await() throws InterruptedException
    {
        l.lock();
        try {
            while(ready) c.await();
            counter++;
            if (counter<this.n){
                while (!ready){
                    c.await();
                }
            }
            else {
                c.signalAll();
                counter=0;
                this.ready=true;
            }

        }finally {
            l.unlock();
        }
    }
}