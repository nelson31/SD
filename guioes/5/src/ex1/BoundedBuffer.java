package ex1;

/**
 * BoundedBuffer feito com o uso de Locks,
 * e uso de variaveis de condiçao, e primitivas como await()
 * e signal()/signalAll()
 *
 * @author nelson
 */

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Nova versao do BoundedBuffer usando ReentrantLocks
 * e VARIAVEIS DE CONDICAO
 */

public class BoundedBuffer {

    private int[] buffer;
    private int tamanhobuf;
    //Lock reentrante do objeto
    private Lock l = new ReentrantLock();
    // Variaveis de condicoes usadas nos locks
    // c1 -> Para o put
    private Condition c1 = l.newCondition();
    // c2 -> Para o get
    private Condition c2 = l.newCondition();

    /**
     * Construtor vazio de BoundedBufferLOCKS
     * @param n
     */
    public BoundedBuffer(int n){
        this.buffer=new int[n];
        this.tamanhobuf=0;
    }

    /**
     * Metodo que coloca num buffer um valor passado como parametro
     * @param v
     * @throws InterruptedException
     */
    public void put(int v) throws InterruptedException{

        // Bloqueamos o acesso simultaneo de varias threads
        l.lock();

        // Enquanto o buffer estiver cheio
        while(this.tamanhobuf == this.buffer.length){
            // Adormecemos a thread atraves da variavel de condicao c1
            c1.await();
        }

        //Colocamos o valor no array
        this.buffer[tamanhobuf++]=v;

        /* Acordamos somente a c2 pois é la que
        estao os gets à espera de ter valores no buffer*/
        c2.signal();

        l.unlock();
    }

    /**
     * Metodo que retira um valor do array
     * @return v
     * @throws InterruptedException
     */
    public int get() throws InterruptedException{
        int v;
        // Seccao Critica
        l.lock();

        // Enquanto o buffer estiver vazio
        while(this.tamanhobuf == 0){
            // Adormecemos a thread atraves da variavel de condicao c2
            c2.await();
        }

        v= this.buffer[--tamanhobuf];

        /* Acordamos somente a c1 pois é la que
        estao os puts à espera de colocar valores no buffer*/
        c1.signal();

        l.unlock();

        return v;
    }



}
