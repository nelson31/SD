import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Versão 4:
 *      - Passageiros entram e saem no terminal certo
 *      - Shuttle com capacidade limitada(30 passageiros)
 *      - Shuttle parte quando tiverem saido todos os passageiros que
 *      tem como destino esse terminal
 *      - ...
 */

public class ControladorImpl4 implements Controlador {

    /** Saber onde esta o shuttle (o negativo significa que esta em movimento)*/
    private int terminal = -1;
    /** Variavel que guarda o numero de passageiros que vao para um
     * determinado destino (tenho 5 destinos)*/
    private int[] Pdestino = new int[5];
    /** Variavel que guarda a ocupacao total do shuttle*/
    private int ocupacao = 0;
    /** Usado para ver se ainda existem passageiros a espera num dado terminal*/
    private int[] Pespera = new int[5];
    /** Variavel que guarda os bilhetes emitidos*/
    private int[] BEmitidos = {1,1,1,1,1};
    /** Variavel que guarda os bilhetes que ja foram usados */
    private int[] BUsados = new int[5];

    private ReentrantLock lock = new ReentrantLock();
    // Variavel que serve para acordar os clientes que pretendem entrar no shuttle
    private Condition eEntrar = lock.newCondition();
    // Variavel que serve para acordar os clientes que pretendem sair do shuttle
    private Condition eSair = lock.newCondition();
    // Variavel que serve para determinar quando é que o shuttle pode partir
    // Simula os passageiros a sair
    private Condition passSair = lock.newCondition();
    // Variavel que serve para verificar se um dado passageiro pode entrar no shuttle
    private Condition podeEntrar = lock.newCondition();


    /**
     * Metodo que serve para requisitar uma viagem
     * @param origem
     * @param destino
     */
    public void requisita_viagem(int origem, int destino) {

        try {
            this.lock.lock();

            // Mais um passageiro á espera
            Pespera[origem-1]++;

            // Numero de bilhete atribuido a um dado passageiro
            int meubilhete = BEmitidos[origem-1]++;

            System.out.println("Numero de pessoas à espera na origem: " + Pespera[origem-1]);

            // Enquanto o shuttle nao estiver no terminal pretendido
            // pelo passaGEIRO PARA ENTRAR
            while (origem != this.terminal && meubilhete>(BUsados[origem-1]+30-ocupacao)) {
                eEntrar.await();
            }

            // Aumenta o numero de bilhetes que foram usados
            BUsados[origem-1]++;

            // Mais um a ocupar o shuttle
            this.ocupacao++;

            // Mais um passageiro para um certo destino
            Pdestino[destino-1]++;

            // Menos um passageiro a espera de entrar
            Pespera[origem-1]--;

            // Podem entrar passageiros
            podeEntrar.signalAll();

            lock.unlock();

        } catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * ESpera que o shuttle chegue ao destino
     * @param destino
     */
    public void espera(int destino) {

        try {
            lock.lock();

            // Enquanto o passageiro nao chegar ao destino!
            while (destino != this.terminal) {
                eSair.await();
            }

            // Sai mais um passageiro num dado destino
            Pdestino[destino-1]--;
            // Menos passageiros no shuttle
            this.ocupacao--;
            // Acordo o 'shuttle' para poder sair
            passSair.signalAll();

            lock.unlock();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     *  Serve para verificar quando é que o shuttle pode sair
     *  shuttle parte quando tiverem saido todos os passageiros para esse de destino
     */

    public void parte(){
        try {
            lock.lock();

            // Com isto digo que o shuttle esta parado
            terminal=-terminal;
            // Os que querem sair num dado destino ja podem
            eSair.signalAll();
            // Os que querem entrar num dado terminal ja podem
            eEntrar.signalAll();

            // Enquanto os passageiros nao tiverem saido todos
            while(Pdestino[this.terminal-1]>0){
                passSair.await();
            }

            // Enquanto os passageiros poderem entrar no shuttle
            while(!(this.ocupacao==30 || this.ocupacao>=10 && Pespera[terminal-1]==0)){
                podeEntrar.await();
            }

            // Simula a saida do shuttle para uma viagem
            if(this.terminal==5){
                this.terminal=-1;
            } else {
                this.terminal= -1*(this.terminal + 1);
            }
            lock.unlock();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
