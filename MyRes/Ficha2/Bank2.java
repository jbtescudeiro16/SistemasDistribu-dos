package MyRes.Ficha2;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Bank2 {

    private static class Account2 {
        private int balance;

        Lock acclock = new ReentrantLock();
        Account2(int balance) { this.balance = balance; }
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
    private Account2[] av;

    Lock l = new ReentrantLock();
    public Bank2(int n)
    {
        slots=n;
        av=new Account2[slots];
        for (int i=0; i<slots; i++) av[i]=new Account2(0);
    }

    // Account balance
    public int balance(int id) {
        if (id < 0 || id >= slots)
            return 0;
        Account2 Aux = av[id];
        Aux.acclock.lock();
        try {
            return Aux.balance();
        }finally {
            Aux.acclock.unlock();
        }

    }

    // Deposit
    boolean deposit(int id, int value) {

        if (id < 0 || id >= slots)
            return false;

        Account2 aux = av[id];
        aux.acclock.lock();
        try{
            return aux.deposit(value);
        }finally {
            aux.acclock.unlock();
        }
    }

    // Withdraw; fails if no such account or insufficient balance
    public boolean withdraw(int id, int value) {
        if (id < 0 || id >= slots)
            return false;
        Account2 aux = av[id];
        aux.acclock.lock();
        try {
            return aux.withdraw(value);
        }finally {
            aux.acclock.unlock();
        }

    }

    public boolean transfer (int from,int to, int value) {
        if (from<0 || to<0 || from >=slots || to>=slots)
            return false;
      boolean b = av[from].withdraw(value);

            if (b == false) return false;

            Account2 frm= av[from];
            Account2 tu= av[to];

            if (from<to){
                frm.acclock.lock();
                tu.acclock.lock();

        }
            else {
                tu.acclock.lock();
                frm.acclock.lock();
            }
            try{
                try {
                    boolean ap = frm.withdraw(value);
                    if (!ap) return false;
                }finally {
                    frm.acclock.unlock();
                }
                return tu.deposit(value);
            }
            finally {
                tu.acclock.unlock();
            }
    }
    public int totalBalance (){
        int b=0;

            for ( int i =0;i<slots;i++){
                av[i].acclock.lock();
            }
        for ( int i =0;i<slots;i++){
            b+=balance(i);
            av[i].acclock.unlock();
        }
        return b;

    }
}