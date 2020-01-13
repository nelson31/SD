import java.util.*;

/**
 * Classe principal que gere as trocas de ficheiros
 *
 * @author nelson
 */

public class SoudCloud {

    /** Lista de utilizadores que estao a usar o sistema*/
    private Map<String, Utilizador> utilizadores;
    /** Lista de musicas que estao no sistema*/
    private Map<Integer,Musica> musicas;
    /** Variavel que guarda o ultimo ID inserido numa musica*/
    private int lastID;

    /**
     * Construtor por omissao
     */
    public SoudCloud(){
        this.utilizadores = new HashMap<>();
        this.musicas = new HashMap<>();
        this.lastID=0;
    }

    /**
     * Metodo que serve para autenticar um certo utilizador
     * @param usr
     * @param pass
     * @return
     * @throws UtilizadorInvalido
     */
    public boolean login(String usr,String pass) throws UtilizadorInvalido,PasswordIncorreta{
        boolean b=false;
        if(this.utilizadores.containsKey(usr)) {
            b=this.utilizadores.get(usr).login(usr, pass);
        } else throw new UtilizadorInvalido();

        return b;
    }


    /**
     * Metodo que serve para o logout de um certo utilizador do sistema
     * @param nome
     */
    public void logout(String nome){
        this.utilizadores.get(nome).logout();
    }

    /**
     * Metodo que serve para adicionar uma musica ao sistema
     * @param titulo
     * @param inter
     * @param ano
     * @param etqs
     */
    public int addMusica(
            String titulo, String inter, int ano, String[] etqs){

        Set<String> etiquetas = new TreeSet<>();
        for(String e:etqs){
            etiquetas.add(e);
        }
        etiquetas.add(titulo);
        etiquetas.add(inter);
        Musica musica = new Musica(++this.lastID,titulo,inter,ano,etiquetas);
        this.musicas.put(this.lastID,musica);
        return this.lastID;
    }

    /**
     * Metodo que adiciona um utilizador ao sistema
     * @param username
     * @param pass
     */
    public void addUtilizador(String username, String pass) throws ContaJaExistente{

        if(!this.utilizadores.containsKey(username)) {
            Utilizador u = new Utilizador(username, pass);

            this.utilizadores.put(username, u);
        } else throw new ContaJaExistente();
    }

    /**
     * Metodo que dado um conjunto de etiquetas nos da todas as musicas
     * que se relacionam com elas
     * @param etqs
     * @return
     */
    public Set<Musica> searchByEtiquetas(String[] etqs){
        Set<Musica> musicas = new HashSet<>();

        for(Musica m:this.musicas.values()){
            if(m.containsEtiquetas(etqs))
                musicas.add(m);
        }

        return musicas;
    }


}
