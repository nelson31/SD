package guiao2;

public class Cliente1 implements Runnable {

    private Banco banco;

    public Cliente1(Banco b){
        this.banco = b;
    }

    /* Para o Ex2
    public void run() {
        for(int i=0;i<100000;i++)
            this.banco.depositar(0,5);
    }
    */

    public void run() {
        for(int i=0;i<1000;i++)
            this.banco.transferir(0,1,1);
        //this.banco.transferir(0,1,1000);
    }
}
