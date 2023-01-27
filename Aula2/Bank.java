package Aula2;
import java.util.concurrent.locks.*;
public class Bank {
    private static class Account {
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

    // Bank slots and vector of accounts
    private int slots;
    private Account[] av;

    private Lock l = new ReentrantLock();
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
    public boolean deposit(int id, int value) {
        if (id < 0 || id >= slots)
            return false;
        l.lock();
        try {
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

    public boolean transfer (int ftom,int to, int value){

        if( ftom<0 || ftom>=slots || to <0 || to >=slots) return false;
        l.lock();
        try {
        boolean b  = av[ftom].withdraw(value);
        if (!b ) return false;
        else return deposit(to,value);
        } finally {
            l.unlock();
        }
    }
    public int totalBalance(){
    l.lock();
    try{
        int b =0;
        for (int i=0; i<slots; i++) b+= balance(i);
        return b ;
    }finally {
        l.unlock();
    }
    }
}
