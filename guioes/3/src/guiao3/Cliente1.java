package guiao3;

import static java.lang.Thread.sleep;

public class Cliente1 implements Runnable {

    private Banco banco;
    private int[] contas;

    public Cliente1(Banco b){
        this.banco = b;
        this.contas = new int[10000];
    }

    public void imprimeNumContas(){

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
            System.out.println("Saldo: " + valor);
        }
        catch (ContaInvalida e){
            System.out.println("Conta inexistente");
        }
    }
     */
    /*
    public void run() {
        double valor = 0;

        try{
            valor = this.banco.consultar(0);
            System.out.println("Saldo de consulta: " + valor);
        }
        catch (ContaInvalida e){
            System.out.println("Conta Inexistente");
        }

    }
     */
    /*
    public void run() {

        try{
            for(int i=0;i<10000;i++)
                this.banco.transferir(0,1,1);
            //System.out.println("Saldo conta 1: " + this.banco.consultar(1));
        }
        catch (ContaInvalida e){
            System.out.println("Conta Inexistente ");
        }
        catch (DinheiroInsuficiente e){
            System.out.println("Dinheiro conta 0 insuficiente!");
        }

    }
    */

    public void run() {

        int[] contas = new int[10000];
        for(int i=0;i<10000;i++)
            contas[i] = i;

        double total = banco.consultarTotal(contas);

        System.out.println(total);

    }


}
