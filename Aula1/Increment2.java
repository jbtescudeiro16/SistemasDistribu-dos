package Aula1;

 class Increment2 implements Runnable{

     int total;
     Bank bank;
     public Increment2 (int i , Bank b){
         this.total = i;
         this.bank = b;
     }
         public void run() {
             final long I=1000;

             for (long i = 0; i < I; i++){

               bank.deposit(total);
         }


     }
}
