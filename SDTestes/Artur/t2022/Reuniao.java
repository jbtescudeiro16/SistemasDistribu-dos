package Pratica;

public interface Reuniao {
    void participa(int lista) throws InterruptedException;
    void abandona(int lista);
    int naSala();

    int elementosnaSala();
    int aEspera();
}