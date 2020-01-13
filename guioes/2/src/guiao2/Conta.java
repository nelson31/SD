package guiao2;

public class Conta {

    private double valor;

    public Conta(){
        this.valor=0;
    }

    public void depositar(double valor){
        this.valor+=valor;
    }

    public void levantar(double valor){
        this.valor-=valor;
    }

    public double consultar(){
        return this.valor;
    }
}
