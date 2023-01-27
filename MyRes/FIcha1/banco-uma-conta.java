package MyRes.FIcha1;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Bank {

  private static class Account {
    Lock l = new ReentrantLock();
    private int balance;
    Account(int balance) { this.balance = balance; }
    int balance() { return balance; }
    boolean deposit(int value) {
      //para a 3 tem de se por aqui o lock 
      l.lock();
      balance += value;
      l.unlock();
      return true;
    }
  }

  // Our single account, for now
  private Account savings = new Account(0);

  // Account balance
  public int balance() {
    return savings.balance();
  }

  // Deposit
  boolean deposit(int value) {
    return savings.deposit(value);
  }
}
