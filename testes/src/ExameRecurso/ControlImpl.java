package ExameRecurso;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * IMplementacao do control de barcos e carros para entrar numa secccao critica
 *
 * Resolucao do exame de recurso (2019)
 * @author nelson v.(2.0) 09/01/2020
 */

public class ControlImpl implements Control {


    // Numero de carros que estao em cima da ponte
    private int numCarros = 0;
    // Booleano que é true se a ponte estiver a ser atravessada por um Barco
    private boolean atravessaBarco = false;
    // Variavel que guarda o numero de barcos que estao à espera para entrar
    private int esperaBarcos = 0;
    // Variavel que guarda o numero de carros que estao à espera para entrar
    private int esperaCarros = 0;
    // Variavel usada para garantir justiça(numero maximo de barcos os
    // carros que podem seguir seguidos)
    private static int MAXSEGUIDOS = 2;
    //Variavel usada para verificar quantos carros entraram seguidos
    private int carrosSeguidos=0;
    //Variavel usada para verificar quantos barcos entraram seguidos
    private int barcosSeguidos=0;


    private ReentrantLock l = new ReentrantLock(true);
    // Variavel de condiçao que serve para caso um barco esteja a atravessar,
    // o carro espera
    private Condition esperaCarro = l.newCondition();
    // Variavel que serve para caso existam carros a atravessar
    // a ponte, o barco tem de esperar
    private Condition esperaBarco = l.newCondition();


    /**
     * Construtor
     */
    public ControlImpl(){

    }

    /**
     * O método entra_carro deve bloquear enquanto houver algum
     * barco a passar ou a pedir permissão pelo uso da ponte, sendo
     * o retorno do método indicação que o carro pode entrar na ponte.
     */
    @Override
    public void entra_carro() {

        try {
            l.lock();

            // Mais um carro a espera
            this.esperaCarros++;

            // Espero se estiver um barco a atravessar OU se existirem barcos a espera
            // e ja passaram 2 carros seguidos
            while (this.atravessaBarco || (this.esperaBarcos>0 && this.carrosSeguidos>=MAXSEGUIDOS)) {
                esperaCarro.await();
            }

            // Menos um carros a espera
            this.esperaCarros--;

            // Mais um barco que entrou
            this.numCarros++;

            // Mais um carros que entrou seguido
            this.carrosSeguidos++;

            // Coloca o numero de barcos que entram seguidos a zero pois
            // acaba de entrar um carro
            this.barcosSeguidos=0;

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            l.unlock();
        }
    }

    @Override
    public void sai_carro() {

        try {
            l.lock();

            this.numCarros--;

            if(this.esperaBarcos>0 && this.numCarros==0){
                esperaBarco.signal();
            }

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            l.unlock();
        }


    }

    @Override
    public void entra_barco() {

        try {
            l.lock();

            // Menos um barco a espera
            this.esperaBarcos++;

            // Enquanto existirem carros na seccao critica OU existirem carros
            // a espera e entraram mais de MAXSEGUIDOS barcos OU estiver um barco
            // a atravessar, ESPERA
            while(this.numCarros>0 ||(this.esperaCarros>0 && this.barcosSeguidos>=MAXSEGUIDOS) || this.atravessaBarco){
                esperaBarco.await();
            }

            // Menos um barco a espera
            this.esperaBarcos--;

            // Acabou de entrar um barco
            this.atravessaBarco=true;

            // Entrou mais um barco seguido
            this.barcosSeguidos++;

            // Colocar a var. dos carrosseguidos a zero pk acabou de entrar um barco
            this.carrosSeguidos=0;

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            l.unlock();
        }

    }

    @Override
    public void sai_barco() {

        try{

            l.lock();

            this.atravessaBarco=false;

            if(this.esperaBarcos>0){
                this.esperaBarco.signal();
            }

            if(this.esperaCarros>0){
                this.esperaCarro.signalAll();
            }

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            l.unlock();
        }
    }
}
