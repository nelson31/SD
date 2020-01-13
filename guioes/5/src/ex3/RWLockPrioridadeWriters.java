package ex3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Solucao que da prioridade aos writers
 */

public class RWLockPrioridadeWriters {

    // Numero de leitores que estao a usar a seccao critica
    private int readers = 0;
    // Numero de escritores que estao a usar a seccao critica(Podia ser um booleano)
    // porque no max so podems ter 1 escritor ao mesmo tempo
    private int writers = 0;
    // Numero de escritores a espera de entrar
    private int writersRequest=0;

    private ReentrantLock l = new ReentrantLock();
    private Condition waitWriter = l.newCondition();
    private Condition waitReader = l.newCondition();


    /**
     * Metodo que serve para fazer lock da zona de leitura, ou
     * seja, o leitor passa a usar o RWLock
     *
     */
    public void readLock() throws InterruptedException{
        l.lock();

        // Neste caso nao é necessario ter this.readers>0 pk
        // podemos ter varios leitores ao mesmo tempo na seccao critica
        // Assim so se precisa de ver se existem escritores na seccao critica
        while(this.writers > 0 || this.writersRequest>0){
            this.waitReader.await();
        }
        // Aumenta o numero de leitores
        this.readers++;

        System.out.println("Entrou leitor");

        l.unlock();
    }

    /**
     * Metodo que serve para fazer unlock da zona de leitura, ou
     * seja, o leitor deixa de estar a usar o RWLock
     *
     */
    public void readUnlock() throws InterruptedException{
        l.lock();

        // Fica com menos 1 leitor
        readers--;

        // Caso nao hajam leitores temos de avisar os escritores!!!!
        if(readers == 0) {
            waitWriter.signal();
        }

        l.unlock();
    }

    /**
     * Metodo que serve para fazer lock da zona de escrita, ou
     * seja, o escritor passa a usar o RWLock
     *
     */
    public void writeLock() throws InterruptedException{
        l.lock();

        // Numero de pedidos de escritores
        this.writersRequest++;

        // Neste caso, temos de impedir que hajam escritores em simultaneo!!
        while(this.readers > 0 || this.writers > 0){
            this.waitWriter.await();
        }

        System.out.println("Entrou escritor");

        // Menos escritores a espera para entrar
        this.writersRequest--;

        // Um escritor conseguiu entrar na zona critica
        this.writers++;

        l.unlock();
    }

    /**
     * Metodo que serve para fazer unlock da zona de escrita, ou
     * seja, o escritor deixa de estar a usar o RWLock
     *
     */
    public void writeUnlock(){
        l.lock();

        // Decrementa o numero de escritores(ou seja,
        // avisa que já não se encontra um escritor na zona cr)
        writers--;

        //Avisa os leitores que podem adquirir lock
        waitReader.signalAll();

        // Avisa um escritor que pode adquirir lock
        waitWriter.signal();

        l.unlock();
    }
}
