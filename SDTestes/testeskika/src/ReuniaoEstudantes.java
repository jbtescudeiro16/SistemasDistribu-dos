import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReuniaoEstudantes implements Reuniao{
    private ReentrantLock l= new ReentrantLock();
    private int currentLista=0;
    private int npessoas=0;
    private Map<Integer,Integer> sala = new HashMap<>();
    private Map<Integer, Condition> waitingConditions = new HashMap<>();

    public ReuniaoEstudantes(){

    }

    @Override
    public void participa(int lista) throws InterruptedException {
        l.lock();
        try {
            while (currentLista != lista) {
                if (!waitingConditions.containsKey(lista)) waitingConditions.put(lista, l.newCondition());
                Integer n = sala.get(lista);
                if (n == null) sala.put(lista, 1);
                else sala.put(lista, n + 1);
                waitingConditions.get(lista).await();
            }
            npessoas++;
            int n = sala.get(lista);
            if (n == 1) sala.remove(lista);
            else sala.put(lista, n - 1);
        }
        finally {
            l.unlock();
        }
    }

    private int getNextList(){
        int l = -1;
        int maxP = -1;
        for (int i: sala.keySet()){
            int n = sala.get(i);
            if (n>maxP){
                l= i;
                maxP=n;
            }
        }
        return l;
    }

    @Override
    public void abandona(int lista) {
        l.lock();
        try {
            if (lista == currentLista) {
                npessoas--;
                if (npessoas == 0) {
                    int nl = getNextList();
                    if (nl != -1) {
                        currentLista = nl;
                        waitingConditions.get(nl).signalAll();
                    }
                }
            }
        }
        finally {
            l.unlock();
        }
    }

    @Override
    public int aEspera() {
        l.lock();
        try {
            int r = 0;
            for (int i : sala.values())
                r += sala.get(i);
            return r;
        }
        finally {
            l.unlock();
        }
    }

    @Override
    public int naSala() {
        l.lock();
        try {
            return npessoas;
        }
        finally {
            l.unlock();
        }
    }
}
