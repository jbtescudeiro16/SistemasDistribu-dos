package Aula2;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//EX 3 CHAVAL

public class Bank2 {
        private static class Account {
            Lock loc = new ReentrantLock();
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
        private Aula2.Bank2.Account[] av;

        private Lock l = new ReentrantLock();
        public Bank2(int n)
        {
            slots=n;
            av=new Aula2.Bank2.Account[slots];
            for (int i=0; i<slots; i++) av[i]=new Aula2.Bank2.Account(0);
        }

        // Account balance
        public int balance(int id) {

            if (id < 0 || id >= slots)
                return 0;
                Account aux = av[id];
                aux.loc.lock();
                try{
                    return aux.balance;
                }finally{
                    aux.loc.unlock();
                }


        }

        // Deposit
        public boolean deposit(int id, int value) {
            if (id < 0 || id >= slots)
                return false;

            Account aux = av[id];
            aux.loc.lock();
            try{
                return aux.deposit(value);
            }finally {
                aux.loc.unlock();
            }

        }

        // Withdraw; fails if no such account or insufficient balance
        public boolean withdraw(int id, int value) {
            if (id < 0 || id >= slots)
                return false;

            Account aux = av[id];
            aux.loc.lock();
            try{
                return aux.withdraw(value);
            }finally{
                aux.loc.unlock();
            }



        }

        public boolean transfer (int ftom,int to, int value) {

            if (ftom < 0 || ftom >= slots || to < 0 || to >= slots) return false;
            boolean b = av[ftom].withdraw(value);
            if (!b) return false;


            Account f = av[ftom];
            Account to2 = av[to];

            if (ftom < to) {
                f.loc.lock();
                to2.loc.lock();
            } else {
                to2.loc.lock();
                f.loc.lock();
            }
            try {
                try {
                    boolean ap = f.withdraw(value);
                    if (!ap) return false;
                } finally {
                    f.loc.unlock();
                }
                return to2.deposit(value);
            }
               finally{
                    to2.loc.unlock();
                }

        }

        public int totalBalance(){

                int b =0;
                for (int i=0; i<slots; i++) av[i].loc.lock();
                 for (int i=0; i<slots; i++) {
                     b+=balance(i);
                     av[i].loc.unlock();
                 }
            return b;
            }

        }


