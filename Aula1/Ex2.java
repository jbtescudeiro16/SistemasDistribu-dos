package Aula1;

public class Ex2 {

    public static void main(String[] args)  throws InterruptedException{


        int K =10;
        int i ;
        Thread [] a = new Thread[K];
        Bank b= new Bank();
        System.out.printf("O valor no banco é : " + b.balance());

        for ( i=0;i<K;i++)
        {
            a[i]= new Thread(new Increment2(100,b));

        }
        for ( i=0;i<K;i++)
        {
            a[i].start();

        }
        for ( i=0;i<K;i++)
        {
            a[i].join();
        }
        System.out.println("Acabei bro!");
        System.out.printf("O valor no banco é : " + b.balance());

    }
}

