package ex2;

public class Main2 {

    public static void main(String[] args) throws Exception{

        Warehouse wh = new Warehouse();

        Item i1 = new Item(0,"item1");
        Item i2 = new Item(0,"item2");
        Item i3 = new Item(0,"item3");

        wh.adicionar(i1);
        wh.adicionar(i2);
        wh.adicionar(i3);

        Thread[] arr = new Thread[2];

        Consumer c = new Consumer(wh);
        Producer p = new Producer(wh);

        arr[0] = new Thread(c);
        arr[1] = new Thread(p);

        arr[0].start();
        arr[1].start();

        try{
            for(Thread t : arr)
                t.join();
        } catch (InterruptedException e){}


    }
}
