package ex3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Versao criada para evitar fenomenos de starvation!
 * Totalmente justa(fair)
 */

public class RWLockOptimized {

    // Numero maximo a entrar na seccao critica
    private int max = 2;
    // Contador de readers que entraram seguidamente na seccao critica
    private int contadorReaders = 0;
    // Contador de writers que entraram seguidamente na seccao critica
    private int contadorWriters = 0;
    // Numero de escritores a espera de entrar
    private int writersRequest=0;
    // Numero de leitores a espera de entrar
    private int readersRequest=0;
    // Numero de leitores que estao a usar a seccao critica
    private int readers = 0;
    // Numero de escritores que estao a usar a seccao critica(Podia ser um booleano)
    // porque no max so podems ter 1 escritor ao mesmo tempo
    private int writers = 0;

    // Variaveis usadas para controlo de concorrencia
    private ReentrantLock l = new ReentrantLock(true);
    private Condition waitWriter = l.newCondition();
    private Condition waitReader = l.newCondition();


    /**
     * Metodo que serve para fazer lock da zona de leitura, ou
     * seja, o leitor passa a usar o RWLock
     *
     */
    public void readLock() throws InterruptedException{

        l.lock();

        // O numero de pedidos de acesso a zona critica aumenta
        this.readersRequest++;
        // Neste caso nao é necessario ter this.readers>0 pk
        // podemos ter varios leitores ao mesmo tempo na seccao critica
        // Assim so se precisa de ver se existem escritores na seccao critica
        // e se existem pedidos de escritores e em simultaneo de o numero de
        // leitores que entrou na seccao critica passou o limite
        while(this.writers > 0
                || (this.writersRequest>0 && this.contadorReaders>=this.max)){
            this.waitReader.await();
        }

        // Aumenta o numero de leitores
        this.readers++;
        //Diminui o numero de pedidos de leitura
        this.readersRequest--;
        // Coloca o numero de escritores que entraram seguidos na seccao critica
        // a zero, uma vez que agora entrou um leitor
        this.contadorWriters=0;
        // Aumenta o numero de leitores que entraram na seccao critica
        this.contadorReaders++;

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

        // Fica com menos 1 leitor na seccao critica
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

        // Numero de pedidos de escritores para aceder a seccao critica aumenta
        this.writersRequest++;
        // Neste caso, temos de impedir que hajam escritores em simultaneo!!
        // Caso hajam leitores a tentar aceder á seccao critica E o numero de
        // escritores que entraram em simultaneo na seccao critica for maior que
        // o max permitido, o escritor tem de esperar
        while(this.readers > 0 || this.writers > 0
                || ( this.readersRequest>0 && this.contadorWriters>=this.max)){
            this.waitWriter.await();
        }
        // Um escritor conseguiu entrar na zona critica
        this.writers++;
        // Diminuir o numero de pedidos de escritores
        this.writersRequest--;
        // Contador de readers volta a zero
        this.contadorReaders=0;
        // Aumenta o numero de escritores que entraram na seccao critica
        this.contadorWriters++;

        System.out.println("Entrou escritor");

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

        // Avisa um escritor que pode adquirir lock
        waitWriter.signal();

        //Avisa os leitores que podem adquirir lock
        waitReader.signalAll();

        l.unlock();
    }
}
