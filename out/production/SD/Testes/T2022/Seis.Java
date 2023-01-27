package Testes.T2022;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Seis implements Reuniao{

//nao é preciso fazer com objeto da classe , da forma simples sá na mesma
    private final int N=10;
    private int sala=0;
    private  int listacorrente=0;
    private  int espera=0;

    Lock l = new ReentrantLock();
    Condition c = l.newCondition();



    public class Lista{
        int espera=0;
        Condition c = l.newCondition()
    }


    Lista[] k = new Lista[N];


    public void participa(int lista)throws InterruptedException
    {
        lista-=1;
        l.lock();
        try{
            espera+=1;
            k[lista].espera++;
        while(lista!=this.listacorrente && sala>0)

            k[lista].c.await();
        espera-=1;
        sala+=1;
        this.listacorrente=lista;

        }finally {
            l.unlock();
        }

    }


    public void abandona(int lista) {
        l.lock();
        try {
            sala-=1;
            if (sala==0) c.signalAll();
            //percorrer oisição da lista com mais à espera 

        this.listacorrente=0;
        }
     finally{
        l.unlock();
        }
    }


    public int naSala() {
        l.lock();
        try{
            return sala;
        }finally {
            l.unlock();
        }

    }

    public int aEspera() {
        l.lock();
        try {
            return espera;
        }
        finally {
            l.unlock();
        }
    }
}