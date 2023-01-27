package epocaespecial2022;

/**
 * ControloEntrada
 */
public interface ControloEntrada {

  void podeAbrirEntrada() throws InterruptedException;
  void saiuPassageiro();
  void podeFecharEntrada() throws InterruptedException;
  void entrouPassageiro(String bilhete);
}
