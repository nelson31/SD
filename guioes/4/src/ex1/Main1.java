package ex1;

public class Main1 {

    public static void main(String[] args){

        //BoundedBufferLocks buf=new BoundedBufferLocks(10);
        BoundedBuffer buf = new BoundedBuffer(10);

        Thread[] arr = new Thread[2];

        Produtor p = new Produtor(buf);
        Consumidor c = new Consumidor(buf);

        arr[0]= new Thread(p);
        arr[1]= new Thread(c);

        arr[0].start();
        arr[1].start();

        for(Thread t:arr)
            try{
                t.join();
            }catch (InterruptedException exc){
                System.out.println("Fim");
            }

    }
}
