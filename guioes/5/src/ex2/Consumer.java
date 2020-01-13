package ex2;

/**
 * Classe Consumidor que serve para nos criarmos as threeads
 * para testar a classe Warehouse
 */

public class Consumer implements Runnable{

    private Warehouse wh;

    public Consumer(Warehouse wh){
        this.wh = wh;
    }


    public void run() {
        String[] args = new String[3];
        args[0] = "item1";
        args[1] = "item2";
        args[2] = "item3";

        this.wh.consume(args);
    }
}
