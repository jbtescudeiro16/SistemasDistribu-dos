package Aula1;


public class Ex1 {


    public static  class ConcurrentCounter {
        private int a = 10;
        private int b = 0;

        public void runConcurrentCounter() {
            for (int i = 0; i < 100; i++) {
                Thread thread = new Thread(new Runnable() {
                    public void run() {
                        if (a > 0) {
                            a--;
                            b++;
                        }
                    }
                });
                thread.start();
            }
        }

        public static void main(String[] args){
            ConcurrentCounter counter = new ConcurrentCounter();
            counter.runConcurrentCounter();
            System.out.println("Valor final de a: " + counter.a);
            System.out.println("Valor final de b: " + counter.b);
        }
    }
}
