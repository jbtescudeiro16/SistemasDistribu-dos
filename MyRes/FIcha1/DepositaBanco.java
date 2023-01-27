package MyRes.FIcha1;

class DepositaBanco implements Runnable {

    int value = 100;
    Bank bank;

    public  DepositaBanco(Bank bank, int val) {
        this.bank = bank;
        this.value = val;

    }

    public void run() {
        final long I = 1000;

        for (long i = 0; i < I; i++)
            bank.deposit(value);
    }
}
