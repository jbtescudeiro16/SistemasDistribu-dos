import java.util.List;

public interface ControloEntrada {
    void podeAbrirEntrada() ;
    void saiuPassageiro() ;
    void podeFecharEntrada() ;
    void entrouPassageiro(String bilhete);

    List<String> listarPassageiros();
}
