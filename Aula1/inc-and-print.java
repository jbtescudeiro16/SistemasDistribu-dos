package Aula1;

class Increment implements Runnable {

  int a ;
  int b;
  public Increment(int a, int b){

    this.a=a;
    this.b=b;
  }

  public void run() {
    if(a>0){
      a--;
      b++;
    }
    System.out.println("a:" +a);
    System.out.println("b:"+b);
  }


}
