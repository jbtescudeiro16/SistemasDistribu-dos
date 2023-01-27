package codigo;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ControloVacinasServer implements ControloVacinas {

    private class Vaga {
        public int numeroUtentes = 0;
        public boolean jaSaiu = false;
        public Condition utentesSuficientes;
        public Condition existeFrasco;

        public Vaga(Condition utentesSuficientes, Condition existeFrasco) {
            this.utentesSuficientes = utentesSuficientes;
            this.existeFrasco = existeFrasco;
        }
    }

    private static final int NUM = 2;
    
    private ReentrantLock lock = new ReentrantLock();
    private List<Vaga> vagas;
    private int numeroFrascos;

    public ControloVacinasServer() {
        this.vagas = new ArrayList<>();
        this.numeroFrascos = 0;
    }

    @Override
    public void pedirVacinas() throws InterruptedException {
        try {
            this.lock.lock();
            if(this.vagas.size() == 0 || this.vagas.get(this.vagas.size() - 1).numeroUtentes == NUM) {
                this.vagas.add(new Vaga(this.lock.newCondition(), this.lock.newCondition()));
            }
            var vaga = this.vagas.get(this.vagas.size() - 1);
            vaga.numeroUtentes++;
            if(vaga.numeroUtentes == NUM) {
                vaga.utentesSuficientes.signalAll();
            }
            while(vaga.numeroUtentes < NUM && !vaga.jaSaiu) {
                vaga.utentesSuficientes.await();
            }

            while(this.numeroFrascos == 0 && !vaga.jaSaiu) {
                vaga.existeFrasco.await();
            }
            if(vaga.numeroUtentes == NUM) {
                this.vagas.remove(0);
                this.numeroFrascos--;
            }
            vaga.numeroUtentes--;
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public void fornecerFrascos(int frascos) {
        try {
            this.lock.lock();
            this.numeroFrascos += frascos;
            int i = 0;
            var iter = this.vagas.iterator();
            while(iter.hasNext() && i < frascos) {
                var vaga = iter.next();
                vaga.existeFrasco.signalAll();
                ++i;
            }
        } finally {
            this.lock.unlock();
        }
    }
    
}
