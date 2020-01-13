/**
 *  Ideia Base do problema:
 *    - shuttle circula entre terminais (com passageiros);
 *    - Controlador permite aos passageiros:
 *        - requisitar viagens
 *        - saber quando pode entrar
 *        - saber quando pode sair
 *
 *  Extras:
 *    - Capacidade limitada (max 30);
 *    - pode partir com minimo de 10 se não houver mais ninguem à espera;
 *    - Se houver mais passageiros do que lugares, estes entram por ordem
 * de requisicao;
 *
 */

public interface Controlador {

    void requisita_viagem(int origem, int destino);

    void espera(int destino);
}
