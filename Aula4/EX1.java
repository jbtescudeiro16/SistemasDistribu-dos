package Aula4;
import  java.util.concurrent.locks.*;
public class EX1 {

    class Barrier {

        Lock l=new ReentrantLock();
        Condition c = l.newCondition();
        int counter=0;
        final int N;

        Barrier (int N) {this.N=N;


        }

        void await() throws InterruptedException {
            l.lock();
            int x;
            try {
                counter++;
                if (counter < N) {
                    while (counter < N) {
                        c.await();
                    }
                } else {
                    c.signalAll();
                    counter=0;
                }

            }finally {
                l.unlock();
            }
        }

    }

}
