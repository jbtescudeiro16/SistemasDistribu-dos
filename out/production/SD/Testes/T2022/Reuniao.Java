package Testes.T2022;

interface Reuniao {
    void participa(int lista) throws InterruptedException;
    void abandona(int lista);
    int naSala();
    int aEspera();
}