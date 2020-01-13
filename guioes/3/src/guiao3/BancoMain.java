package guiao3;

public class BancoMain {

    public static void main(String[] args){

        Banco b = new Banco();

        Thread[] arr = new Thread[10];

        //Cliente1 cl1 = new Cliente1(b);
        //Cliente2 cl2 = new Cliente2(b);

        /** Criar a contas **/
        for(int i=0;i<10000;i++)
            b.criarConta(100);
        //DEBUG
        try{
            System.out.println(b.consultar(0));
            System.out.println(b.consultar(1));
        }catch (ContaInvalida e){}


        for(int i=0;i<10;i++){
            Cliente1 cl1 = new Cliente1(b);
            arr[i]= new Thread(cl1);
        }

        for(int i=0;i<10;i++)
            arr[i].start();

        //arr[0]= new Thread(cl1);
        //arr[1]= new Thread(cl2);

        //arr[0].start();
        //arr[1].start();

        for(Thread t:arr)
            try{
                t.join();
            }catch (InterruptedException exc){
                System.out.println("Fim");
            }

        //cl1.imprimeNumContas();
        //cl2.imprimeNumContas();
        try {
            System.out.println("Saldo conta 0: " + b.consultar(0));
            System.out.println("Saldo conta 1: " + b.consultar(1));
        }
        catch (ContaInvalida e){}



    }
}
