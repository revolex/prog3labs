import java.lang.reflect.Array;
import java.util.ArrayList;

public class Fifo {
    ArrayList<String> tarolo = new ArrayList<>(10);

    public synchronized void put(String s) throws InterruptedException {
        System.out.println("put: "+Thread.currentThread());
        while(tarolo.size()>=10)
            this.wait();
        tarolo.add(s);
        this.notify();
        System.out.println("produced "+s+" "+(System.currentTimeMillis()%100000));
    }
    public synchronized String get() throws InterruptedException {
        System.out.println("get: "+Thread.currentThread());
        while(tarolo.size()<=0)
            this.wait();
        String temp = tarolo.get(0);
        tarolo.remove(0);
        this.notify();
        return temp;
    }
}
