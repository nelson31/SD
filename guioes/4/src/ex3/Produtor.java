package ex3;

/**
 * Produtor   que só faz put() do BoundedBuffer
 * Usado para colocar nas threads
 *
 * @author nelson
 */

public class Produtor implements Runnable{

    private BoundedBuffer buffer;
    private Barreira barreira;

    public Produtor(BoundedBuffer b, Barreira barr){
        this.buffer = b;
        this.barreira = barr;
    }

    public void run() {
        try {
            for (int i = 1; i <= 100; i++) {
                buffer.put(20);
                System.out.println("Put numero: " + i);
            }
            this.barreira.esperar();
        }catch(InterruptedException e) {}

    }
}
