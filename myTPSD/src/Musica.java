import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Classe Musica
 *
 * @author nelson
 */

public class Musica {

    /** ID unico que identifica a musica*/
    private int id;
    /** Titulo da musica*/
    private String titulo;
    /** Interprete da musica*/
    private String interprete;
    /** Ano de realizacao da musica*/
    private int ano;
    /** Variavel que guarda um conjunto de etiquetas que
     * se relacionam com a musica*/
    private Set<String> etiquetas;
    /** Numero de vezes que foi descarregada*/
    private int numDescargas;

    /**
     * Construtor por omissao de Musica
     */
    public Musica(){
        this.id=0;
        this.titulo = "";
        this.interprete = "";
        this.ano = 0;
        this.etiquetas = new TreeSet<>();
        this.numDescargas=0;
    }

    /**
     * Construtor parametrizado de Musica
     * @param titulo
     * @param interprete
     * @param ano
     * @param etiquetas
     */
    public Musica(int id,String titulo,String interprete,int ano,Set<String> etiquetas){

        this.id=id;
        this.titulo=titulo;
        this.interprete = interprete;
        this.ano = ano;
        this.etiquetas = etiquetas;
        this.numDescargas=0;
    }

    // getters and setters

    /**
     * Metodo que devolve o id
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Metodo que atualiza o Id
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Metodo que nos devolve o valor do titulo
     * @return
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Metodo que nos atualiza o valor do titulo
     * @param titulo
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Metodo que nos devolve o valor do interprete
     * @return
     */
    public String getInterprete() {
        return interprete;
    }

    /**
     * Metodo que nos atualiza o valor do interprete
     * @param interprete
     */
    public void setInterprete(String interprete) {
        this.interprete = interprete;
    }

    /**
     * Metodo que nos devolve o valor do ano
     * @return
     */
    public int getAno() {
        return ano;
    }

    /**
     * Metodo que nos atualiza o valor do ano
     * @param ano
     */
    public void setAno(int ano) {
        this.ano = ano;
    }

    /**
     * Metodo que nos devolve o valor das etiquetas
     * @return
     */
    public Set<String> getEtiquetas() {
        return etiquetas;
    }

    /**
     * Metodo que nos atualiza o valor das etiquetas
     * @param etiquetas
     */
    public void setEtiquetas(Set<String> etiquetas) {
        this.etiquetas = etiquetas;
    }

    /**
     * Metodo que nos adiciona uma etiqueta
     * @param et
     */
    public void addEtiqueta(String et){
        this.etiquetas.add(et);
    }

    /**
     * Metodo que dado uma etiqueta noos diz se existe nas etiquetas
     * @param ets
     * @return
     */
    public boolean containsEtiquetas(String[] ets){
        boolean b=false;
        for(String et:ets){
            if(this.etiquetas.contains(et)){
                b=true;
            }
        }
        return b;
    }

    /**
     * REVER O TOSTRING para o tornar melhor
     * @return
     */
    @Override
    public String toString() {
        return "Musica{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", interprete='" + interprete + '\'' +
                ", ano=" + ano +
                ", etiquetas=" + etiquetas +
                ", numDescargas=" + numDescargas +
                '}';
    }
}
