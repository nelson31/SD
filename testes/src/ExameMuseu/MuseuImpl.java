package ExameMuseu;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Versao inicial INCOMPLETA
 */

public class MuseuImpl implements Museu {

    // Variavel que guarda a quantidade de visitantes que estao no grupo
    // em que se fala portugues
    private int falaPT = 0;
    // Variavel que guarda a quantidade de visitantes que estao
    // no grupo dos que falam ingles
    private int falaEN = 0;
    // Variavel que guarda os grupos que ja existem(Um grupo portugues é
    // identificado com um 1, um ingles com um 2)
    private Queue<Integer> grupos = new ConcurrentLinkedQueue<>();
    // Variavel que guarda o numero de visitantes que estao a espera
    // para o grupo PT
    private int esperaPT = 0;
    // Variavel que guarda o numero de visitantes que estao a espera
    // para o grupo EN
    private int esperaEN = 0;
    // Variavel que guarda o numero de visitantes que estao a espera
    // para o grupo PT ou EN
    private int esperaPoly = 0;


    private ReentrantLock l = new ReentrantLock(true);
    // Variavel de condicao que serve para os visitantes portugueses
    // esperarem por entrar num grupo
    private Condition podeEntrarPT = l.newCondition();
    // Variavel de condicao que serve para os visitantes ingleses
    // esperarem por entrar num grupo
    private Condition podeEntrarEN = l.newCondition();
    // Variavel de condicao que serve para os visitantes poliglotas
    // esperarem por entrar num grupo
    private Condition podeEntrarPoly = l.newCondition();
    // Variavel de condicao que serve para o guia, no caso de nao ter
    // pessoas á espera, esperar
    private Condition podeComecar = l.newCondition();
    // Variavel de condicao que serve para o guia, no caso de nao ter
    // um grupo minimo de visitantes a iniciar a visita, esperar
    private Condition podeIniciarVisita = l.newCondition();


    /**
     * Metodo quie serve para avisar que entrou um visitante
     * que so sabe falar portugues
     */
    @Override
    public void enterPT() {

        try {
            l.lock();

            System.out.println("Mais um PT");

            this.esperaPT++;

            // Avisa caso hajam guias que se encontra mais um a espera
            this.podeComecar.signalAll();

            // Se nao existir um grupo em que se fale PT
            // ou se esse grupo tem mais de 15 elementos
            while (!grupos.contains(1) || falaPT>=15 ){
                podeEntrarPT.await();
            }

            this.falaPT++;

            this.esperaPT--;

            this.podeIniciarVisita.signalAll();

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            l.unlock();
        }
    }

    /**
     * Metodo que serve para avisar que entrou um visitante
     * que so sabe falar ingles
     */
    @Override
    public void enterEN() {

        try {
            l.lock();

            System.out.println("Mais um EN");

            this.esperaEN++;

            // Avisa caso hajam guias que se encontra mais um a espera
            this.podeComecar.signalAll();

            // Se nao existir um grupo em que se fale EN
            // ou se esse grupo tem mais de 15 elementos
            while (!grupos.contains(2) || falaEN>=15){
                podeEntrarEN.await();
            }

            this.falaEN++;

            this.esperaEN--;

            this.podeIniciarVisita.signalAll();

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

            System.out.println("Mais um poly");

            this.esperaPoly++;

            // Avisa caso hajam guias que se encontra mais um a espera
            this.podeComecar.signalAll();

            // Se nao existir um grupo em que se fale ingles e
            // em que se fale PT, ou entao o grupo PT esta cheio
            // ou o ingles esta cheio, espera
            while((!grupos.contains(1) && !grupos.contains(2)) || falaPT==15 || falaEN==15){
                podeEntrarPoly.await();
            }

            if(this.grupos.peek()!=null && grupos.peek()==2){
                this.falaEN++;
            } else{
                this.falaPT++;
            }

            this.esperaPoly--;

            this.podeIniciarVisita.signalAll();

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

            System.out.println("Mais um guia");

            // Enquanto nao existirem pessoas a espera para a visita
            while(this.esperaPT==0 && this.esperaEN==0 && this.esperaPoly==0){
                podeComecar.await();
            }

            // Ver quais as pessoas que se encontram a espera
            if(this.esperaPT>0){
                this.grupos.add(1);
                this.podeEntrarPT.signalAll();
            } else if(this.esperaEN>0) {
                this.grupos.add(2);
                this.podeEntrarEN.signalAll();
            } else if(this.esperaPoly>0){
                if(this.grupos.peek()!=null && this.grupos.peek()==1){
                    this.grupos.add(2);
                } else{
                    this.grupos.add(1);
                }
                this.podeEntrarPoly.signalAll();
            }

            System.out.println("Pronto a iniciar uma visita!!");

            while(this.falaPT<10 && this.falaEN<10){
                podeIniciarVisita.await();
            }

            int g = this.grupos.remove();

            //DEBUG
            System.out.println(this.grupos.toString());

            if(g==1){
                this.falaPT=0;
                System.out.println("Visita em Portugues realizada!!");
            } else{
                this.falaEN=0;
                System.out.println("Visita em Ingles realizada!!");
            }

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            l.unlock();
        }
    }
}
