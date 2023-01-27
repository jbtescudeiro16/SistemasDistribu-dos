package Aula3;

import java.util.*;
import java.util.concurrent.locks.*;

class Bank {

    private static class Account {
        private int balance;

        Lock loc= new ReentrantLock();
        Account(int balance) { this.balance = balance; }
        int balance() { return balance; }
        boolean deposit(int value) {
            balance += value;
            return true;
        }
        boolean withdraw(int value) {
            if (value > balance)
                return false;
            balance -= value;
            return true;
        }
    }

    Lock l=new ReentrantLock();
    private Map<Integer, Account> map = new HashMap<Integer, Account>();
    private int nextId = 0;

    // create account and return account id
    public int createAccount(int balance) {
        Account c = new Account(balance);
        l.lock();
        try {
            int id = nextId;
            nextId += 1;
            map.put(id, c);
            return id;
        }finally {
            l.unlock();
        }
    }

    // close account and return balance, or 0 if no such account
    public int closeAccount(int id) {
        Account c;
        l.lock();
        try {
            c = map.remove(id);
            if (c == null)
                return 0;
            c.loc.lock();
        }
           finally{
                l.unlock();
            }
            try {
                return c.balance();
            } finally {
                c.loc.unlock();
            }
    }




    // account balance; 0 if no such account
    public int balance(int id) {
        Account c;
        l.lock();
         try {
          c = map.get(id);
          if (c == null)
              return 0;
            c.loc.lock();
            }
      finally {
          l.unlock();
        }try {
        return c.balance();
        }finally {
            c.loc.unlock();
        }
        }

    // deposit; fails if no such account
    public boolean deposit(int id, int value) {
        Account c ;
        l.lock();
        try {
            c = map.get(id);

            if (c == null)
                return false;
        c.loc.lock();
        }
        finally {
            l.unlock();
        }
        try {
            return c.deposit(value);
        }

        finally {
         c.loc.unlock();
        }

    }

    // withdraw; fails if no such account or insufficient balance
    public boolean withdraw(int id, int value) {
        Account c;
        l.lock();
        try {

            c = map.get(id);
            if (c == null)
                return false;
            c.loc.lock();
        }
        finally {
            l.unlock();
        }try {
            return c.withdraw(value);
        }finally {
            c.loc.unlock();
        }
    }

    // transfer value between accounts;
    // fails if either account does not exist or insufficient balance
    public boolean transfer(int from, int to, int value) {

        Account cfrom, cto;
        l.lock();
        try {
            cfrom = map.get(from);
            cto = map.get(to);
            if (cfrom == null || cto ==  null)
                return false;
            cfrom.loc.lock();
            cto.loc.lock();
        }finally {
        l.unlock();
        }
        try {
            return cfrom.withdraw(value) && cto.deposit(value);
        }
        finally {
            cfrom.loc.unlock();
            cto.loc.unlock();
        }

    }

    // sum of balances in set of accounts; 0 if some does not exist
    public int totalBalance(int[] ids) {
        int total = 0;
        Account c;
        for (int i : ids) {
            l.lock();
            try {
                c = map.get(i);
                if (c == null)
                    return 0;
                c.loc.lock();
                total += c.balance();
            }
            finally {
                l.unlock();
               // c.loc.unlock();
            }
        }
        return total;
  }

}
