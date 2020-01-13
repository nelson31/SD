package ex3;

public class Reader implements Runnable {

    RWLockOptimized rwLock;

    public Reader(RWLockOptimized rw){
        this.rwLock = rw;
    }

    public void run(){
        try{
            rwLock.readLock();
            Thread.sleep(1000);
            rwLock.readUnlock();
        } catch (InterruptedException e){

        }
    }
}
