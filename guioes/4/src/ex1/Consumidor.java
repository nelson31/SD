package ex1;

public class Consumidor implements Runnable{

    private BoundedBuffer buffer;

    public Consumidor(BoundedBuffer b){
        this.buffer = b;
    }

    /**
     * Metodo por onde se da inicio á execuçao da thread
     */
    public void run() {
        int v=0;
        for(int i=1;i<=20;i++){
            try{
                v=buffer.get();
                System.out.println("Get numero: " + i + " Valor: " + v);
            }
            catch (InterruptedException e){}
        }
    }
}
