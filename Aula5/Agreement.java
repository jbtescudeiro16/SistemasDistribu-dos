package Aula5;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Agreement {

    private static class Stage{
        int max=Integer.MIN_VALUE;
    }

    Lock l = new ReentrantLock();
    Condition c =  l.newCondition();
    int counter=0;


    final int N;
    Stage stage=new Stage();

    Agreement (int N){this.N=N;}

    int propose(int choice ) throws InterruptedException{
        l.lock();
        try{
            final Stage stage =this.stage;
            counter++;
            stage.max=Math.max(stage.max,choice);
            if (counter<N){
                while(this.stage==stage){
                    c.await();
                }

            }
            else {
                this.stage=new Stage();
                counter=0;
                max=Integer.MIN_VALUE;
                c.signalAll();
            }
            return max;
        }finally {
             l.unlock();
        }
    }



    int propose(int choice) throws InterruptedException { ... }
}