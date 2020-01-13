import java.util.Arrays;
import java.util.List;

public interface IBanco {

    /**
     * Metodo que serve para criar uma conta
     * @param saldo
     * @return
     */
    int criarConta(double saldo) throws BancoRemotoException;

    /**
     * Fechar uma conta
     * @param id
     * @return
     * @throws ContaInvalida
     */

    double fecharConta(int id) throws ContaInvalida,BancoRemotoException;


    /**
     * Consultar o saldo do conjunto das contas recebidas como argumento
     * @param contas
     * @return
     */
    double consultarTotal(int contas[]) throws BancoRemotoException;

    /**
     * Metodo que serve para consultar o saldo de uma conta
     * @param c
     * @return
     * @throws ContaInvalida
     */
    double consultar(int c) throws ContaInvalida,BancoRemotoException;

    /**
     * Metodo usado para depositar dinheiro numa certa conta
     * @param c
     * @param valor
     */
    void depositar(int c, double valor) throws ContaInvalida,BancoRemotoException;

    /**
     * Metodo para levantar um certo dinheiro de uma conta
     * @param c
     * @param valor
     * @throws DinheiroInsuficiente
     * @throws ContaInvalida
     */
    void levantar(int c, double valor)
            throws DinheiroInsuficiente,ContaInvalida,BancoRemotoException;

    /**
     * Tranfere dinheiro da conta c1 para c2
     * @param c1 origem
     * @param c2 destino
     * @param valor
     */
    void transferir(int c1, int c2, double valor)
            throws ContaInvalida,DinheiroInsuficiente,BancoRemotoException;

    /**
     * Metodo que dado um id nos retorna uma lista com os movimentos
     * totais realizados por um cliente com esse id
     * @param id
     * @return
     * @throws ContaInvalida
     */
    List<Movimento> movimentos (int id) throws ContaInvalida,BancoRemotoException;

}
