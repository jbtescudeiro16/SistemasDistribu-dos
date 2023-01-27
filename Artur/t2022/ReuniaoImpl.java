package Pratica;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReuniaoImpl implements Reuniao {

    private int listaEmSala;
    private int numeroMembros;
    private Lock salaLock = new ReentrantLock();
    private Condition semMembros = this.salaLock.newCondition();
    private int emEspera;

    public ReuniaoImpl() {
        this.listaEmSala = -1;
        this.numeroMembros = 0;
        this.emEspera = 0;
    }

    @Override
    public void participa(int lista) throws InterruptedException {
        try {
            this.salaLock.lock();
            ++this.emEspera;

            while (this.listaEmSala != lista && this.numeroMembros != 0) {
                this.semMembros.await();
            }
            --this.emEspera;

            ++this.numeroMembros;
            this.listaEmSala = lista;

        } finally {
            this.salaLock.unlock();
        }
    }

    @Override
    public void abandona(int lista) {

        try {
            this.salaLock.lock();
            if (this.listaEmSala == lista && this.numeroMembros > 0) {
                if (--this.numeroMembros == 0)
                    this.semMembros.signalAll();
            }
        } finally {
            this.salaLock.unlock();
        }
    }

    @Override
    public int naSala() {
        try {
            this.salaLock.lock();
            return this.numeroMembros;
        } finally {
            this.salaLock.unlock();
        }
    }

    @Override
    public int aEspera() {
        try {
            this.salaLock.lock();
            return this.emEspera;
        } finally {
            this.salaLock.unlock();
        }
    }

}
