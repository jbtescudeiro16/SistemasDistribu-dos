package MyRes.Ficha2;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Bank {

  private static class Account {
    private int balance;

    Lock acclock = new ReentrantLock();
    Account(int balance) { this.balance = balance; }
    int balance() { return balance; }
    boolean deposit(int value) {
      acclock.lock();
      balance += value;
      acclock.unlock();
      return true;
    }
    boolean withdraw(int value) {
      if (value > balance)
        return false;
      acclock.lock();
      balance -= value;
      acclock.unlock();
      return true;
    }
  }

  // Bank slots and vector of accounts
  private int slots;
  private Account[] av; 

  Lock l = new ReentrantLock();
  public Bank(int n)
  {
    slots=n;
    av=new Account[slots];
    for (int i=0; i<slots; i++) av[i]=new Account(0);
  }

  // Account balance
  public int balance(int id) {
    if (id < 0 || id >= slots)
      return 0;
    l.lock();
    try {
      return av[id].balance();
    }finally {
      l.unlock();
    }

  }

  // Deposit
  boolean deposit(int id, int value) {

    if (id < 0 || id >= slots)
      return false;

   l.lock();
    try{
      return av[id].deposit(value);
      }finally {
      l.unlock();
      }
  }

  // Withdraw; fails if no such account or insufficient balance
  public boolean withdraw(int id, int value) {
    if (id < 0 || id >= slots)
      return false;
    l.lock();
    try {
      return av[id].withdraw(value);
    }finally {
      l.unlock();
    }

  }

  public boolean transfer (int from,int to, int value) {
    if (from<0 || to<0 || from >=slots || to>=slots)
      return false;
    l.lock();
    try {
      boolean aux = av[from].withdraw(value);
      if (aux == false) return false;
      else return av[to].deposit(value);

    }
    finally {
      l.unlock();
    }
    }
    public int totalBalance (){
    int b=0;
    if (slots<=0) return 0;
    else
      l.lock();
    try {
      for ( int i =0;i<slots;i++){
        b+=av[i].balance();
      }
      return b;
    }finally {
      l.unlock();
    }
    }

}
