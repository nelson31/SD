package guiao3;

public class Cliente2 implements Runnable {

    private Banco banco;
    private int[] contas;

    public Cliente2(Banco b){
        this.banco = b;
        this.contas = new int[10000];
    }

    public void getNumContas(){

        for(Integer i:contas)
            System.out.println(i);
    }


    /*
    public void run() {
        for(int i=0;i<10000;i++)
            contas[i] = this.banco.criarConta(2);
        //this.banco.transferir(0,1,1000);
    }
     */
/*
    public void run() {
        double valor = 0;
        try{
            valor = this.banco.fecharConta(0);
            System.out.println("Saldo da conta eliminada: " + valor);
        }
        catch (ContaInvalida e){
            System.out.println("Conta inexistente");
        }
    }
*/
    public void run() {

        try{
            for(int i=0;i<10000;i++)
                this.banco.transferir(1,0,1);
            //System.out.println("Saldo conta 0: " + this.banco.consultar(0));
        }
        catch (ContaInvalida e){
            System.out.println("Conta Inexistente ");
        }
        catch (DinheiroInsuficiente e){
            System.out.println("Dinheiro conta 1 insuficiente!");
        }

    }

}
