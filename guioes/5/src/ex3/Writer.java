package ex3;

public class Writer implements Runnable{

   private RWLockOptimized rwLock;

    public Writer(RWLockOptimized rw){
        this.rwLock = rw;
    }

    public void run(){
        try{
            rwLock.writeLock();
            Thread.sleep(1000);
            rwLock.writeUnlock();
        } catch (InterruptedException e){
            System.out.println("FIM");
        }
   }
}
