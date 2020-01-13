package guiao3;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class Banco {

    // Map de contas registadas no Banco
    private Map<Integer,Conta> conta;
    // ultimo id para aquando da criacao de conta
    private int lastId;
    // lock
    private ReentrantLock lockBanco = new ReentrantLock();


    /**
     * Construtor por omissao de Banco
     */
    public Banco(){

        this.conta = new HashMap<>();
        this.lastId=0;
    }

    /**
     * Metodo que serve para criar uma conta
     * @param saldo
     * @return
     */
    public int criarConta(double saldo){

        this.lockBanco.lock();
        int id = lastId++;
        Conta c =  new Conta(id,saldo);
        this.conta.put(id,c);
        this.lockBanco.unlock();

        return id;

    }

    /**
     * Fechar uma conta
     * @param id
     * @return
     * @throws ContaInvalida
     */

    public double fecharConta(int id) throws ContaInvalida{
        double valor=0;

        //Fecha o  banco
        this.lockBanco.lock();
        if(this.conta.containsKey(id)) {
            Conta c = this.conta.get(id);

            //Fecha a conta
            c.lock();
            valor = c.consultar();
            this.conta.remove(id);
            this.lockBanco.unlock();
            c.unlock();
        } else {
            this.lockBanco.unlock();
            throw new ContaInvalida();
        }
        return valor;
    }


    /**
     * Consultar o saldo do conjunto das contas recebidas como argumento
     * @param contas
     * @return
     */
    public double consultarTotal(int contas[]){
        double sum=0;

        // Ordenou-se por uma questao protocolar
        Arrays.sort(contas);

        this.lockBanco.lock();
        for(Integer i:contas){
            // Se a conta existir fazemos o lock do banco
            if(this.conta.containsKey(i)) {
                Conta c = this.conta.get(i);
                c.lock();
            }
        }
        // Tiramos o lock do banco
        this.lockBanco.unlock();

        for(Integer i:contas){

            if(this.conta.containsKey(i)){
                Conta c = this.conta.get(i);
                sum += c.consultar();
                c.unlock();
            }
        }
        return sum;
    }

    /**
     * Metodo que serve para consultar o saldo de uma conta
     * @param c
     * @return
     * @throws ContaInvalida
     */
    public double consultar(int c) throws ContaInvalida{
        double valor;
        this.lockBanco.lock();
        if(this.conta.containsKey(c)){
            Conta cont = this.conta.get(c);
            cont.lock();
            this.lockBanco.unlock();
            valor=cont.consultar();
            cont.unlock();
            //this.lockBanco.unlock();
        }
        else {
            this.lockBanco.unlock();
            throw new ContaInvalida();
        }
        return valor;
    }

    /**
     * Metodo usado para depositar dinheiro numa certa conta
     * @param c
     * @param valor
     */
    public void depositar(int c, double valor) throws ContaInvalida{

        this.lockBanco.lock();

        if(this.conta.containsKey(c)) {
            this.conta.get(c).lock();

            this.lockBanco.unlock();

            this.conta.get(c).depositar(valor);

            this.conta.get(c).unlock();
        } else {
            this.lockBanco.unlock();

            throw new ContaInvalida();
        }

    }

    /**
     * Metodo para levantar um certo dinheiro de uma conta
     * @param c
     * @param valor
     * @throws DinheiroInsuficiente
     * @throws ContaInvalida
     */
    public void levantar(int c, double valor) throws DinheiroInsuficiente,ContaInvalida{

        this.lockBanco.lock();
        if(this.conta.containsKey(c)) {

            this.conta.get(c).lock();

            this.lockBanco.unlock();

            this.conta.get(c).levantar(valor);

            this.conta.get(c).unlock();
        } else{
            this.lockBanco.unlock();
            throw new ContaInvalida();
        }

    }

    /**
     * Tranfere dinheiro da conta c1 para c2
     * @param c1 origem
     * @param c2 destino
     * @param valor
     */
    public void transferir(int c1, int c2, double valor)
            throws ContaInvalida,DinheiroInsuficiente
    {

        /**
         * Tive de fazer assim por causa dos bloqueios para obrigar a que as threads
         * começem por bloquear o mesma conta
         */
        int maior=Integer.max(c1,c2);
        int menor=Integer.min(c1,c2);

        this.lockBanco.lock();
        if(this.conta.containsKey(menor)) {
            Conta conta2 = this.conta.get(menor);
            conta2.lock();
            if(this.conta.containsKey(maior)) {
                Conta conta1 = this.conta.get(maior);
                conta1.lock();
                //Acho que ja se pode fazer unlock pk
                // ja temos as duas contas bloqueadas
                this.lockBanco.unlock();
                //Para evitar deadlocks
                try {
                    this.conta.get(c1).levantar(valor);
                    this.conta.get(c2).depositar(valor);
                } finally {
                    conta2.unlock();
                    conta1.unlock();
                }
            } else {
                this.lockBanco.unlock();
                conta2.unlock();
                throw new ContaInvalida();
            }
        } else {
            this.lockBanco.unlock();
            throw new ContaInvalida();
        }

    }
}

