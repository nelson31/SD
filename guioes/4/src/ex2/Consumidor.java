package ex2;

public class Consumidor implements Runnable{

    private BoundedBuffer buffer;
    private long tc;
    private int totalOps;

    public Consumidor(BoundedBuffer b, long tc, int total_ops){
        this.buffer = b;
        this.tc=tc;
        this.totalOps=total_ops;
    }

    public void run() {
        int v=0;
        for(int i=1;i<=this.totalOps;i++){
            try{
                v=buffer.get();
                System.out.println("Get numero: " + i + " Valor: " + v);
                Thread.sleep(this.tc);
            }
            catch (InterruptedException e){}
        }
    }
}
