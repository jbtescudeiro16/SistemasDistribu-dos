package Aula4;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class EX2 {

    class Barrier {

        Lock l=new ReentrantLock();
        Condition c = l.newCondition();
        int counter=0;
        boolean ready = false;
        final int N;

        Barrier (int N) {this.N=N;


        }
        void await() throws InterruptedException{
            l.lock();
            int x ,k ;
            boolean b ;

            try{
                while(ready)
                    c.await();
                counter+=1;
                if (counter<N){
                    while (!ready){
                        c.await();
                    }
                }else {
                    c.signalAll();
                    ready=true;
                }

                    counter+=1;
                if (counter==0)
                    ready=false;

                }finally {
                l.unlock();
            }
        }



    }

}
