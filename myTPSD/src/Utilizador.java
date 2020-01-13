/**
 * Classe Utilizador que representa um utilizador do sistema
 *
 * @author nelson
 */

public class Utilizador {

    /** Nome do utilizador */
    private String username;
    /** Palavra passe*/
    private String password;
    /** Variavel que diz se o utilizador iniciou sessao*/
    private boolean ativo;

    /**
     * Construtor parametrizado de Utilizador
     * @param user
     * @param pass
     */
    public Utilizador(String user,String pass){
        this.username=user;
        this.password=pass;
        this.ativo=false;
    }


    /**
     * Metodo usado para a autenticacao do utilizador
     * @param user
     * @param pass
     * @return
     */
    public boolean login(String user,String pass) throws PasswordIncorreta{
        if (!(this.username.equals(user) && this.password.equals(pass)))
            throw new PasswordIncorreta();
        else {
            this.ativo=true;
            return true;
        }
    }

    /**
     * Metodo que serve para desativar um utilizador
     */
    public void logout(){
        this.ativo=false;
    }


}
