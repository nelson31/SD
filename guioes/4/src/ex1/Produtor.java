package ex1;

public class Produtor implements Runnable{

    private BoundedBuffer buffer;

    public Produtor(BoundedBuffer b){
        this.buffer = b;
    }

    /**
     * Metodo por onde se da inicio á execuçao da thread
     */
    public void run() {
        for(int i=1;i<=20;i++){
            try{
                buffer.put(20);
                System.out.println("Put numero: " + i);
            }
            catch (InterruptedException e){}
        }
    }
}
