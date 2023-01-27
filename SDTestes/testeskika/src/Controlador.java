public interface Controlador {
    void reserva(int testeId, int[] salaIds) throws InterruptedException; //docente reserva conjunto de salas
    boolean presenca(int testeId);// aluno regista presena no teste
    void entrega(int testeId);// aluno entrega o seu teste
    int comear_limpeza();// obter sala para limpeza
    void terminar_limpeza(int salaId);//fim de limpeza de sala
}

