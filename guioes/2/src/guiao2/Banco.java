package guiao2;

public class Banco {

    private double[] conta;


    public Banco(int n){
        this.conta = new double[n];
    }

    public synchronized double consultar(int conta){
        return this.conta[conta];
    }

    public synchronized void depositar(int conta, double valor){
        this.conta[conta]+=valor;
    }

    public synchronized void levantar(int conta, double valor){
        this.conta[conta]-=valor;
    }

    /**
     * Tranfere dinheiro da conta c1 para c2
     * @param c1
     * @param c2
     * @param valor
     */
    public synchronized void transferir(int c1, int c2, double valor){

        this.conta[c1]-=valor;
        this.conta[c2]+=valor;
    }
}
