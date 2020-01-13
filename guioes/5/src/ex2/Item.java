package ex2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Item {

    private ReentrantLock l = new ReentrantLock();
    private Condition isEmpty = l.newCondition();
    // Variavel que guarda a quantidade de item
    private int quantity;
    // Variavel que guarda o nome do item
    private String nome;

    public Item(int n, String nome){
        this.quantity=n;
        this.nome = nome;
    }

    /**
     * Devolve o nome do item
     * @return
     */
    public String getNome(){
        return this.nome;
    }

    /**
     * Metodo que adiciona uma quantidade ao item em questao
     * @param quantity
     */
    public void supply(int quantity){

        this.l.lock();

        // consumir uma unidade e notificar as restantes
        this.quantity += quantity;

        System.out.println("Item: " + this.nome + " avisa que já tem stock!");

        this.isEmpty.signalAll();

        this.l.unlock();
    }

    /**
     * Metodo que tira uma unica unidade ao Item
     */
    public void consume(){

        this.l.lock();

        try {
            // Enquanto nao existir quantidade suficiente do item
            while (this.quantity == 0) {
                System.out.println("Item: " + this.nome + " à espera!");
                isEmpty.await();
            }
            // consumir uma unidade
            this.quantity--;
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        finally {
            // Importante nao esquecer de fazer unlock
            this.l.unlock();
        }

    }
}
