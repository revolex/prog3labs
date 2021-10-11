public class Application {
    public static void main(String[] args) throws InterruptedException {
        Fifo storage = new Fifo();
        Thread ts[] = new Thread[7];
        for(int i=0;i<3;i++)
            ts[i] = new Thread(new Producer( "producer",storage, (int) (Math.random() * 1000)));
        for(int i=3;i<7;i++)
            ts[i] = new Thread(new Consumer(storage, "consumer", (int) (Math.random() * 1000)));

        for(int i=0;i<7;i++)
            ts[i].start();


    }
}
