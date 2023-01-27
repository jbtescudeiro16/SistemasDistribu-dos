package Pratica;

import java.util.concurrent.locks.Condition;

public class Lista {
    private int numeroLista;
    public Condition podeEntrar; 
    private int numeroEmEspera;

    public Lista(Condition condition, int numeroLista) {
        this.numeroEmEspera = 0;
        this.numeroLista = numeroLista;
        this.podeEntrar = condition;
    }

    public void addEspera() {
        ++this.numeroEmEspera;
    }

    public void remEspera() {
        --this.numeroEmEspera;
    }

    public int getElementsWait() {
        return this.numeroEmEspera;
    }
}