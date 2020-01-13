package ex3;

/**
 * BoundedBuffer feito com o uso das primitivas syncronized,
 * notyfyAll() e wait()
 *
 * @author nelson
 */

public class BoundedBuffer {

    private int[] buffer;
    private int tamanhobuf;


    public BoundedBuffer(int n){
        this.buffer = new int[n];
        this.tamanhobuf=0;
    }

    /**
     * Metodo que coloca num buffer um valor passado como parametro
     * @param v
     * @throws InterruptedException
     */
    public void put(int v) throws InterruptedException{
        // Bloqueamos a secçao critica
        synchronized (this) {
            // Enquanto o buffer estiver cheio
            while (tamanhobuf == this.buffer.length) {
                // Espera que esteja vazio(Espera por um notify(ou notifyAll))
                wait();
            }
            // Adiciona o valor
            this.buffer[tamanhobuf++]=v;
            // Notifica todas as threads que foi adicionado um novo elemento
            notifyAll();
        }
    }

    /**
     * Metodo que retira um valor do array
     * @return
     * @throws InterruptedException
     */
    public int get() throws InterruptedException{
        int v;
        // Bloqueamos esta secçao critica
        synchronized (this) {
            // Enquanto o buffer estiver vazio
            while (tamanhobuf == 0) {
                // Espera que hajam elementos no array
                // Que seja enviado algum  notify(ou notifyAll)
                wait();
            }
            //Obtem o valor
            v=this.buffer[--tamanhobuf];
            // Notifica todas as threads bloqueadas que acabou de tirar um elemento
            notifyAll();
        }
        return v;
    }
}
