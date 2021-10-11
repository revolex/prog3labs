public class Producer implements Runnable{
    private String m;
    private Fifo f;
    private int sTime;

    public Producer(String message, Fifo fifo, int n){
        m=message;
        f=fifo;
        sTime=n;
    }

    public void go() throws InterruptedException {
        int i =0;
        while(true){
            f.put(m+" "+i);
            Thread.sleep(500);
            i++;
        }
    }

    @Override
    public void run() {
        try {
            this.go();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
