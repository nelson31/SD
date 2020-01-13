package ex2;

/**
 * Classe Produtor que serve para nos criarmos as threeads
 * para testar a classe Warehouse
 */

public class Producer implements Runnable{

    private Warehouse wh;


    public Producer(Warehouse wh){
        this.wh = wh;
    }

    public void run() {
        try{
            this.wh.supply("item1",1);
            Thread.sleep(3000);
            this.wh.supply("item2",1);
            Thread.sleep(3000);
            this.wh.supply("item3",1);
        } catch (InterruptedException e) {
            System.out.println("FIM");
        }

    }
}
