public class Movimento {

    /**
     * Variavel que guarda o identificador do cliente que
     * realizou o movimento
     */
    private int id;

    /** Variavel que tem uma descricao relativa ao movimento */
    private String descricao;

    /** Montante resultante do movimento*/
    private double montante_resultante;

    /**
     * Construtor parametrizado
     * @param id
     * @param descricao
     * @param montante_resultante
     */
    public Movimento(int id, String descricao, double montante_resultante){
        this.id = id;
        this.descricao = descricao;
        this.montante_resultante = montante_resultante;
    }

    public int getId(){
        return this.id;
    }

    public String getDescricao(){
        return this.descricao;
    }

    public double getMontante_resultante(){
        return this.montante_resultante;
    }

    public void setId(int id){
        this.id=id;
    }

    public void setDescricao(String descricao){
        this.descricao=descricao;
    }

    public void setMontante_resultante(double montante){
        this.montante_resultante = montante;
    }

    @Override
    public String toString() {
        return
                ("Operacao na conta " + this.id + ": " + this.descricao + ". Saldo resultante: " + this.montante_resultante);
    }
}
