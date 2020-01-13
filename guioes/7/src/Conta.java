import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Conta {

    // Dinheiro na conta
    private double valor;
    // ID da conta
    private int id;
    // lock
    private ReentrantLock lockConta= new ReentrantLock();
    // Lista de movimentos
    private List<Movimento> movimentos;


    public Conta(int id, double saldo){

        this.valor=saldo;
        this.id = id;
        this.movimentos = new ArrayList<>();
    }


    public void depositar(double valor){

        this.valor+=valor;
    }


    public void levantar(double v) throws DinheiroInsuficiente{
        double aux = this.valor-v;
        if(aux>=0) {
            this.valor -= v;
        }
        else throw new DinheiroInsuficiente();

    }

    public double consultar(){
        return this.valor;
    }


    public void lock(){
        this.lockConta.lock();
    }

    public void unlock(){
        this.lockConta.unlock();
    }

    public List<Movimento> getMovimentos(){

        // Devo delvover o resultado assim por causa da concorrencia
        return new ArrayList<Movimento>(this.movimentos);
    }

    public void addMovimento(Movimento mov){
        this.movimentos.add(mov);
    }

}
