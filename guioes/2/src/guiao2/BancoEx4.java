package guiao2;

public class BancoEx4 {

    private Conta[] conta;


    public BancoEx4(int n){
        this.conta = new Conta[n];
        /** Podia ser assim*/
        for(int i=0;i<n;i++)
            this.conta[i]=new Conta();
    }

    public double consultar(int c){
        synchronized (this.conta[c]){
            return this.conta[c].consultar();
        }
    }

    public void depositar(int c, double valor){
        synchronized (this.conta[c]){
            this.conta[c].depositar(valor);
        }
    }

    public void levantar(int c, double valor){
        synchronized (this.conta[c]){
            this.conta[c].levantar(valor);
        }
    }

    /**
     * Tranfere dinheiro da conta c1 para c2
     * @param c1 origem
     * @param c2 destino
     * @param valor
     */
    public void transferir(int c1, int c2, double valor){

        /**
         * Tive de fazer assim por causa dos bloqueios para obrigar a que as threads
         * começem por bloquear o mesma conta
         */
        if(c1>c2) {
            synchronized (this.conta[c2]) {
                synchronized (this.conta[c1]) {
                    this.conta[c1].levantar(valor);
                    this.conta[c2].depositar(valor);
                }
            }
        } else {
            synchronized (this.conta[c1]) {
                synchronized (this.conta[c2]) {
                    this.conta[c1].levantar(valor);
                    this.conta[c2].depositar(valor);
                }
            }
        }
    }
}
