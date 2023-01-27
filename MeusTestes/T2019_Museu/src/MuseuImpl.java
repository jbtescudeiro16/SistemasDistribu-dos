import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MuseuImpl  implements Museu{
    private final int MAX=15;
    private final int MIN=10;


    private int  numEN;
    private  int numPT;
    private  int numPoly;
    private int guides;
    private  int readyGroups;

    Lock l;
    Condition grupos;
    Condition guias;

    public MuseuImpl(){
        this.l=new ReentrantLock();
        this.guias= l.newCondition();
        this.grupos=l.newCondition();
        this.guides=0;
        this.numPoly=0;
        this.numEN=0;
        this.numPT=0;
        this.readyGroups=0;

    }


    private boolean validnumb(int num){
        if (num<=MAX && num>=MIN ) return true;
        else return false;
    }
    @Override
    public void enterPT() {
        l.lock();
        try {
            this.numPT++;
            if (validnumb(this.numPT+this.numPoly)){
                this.numPT=0;
                this.numPoly=0;
                this.readyGroups++;
                this.grupos.signal();
            }
            while (this.guides==0){
                this.guias.await();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            l.unlock();
        }

    }

    @Override
    public void enterEN() {
        l.lock();
        try {
            this.numEN++;
            if (validnumb(this.numEN+this.numPoly)){
                this.numEN=0;
                this.numPoly=0;
                this.readyGroups++;
                this.grupos.signal();
            }
            while (this.guides==0){
                this.guias.await();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            l.unlock();
        }

    }


    @Override
    public void enterPoly()
    {
        l.lock();
        try {
            this.numPoly++;
            if (validnumb(this.numEN+this.numPoly)){
                this.numEN=0;
                this.numPoly=0;
                this.readyGroups++;
                this.grupos.signal();
            }
            else  if (validnumb(this.numPT+this.numPoly)){
                this.numPT=0;
                this.numPoly=0;
                this.readyGroups++;
                this.grupos.signal();
            }
            while (this.guides==0){
                this.guias.await();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            l.unlock();
        }
    }

    @Override
    public void enterGuide() {
        l.lock();
        try {
        this.guides++;
        this.guias.signalAll();
        while (this.readyGroups==0){
            this.grupos.await();
        }
        this.readyGroups--;
        this.guides--;


        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            l.unlock();
        }

    }
}
