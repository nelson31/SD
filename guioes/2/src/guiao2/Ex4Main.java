package guiao2;

public class Ex4Main {

    public static void main(String[] args) throws Exception{

        BancoEx4 banco = new BancoEx4(2);

        Cliente3 cl1 = new Cliente3(banco);
        Cliente4 cl2 = new Cliente4(banco);

        Thread[] arr = new Thread[2];

        arr[0]= new Thread(cl1);
        arr[1]= new Thread(cl2);

        banco.depositar(0,1000);

        arr[0].start();
        arr[1].start();

        for(Thread t:arr)
            try{
                t.join();
            }catch (InterruptedException exc){
                System.out.println("Fim");
            }

        System.out.println("Valor final do cliente 1: " + banco.consultar(0));
        System.out.println("Valor final do cliente 2: " + banco.consultar(1));

    }
}
