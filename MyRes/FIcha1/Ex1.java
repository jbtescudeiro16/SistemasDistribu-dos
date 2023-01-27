package MyRes.FIcha1;

public class Ex1 {
    public static void main(String[] args)  throws InterruptedException{

        int K =10;
        int i ;
        Thread [] a = new Thread[K];

        System.out.printf("");

        for ( i=0;i<K;i++)
        {
            a[i]= new Thread(new Increment());

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

    }
}
