import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FicheirosImpl implements Ficheiros {
    public class Fich{
        String nome;
        boolean using;
        boolean changed;

        Lock fileLock;
        Condition fileCondition;

        public Fich(){
            this.nome = "";
            this.using=false;
            this.changed=false;
            this.fileLock=new ReentrantLock();
            this.fileCondition=fileLock.newCondition();
        }

    }
    int waiting;
    List <Fich> files;
    Lock l ;
    Condition c1;


    public FicheirosImpl(){
        int waiting=0;
        this.l=new ReentrantLock();
        this.c1=l.newCondition();

    }
    public void  using(String path)
    {
        l.lock();
    try {
        waiting++;
        int i=0;
        for ( i=0;i<files.size();i++){
            if (files.get(i).nome.equals(path)) break;
        }
        Fich aux = files.get(i);
        while (aux.using) c1.await();
        waiting--;
        aux.using=true;
    } catch (InterruptedException e) {
        throw new RuntimeException(e);
    }
    }
   //not sure but pretty confident
     public void notUsing(String path, boolean modified){
            boolean aux=false;
            for ( int i=0;i<files.size()-1 && !aux;i++){
                 Fich ficheir = files.get(i);
                 ficheir.fileLock.lock();
                try {
                    if (ficheir.nome.equals(path)){
                       ficheir.using=false;
                       ficheir.changed=modified;
                       ficheir.fileCondition.signalAll();
                    }
                }finally {
                    ficheir.fileLock.unlock();
                }
            }


        }



        public  boolean allFree(){
            l.lock();
            try {
                for (int i=0;i<files.size()-1;i++){
                    if (files.get(i).using) return false;
                }
                return true;
            }finally {
                l.unlock();
            }
        }
    public List<String> startBackup(){
        l.lock();
        try {
            List<String> mudados=new ArrayList<>();
            if (allFree()){
                for(int i=0;i<files.size()-1;i++){
                    if (files.get(i).changed) mudados.add(files.get(i).nome);
                }
            }
            return mudados;
        }finally {
            l.unlock();
        }
    }
    public void endBackup(){

    }
}
