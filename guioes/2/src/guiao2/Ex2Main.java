package guiao2;

public class Ex2Main {


    public static void main(String[] args) throws Exception{

        Banco banco = new Banco(2);

        Cliente1 cl1 = new Cliente1(banco);
        Cliente2 cl2 = new Cliente2(banco);

        Thread[] arr = new Thread[2];

        arr[0]= new Thread(cl1);
        arr[1]= new Thread(cl2);

        arr[0].start();
        arr[1].start();

        for(Thread t:arr)
            try{
                t.join();
            }catch (InterruptedException exc){
                System.out.println("Fim");
            }

        System.out.println("Valor final: " + banco.consultar(0));

    }



}
