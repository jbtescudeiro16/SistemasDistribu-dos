package Pratica;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReuniaoValorizacaoImpl implements Reuniao {

    private int listaEmSala;
    private int numeroMembros;
    private Lock salaLock = new ReentrantLock();
    private Map<Integer, Lista> listasEmEspera;

    public ReuniaoValorizacaoImpl() {
        this.listaEmSala = -1;
        this.numeroMembros = 0;
        this.listasEmEspera = new HashMap<>();
    }
    

    @Override
    public void participa(int lista) throws InterruptedException {
        try {
            this.salaLock.lock();

            var listaC = this.listasEmEspera.get(lista);
            if(listaC == null) {
                listaC = new Lista(this.salaLock.newCondition(), lista);
                this.listasEmEspera.put(lista, listaC);
            }

            listaC.addEspera();
            while(this.listaEmSala != lista && this.numeroMembros != 0) {
                listaC.podeEntrar.await();
            }

            this.listaEmSala = lista;
            listaC.remEspera();
            ++this.numeroMembros; 

        } finally {
            this.salaLock.unlock();
        }
    }

    @Override
    public void abandona(int lista) {
        try {
            this.salaLock.lock();

            if(this.listaEmSala == lista && this.numeroMembros != 0) {
                --this.numeroMembros;
                if(this.numeroMembros == 0) {
                    Optional<Lista> max = this.listasEmEspera.values().stream().max((l1, l2) -> l1.getElementsWait() - l2.getElementsWait());
                    max.ifPresent(l -> l.podeEntrar.signalAll());
                }
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
            return this.listasEmEspera
            .values()
            .stream()
            .mapToInt(lista -> lista.getElementsWait())
            .sum();
        } finally {
            this.salaLock.unlock();
        }
    }
    
}
