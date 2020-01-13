package ex2;

public class Produtor implements Runnable{

    private BoundedBuffer buffer;
    private long tp;
    private int totalOps;

    public Produtor(BoundedBuffer b, long tp, int total_ops){
        this.buffer = b;
        this.tp=tp;
        this.totalOps=total_ops;
    }

    public void run() {
        for(int i=1;i<=this.totalOps;i++){
            try{
                buffer.put(20);
                System.out.println("Put numero: " + i);
                Thread.sleep(this.tp);
            }
            catch (InterruptedException e){}
        }
    }
}
