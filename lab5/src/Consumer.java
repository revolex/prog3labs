public class Consumer  implements Runnable{

    Fifo fifo;
    String string;
    int sTime;

    public Consumer(Fifo f, String s,int n){
        fifo=f;
        string=s;
        sTime=n;
    }

    @Override
    public void run() {
        while(true){
            try {
                System.out.println("consumed "+string+" "+fifo.get()+" "+System.currentTimeMillis()%100000);
                Thread.sleep(sTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
