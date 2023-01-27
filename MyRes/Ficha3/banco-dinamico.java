package MyRes.Ficha3;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Bank {

    private static class Account {
        Lock al = new ReentrantLock();
        private int balance;
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

    Lock bl= new ReentrantLock();
    private Map<Integer, Account> map = new HashMap<Integer, Account>();
    private int nextId = 0;

    // bloqueamos o banco
    public int createAccount(int balance) {
        bl.lock();
        try{
        Account c = new Account(balance);
        int id = nextId;
        nextId += 1;
        map.put(id, c);
        return id;
    }finally {
            bl.unlock();
        }
    }


    // bloquear o banco para ninguem aceder aquele objeto equanto rtiramos o valor . depois bloquear a conta
    public int closeAccount(int id) {
        Account c;
        bl.lock();
        try {
                c= map.remove(id);
            if (c == null)
                return 0;
            c.al.lock();
        }finally {
            bl.unlock();
        }
        try{
                return c.balance;
            }
            finally {
                c.al.unlock();
            }

        }

    // account balance; 0 if no such account
    public int balance(int id) {

        Account c;
        bl.lock();
        try {
            c= map.get(id);
            if (c == null)
                return 0;
            c.al.lock();
        }finally {
            bl.unlock();
        }try {
            return c.balance;
        }
        finally {
        c.al.unlock();
        }
    }

    public boolean deposit(int id, int value) {

        Account c ;
        bl.lock();
        try {
            c= map.get(id);
            if (c == null)
                return false;
            c.al.lock();
        }finally {
        bl.unlock();
        }
        try {
            return c.deposit(value);
        }
        finally {
            c.al.unlock();
        }
    }

    // withdraw; fails if no such account or insufficient balance
    public boolean withdraw(int id, int value) {
        Account c ;
        bl.lock();
        try {
            c= map.get(id);
            if (c == null)
                return false;
            c.al.lock();
        }finally {
            bl.unlock();
        }
        try {
            return c.withdraw(value);
        }
        finally {
            c.al.unlock();
        }
    }

    // transfer value between accounts;
    // fails if either account does not exist or insufficient balance
    public boolean transfer(int from, int to, int value) {


        Account cfrom, cto;
        bl.lock();
        try {
            cfrom = map.get(from);
            cto = map.get(to);
            if (cfrom == null || cto ==  null)
                return false;
            cfrom.al.lock();
            cto.al.lock();
        }
        finally {
            bl.unlock();
        }
        try {
            return cfrom.withdraw(value) && cto.deposit(value);
        }
        finally {
            cfrom.al.unlock();
            cto.al.unlock();
        }
    }

    // sum of balances in set of accounts; 0 if some does not exist
    public int totalBalance(int[] ids) {
        int total = 0;
        bl.lock();
        try {
            for (int i : ids) {
                Account c = map.get(i);
                if (c == null)
                    return 0;
                c.al.lock();
                total += c.balance();
                c.al.unlock();
            }
        }
        finally {
            bl.unlock();
        }
    return  total;
  }

}
