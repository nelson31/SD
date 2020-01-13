package guiao2;

public class Counter {

    private int inc;

    public Counter(){
        this.inc=0;
    }

    public synchronized int increment(){
        return ++this.inc;
    }

    public void increment2(){
        synchronized (this){
            this.inc++;
            Thread.currentThread().getName();
        }
    }

    public int getInc(){
        return this.inc;
    }

}
