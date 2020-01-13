package ex3;

/**
 * Consumidor   que só faz get() do BoundedBuffer
 * Usado para colocar nas threads
 *
 * @author nelson
 */

public class Consumidor implements Runnable{

    private BoundedBuffer buffer;
    private Barreira barreira;

    public Consumidor(BoundedBuffer b,Barreira barr){
        this.buffer = b;
        this.barreira=barr;
    }

    public void run() {
        int v=0;
        try {
            for (int i = 1; i <= 100; i++) {
                v = buffer.get();
                System.out.println("Get numero: " + i + " Valor: " + v);
            }
            this.barreira.esperar();
        } catch (InterruptedException e){}
    }
}
