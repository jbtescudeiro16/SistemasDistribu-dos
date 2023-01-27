package epocaespecial2022;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ControloEntradaImpl implements ControloEntrada {

  private static final int N = 10;

  private int numeroPassageiros;
  private boolean entrada;
  private boolean saida;
  private ReentrantLock lock;
  private Condition funicularVazio;
  private Condition funicularCheio;

  public ControloEntradaImpl() {
    this.numeroPassageiros = 0;
    this.lock = new ReentrantLock();
    this.funicularCheio = this.lock.newCondition();
    this.funicularVazio = this.lock.newCondition();
  }

@Override
public void podeAbrirEntrada() throws InterruptedException {
    try {
      this.lock.lock();
      while(this.numeroPassageiros > 0) {
        this.funicularVazio.await();
      }
    } finally {
      this.lock.unlock();
    }
}

@Override
public void saiuPassageiro() {
    try {
      this.lock.lock();

      if(--this.numeroPassageiros == 0) {
        this.funicularVazio.signal();
      }

    } finally {
      this.lock.unlock();
    }
}

@Override
public void podeFecharEntrada() throws InterruptedException {
    try {
      this.lock.lock(); 
      while(this.numeroPassageiros < N) {
        this.funicularCheio.await();
      }
      this.entrada = false;
    } finally {
      this.lock.unlock();
    }
}

@Override
public void entrouPassageiro(String bilhete) {
    try {
      this.lock.lock();

      if(++this.numeroPassageiros == N) {
        this.funicularCheio.signal();
      }

    } finally {
      this.lock.unlock();
    }
}


  
}
