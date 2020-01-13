package guiao2;

public class Cliente3 implements Runnable {

    private BancoEx4 banco;

    public Cliente3(BancoEx4 b){
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
            this.banco.transferir(0,1,1);
        //this.banco.levantar(1,1000);
        //this.banco.transferir(0,1,1000);
    }
}
