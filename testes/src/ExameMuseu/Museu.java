package ExameMuseu;

/**
 * Considere um sistema de organização de visitas guiadas a um museu.
 * Cada visitante tem que esperar até haver um guia e um grupo de 10
 * a 15 visitantes prontos para a visita poder começar. Cada visita
 * pode ser guiada numa de duas línguas, português ou inglês. Há visitantes
 * que falam apenas uma das línguas e esperam até haver um grupo correspondente.
 * Outros são poliglotas e podem juntar-se a qualquer grupo. Todos os guias
 * são poliglotas e podem como tal guiar qualquer grupo.
 */

public interface Museu {

        void enterPT();

        void enterEN();

        void enterPoly();

        void enterGuide();
}
