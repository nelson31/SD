/**
 * Classe que serve para lancar excecao caso nao exista o utilizador
 * com um dado user
 *
 * @author nelson
 */

public class UtilizadorInvalido extends Exception {

    /**
     * Construtor por omissao
     */
    public UtilizadorInvalido(){
        super();
    }

    /**
     * Construtor parametrizado
     * @param e
     */
    public UtilizadorInvalido(String e){
        super(e);
    }
}
