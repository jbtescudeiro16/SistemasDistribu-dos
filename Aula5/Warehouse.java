package Aula5;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Warehouse {
  private Map<String, Product> map =  new HashMap<String, Product>();

  private Lock l = new ReentrantLock();
  private class Product { int quantity = 0;
  Condition c = l.newCondition();

  }

  private Product get(String item) {

      Product p = map.get(item);
      if (p != null) return p;
      p = new Product();
      map.put(item, p);
      return p;
    }

  }

  public void supply(String item, int quantity) {
   l.lock();
    try {
      Product p = get(item);
      p.quantity += quantity;
      //para avisar que chegaram produtos novos a todas as threads
      p.c.signalAll();
    }
    finally {
      l.unlock();
    }

  }


  public void consume(Set<String> items) throws InterruptedException {
    l.lock();
    try {
      for (String s : items) {
        Product p = get(s);
        while (p.quantity == 0)
          p.c.await();
        p.quantity--;
      }



    }finally {
      l.unlock();
    }
  }




public void consume2 (Set<String> items) throws InterruptedException {
        l.lock();
        try {
        for (String s : items) {
        Product p = get(s);
        while (p.quantity == 0)
        p.c.await();
        }
        for (String s : items) {
        Product p = get(s);
        p.quantity--;
        }
        }finally {
        l.unlock();
        }
        }

}
