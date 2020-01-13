package ex3;

public class Cliente implements Runnable{

    private BarreiraAula barreira;

    public Cliente(BarreiraAula barr){
        this.barreira = barr;
    }

    public void run() {
        try {
            this.barreira.esperar();
            this.barreira.esperar();

        }catch(InterruptedException e) {}

    }
}
