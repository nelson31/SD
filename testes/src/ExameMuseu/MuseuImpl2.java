package ExameMuseu;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * Melhor versao de implementacao
 */

public class MuseuImpl2 implements Museu {


    // Variavel que serve para identificar se um dado guia é responsavel
    // por um grupo portugues(existe já um guia para um grupo PT)
    private boolean guiaPT=false;
    // Variavel que serve para identificar se um dado guia é responsavel
    // por um grupo ingles(existe já um guia para um grupo PT)
    private boolean guiaEN=false;
    // Variavel que guarda o numero de visitantes PT que estao num certo
    // grupo de Portugueses
    private int numPT = 0;
    // Variavel que guarda o numero de visitantes PT que estao num certo
    // grupo de Portugueses
    private int numEN = 0;
    // Variavel que nos da o numero de ingleses que se encontram à espera
    // para entrar
    private int esperaEN = 0;
    // Variavel que nos da o numero de portugueses que se encontram à espera
    // para entrar
    private int esperaPT = 0;



    private ReentrantLock l = new ReentrantLock(true);
    // Variavel de condicao que serve para verificar se um portugues pode
    // entrar num no museu
    private Condition podeEntrarPT = l.newCondition();
    // Variavel de condicao que serve para verificar se um ingles pode
    // entrar num no museu
    private Condition podeEntrarEN = l.newCondition();
    // Variavel de condicao que serve para verificar se um poliglota pode
    // entrar num no museu
    private Condition podeEntrarPoly = l.newCondition();
    // Variavel de condicao que serve para verificar se um guia pode
    // atender um grupo ou nao
    private Condition podeGuiar = l.newCondition();
    // Variavel de condicao que serve para verificar se um guia ja pode
    // seguir viagem com o seu grupo
    private Condition grupoFormado = l.newCondition();


    /**
     * Metodo que serve para avisar que entrou um visitante
     * que so sabe falar portugues
     */
    @Override
    public void enterPT() {

        try {
            l.lock();

            this.esperaPT++;

            while(!guiaPT || numPT>=15){
                podeEntrarPT.await();
            }

            this.numPT++;

            this.esperaPT--;

            this.grupoFormado.signalAll();

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            l.unlock();
        }

    }

    /**
     * Metodo quie serve para avisar que entrou um visitante
     * que so sabe falar ingles
     */
    @Override
    public void enterEN() {

        try {
            l.lock();

            this.esperaEN++;

            while(!guiaEN || numEN>=15){
                podeEntrarEN.await();
            }

            this.numEN++;

            this.esperaEN--;

            this.grupoFormado.signalAll();

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            l.unlock();
        }

    }

    /**
     * Metodo quie serve para avisar que entrou um visitante
     * que sabe falar todas as linguas
     */
    @Override
    public void enterPoly() {

        try{
            l.lock();

            this.esperaEN++;
            this.esperaPT++;

            // Se nao existir um guia portugues ou o numero no grupo pt é
            // maior que 15 e Se nao existir um guia ingles ou o numero no
            // grupo en é maior que 15
            while((!guiaPT || numPT>=15) && (!guiaEN || numEN>=15)){
                podeEntrarPoly.await();
            }

            // SE existir um grupo de portugueses
            if(guiaPT && numPT<15) {
                this.numPT++;
            } else if(guiaEN && numEN<15){
                this.numEN++;
            }

            this.esperaEN--;
            this.esperaPT--;

            this.grupoFormado.signalAll();

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            l.unlock();
        }
    }

    /**
     * Metodo que serve para avisar que entrou um guia
     */
    @Override
    public void enterGuide() {

        try{

            l.lock();

            // So podem estar 2 guias ao mesmo tempo,
            // pois cada um pode atender tanto um grupo ingles
            // como portugues
            while(this.guiaPT && this.guiaEN){
                podeGuiar.await();
            }

            // Se estiverem portugueses á espera o guia atende um grupo
            // de portugueses
            if(this.esperaPT>0){
               this.guiaPT=true;
               this.podeEntrarPT.signalAll();
               this.podeEntrarPoly.signalAll();
            } else if(this.esperaEN>0){
                // Senao fica um guia ingles ativo
               this.guiaEN=true;
               this.podeEntrarEN.signalAll();
               this.podeEntrarPoly.signalAll();
            }

            while(this.numPT<10 && this.numEN<10){
                this.grupoFormado.await();
            }

            // Se o guia tiver um grupo de portugueses disponivel, atende-o
            // Senao atende o grupo de ingleses
            if(this.numPT>=10){
                this.numPT=0;
                this.guiaPT=false;
                this.podeEntrarPT.signalAll();
                this.podeEntrarPoly.signalAll();
                this.podeGuiar.signalAll();
            } else{
                this.numEN=0;
                this.guiaEN=false;
                this.podeEntrarEN.signalAll();
                this.podeEntrarPoly.signalAll();
                this.podeGuiar.signalAll();
            }

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            l.unlock();
        }

    }


}
