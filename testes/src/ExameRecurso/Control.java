package ExameRecurso;


public interface Control {

    /**
     * O método entra_carro deve bloquear enquanto houver algum
     * barco a passar ou a pedir permissão pelo uso da ponte, sendo
     * o retorno do método indicação que o carro pode entrar na ponte.
     */
    void entra_carro();

    /**
     * Os métodos sai_carro e sai_barco indicam que cada um destes
     * tipos de transporte já abandonaram a ponte após efectuarem
     * a travessia.
     */
    void sai_carro();

    /**
     * Já o método entra_barco deve aguardar 5 minutos e tentar
     * obter permissão de entrada, apenas retornando quando
     * o acesso for possível, sendo que só é admitido um barco de cada vez.
     */
    void entra_barco();

    /**
     * Os métodos sai_carro e sai_barco indicam que cada um destes
     * tipos de transporte já abandonaram a ponte após efectuarem
     * a travessia.
     */
    void sai_barco();
}
