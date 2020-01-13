package guiao2;

public class Cliente2 implements Runnable {

    private Banco banco;

    public Cliente2(Banco b){
        this.banco = b;
    }

    /* Ex2
    public void run() {
        for(int i=0;i<100000;i++)
            this.banco.levantar(0,5);
    }
    */

    public void run() {
        for(int i=0;i<1000;i++)
            this.banco.levantar(1,1);
        //this.banco.levantar(1,1000);
    }
}