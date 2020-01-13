package guiao3;

import java.util.concurrent.locks.ReentrantLock;

public class Conta {

    private double valor;
    private int id;
    private ReentrantLock lockConta= new ReentrantLock();

    public Conta(int id, double saldo){

        this.valor=saldo;
        this.id = id;
    }


    public void depositar(double valor){
        this.valor+=valor;
    }


    public void levantar(double v) throws DinheiroInsuficiente{
        double aux = this.valor-v;
        if(aux>=0) this.valor-=v;
        else throw new DinheiroInsuficiente();

    }


    public double consultar(){
        return this.valor;
    }


    public void lock(){
        this.lockConta.lock();
    }

    public void unlock(){
        this.lockConta.unlock();
    }

}
