/**
 * Classe que serve para lancar excecao
 *
 * @author nelson
 */

public class PasswordIncorreta extends Exception {

    /**
     * Construtor por omissao
     */
    public PasswordIncorreta(){
        super();
    }

    /**
     * Construtor parametrizado
     * @param e
     */
    public PasswordIncorreta(String e){
        super(e);
    }
}