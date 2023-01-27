package MyRes.FIcha1;

public class Ex2 {
    public static void main(String[] args)  throws  InterruptedException{
        int k =10;
        Thread arr[]  = new  Thread[k];
        Bank b= new Bank() ;
        int i;
        int value = 100;


        for (   i=0;i<k;i++)
        {
            arr[i]= new Thread(new DepositaBanco(b,value));

        }
        for ( i=0;i<k;i++)
        {
            arr[i].start();

        }
        for ( i=0;i<k;i++)
        {
            arr[i].join();

        }

        System.out.printf("O banco ficou com " + b.balance()+"euros");
    }
}
