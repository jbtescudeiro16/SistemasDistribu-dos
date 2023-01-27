import java.io.DataInputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ControladorSalas implements  Controlador{

    class Teste{
        int nAlunos=0;
        LocalDateTime comecou = LocalDateTime.now();
        int id;
        int  capacidade;
        ReentrantLock l = new ReentrantLock();
        public Teste(int id,int capacidade){
            this.id=id;
            this.capacidade=capacidade;
        }
    }

    Map<Integer,Integer> salasOcupadas = new HashMap<>();

    Map<Integer,Teste> testes = new HashMap<>();

    ReentrantLock l = new ReentrantLock();
    Condition c = l.newCondition();

    @Override
    public void reserva(int testeId, int[] salaIds) throws InterruptedException {
        l.lock();
        boolean flag = true;
        while (flag) {
            flag=false;
            for (int id : salaIds) {
                while (salasOcupadas.containsKey(id)) {
                    c.await();
                    flag = true;
                }
                if (flag) break;
            }
        }
        for (int id : salaIds) salasOcupadas.put(id,0);
        testes.put(testeId,new Teste(testeId,20* salaIds.length));
        l.unlock();
    }

    @Override
    public boolean presenca(int testeId) {
        l.lock();
        try{
        Teste t = testes.get(testeId);
        t.nAlunos ++;
        if (t.capacidade ==0) return false;
        else t.capacidade--;
        return true;
        }
        finally {
            l.unlock();
        }

    }

    @Override
    public void entrega(int testeId) {


    }

    @Override
    public int comear_limpeza() {
        return 0;
    }

    @Override
    public void terminar_limpeza(int salaId) {

    }
}
