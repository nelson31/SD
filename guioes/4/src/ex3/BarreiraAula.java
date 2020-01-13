package ex3;

/**
 * Resolvido pela professora
 */

public class BarreiraAula {

    private int round;
    private int count;
    private int limite;

    public BarreiraAula (int n){

        this.count = 0;
        this.limite = n;
        this.round = 0;
    }

    public synchronized void esperar() throws InterruptedException{

        int myid = ++this.count;
        int myround = this.round;
        //System.out.println("Ronda: " + myround);

        while (this.count < this.limite && this.round == myround){
            System.out.println(this.count + " -> Esperar!!!");
            //Thread.sleep(5);
            this.wait();
        }

        if(myid==this.limite){
            this.notifyAll();
            System.out.println(this.count + " -> Levantar Barreira");
            ++this.round;
            this.count=0;
        }

    }
}
