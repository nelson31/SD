package ex3;

/**
 * Ex 3 do guiao 4
 * @author nelson
 */

public class Barreira {

    // INteiro que guarda o numero de threads que chegam á
    // barreira até serem libertadas
    private int threadsNum;
    // Inteiro que guarda o numero maximo de threads bloquedas na barreira!
    private int N;
    // Boleano que nos diz se mais threads podem entrar na barreira ou nao
    private boolean podeEntrar;

    /**
     * Construtor vazio de Barreira
     * @param n
     */
    public Barreira(int n){
        this.threadsNum=0;
        this.N=n;
        this.podeEntrar=true;
    }

    /**
     * Metodo esperar que sincroniza N threads
     * @throws InterruptedException
     */
    public synchronized void esperar() throws InterruptedException{

        while(!this.podeEntrar){
            // THEADS ESPERAM QUE A OUTRAS THREADS SAIAM DA BARREIRA
            wait();
        }

        // AUMENTA O NUMERO DE THREADS QUE ENTRAM NA BARREIRA
        this.threadsNum++;

        // Se o numero de threads que entrou for igual ao max da barreira,
        // ja nao se pode entrar na barreira
        if(this.threadsNum==this.N)
            this.podeEntrar=false;

        // Como o wait() tem um bug, devemos usar um ciclo while, pois um
        // wait() pode acordar threads sem ninguem o mandar!!!!!
        // Caso nao tivesse esse bug, poderiamos usar um if!!
        while(this.threadsNum<this.N && this.podeEntrar){
            // Usado para DEBUG
            System.out.println("Thread Espera Numero: " + this.threadsNum);
            // THEADS ESPERAM QUE TENHA ATINGIDO N THREADS
            wait();
        }

        // Usado para DEBUG
        System.out.println("Thread Liberta Numero: " + this.threadsNum);

        // THREAD QUE SAI NOTIFICA AS RESTANTES
        if(this.threadsNum==this.N) {
            notifyAll();
        }

        // Decrementa o contador de threads que sai da barreira
        this.threadsNum--;

        // Se o threadsNum=0 pode-se entrar novamente na barreira
        if(this.threadsNum==0){
            this.podeEntrar=true;
            notifyAll();
        }

    }

}
