/**
 * Classe que serve para lancar excecao
 *
 * @author nelson
 */

public class ContaJaExistente extends Exception {

    /**
     * Construtor por omissao
     */
    public ContaJaExistente(){
        super();
    }

    /**
     * Construtor parametrizado
     * @param e
     */
    public ContaJaExistente(String e){
        super(e);
    }
}
