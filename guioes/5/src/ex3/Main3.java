package ex3;

public class Main3 {

    public static void main(String[] args) throws Exception{

        // RWLock rw = new RWLock();
        // RWLockPrioridadeWriters rw = new RWLockPrioridadeWriters();
        RWLockOptimized rw = new RWLockOptimized();

        Writer w = new Writer(rw);
        Reader r = new Reader(rw);

        Thread[] arr = new Thread[15];

        for(int i=0;i<15/2;i++){
            arr[i] = new Thread(w);
        }

        for(int i=15/2;i<15;i++){
            arr[i] = new Thread(r);
        }


        for(int i=0;i<15;i++){
            arr[i].start();
        }

        try{
            for(Thread t : arr)
                t.join();
        } catch (InterruptedException e){}


    }
}
