package ex2;

/**
 * Deu me isto!!!!
 * -> Débito máximo: 0.0058740601503759395
 * -> Numero de Produtores: 7
 */

public class Main2 {

    public static void main(String[] args){

        BoundedBuffer buf=new BoundedBuffer(10); // iniciar o buffer com 10 posicoes

        int tc = 500; // tempo de consumo em ms: (0.5 segundos)
        int tp=1000; // tempo de producao em ms(1 segundo)
        int total_ops=100; // no total, produzir 100 itens e consumir 100 itens
        int N = 10; // numero total de threads
        int P = 7; // numero de produtores
        int C = N-P; // numero de consumidores
        double maxDebito = 0;
        int maxProd = 0; // guarda o numero de produtores referente ao debito maximo observado

        // Ir testando todas as posssibilidades para ver qual a melhor opcao
        for(P=1;P<=9;P++) {
            C = N-P;

            System.out.println("Para um numero de produtores de: " + P + " e Consumidores: " + C);

            // Threads de Produtor
            Thread[] tsps = new Thread[P];

            for(int i=0;i<P;i++){
                int num;
                if(i==P-1) {
                    num = (total_ops / P) + (total_ops % P);
                } else {
                    num = total_ops / P;
                }
                Produtor p = new Produtor(buf,tp,num);
                tsps[i]= new Thread(p);
            }

            // Threads de Consumidor
            Thread[] tscs = new Thread[C];

            for(int i=0;i<C;i++){
                int num;
                if(i==C-1) {
                    num = (total_ops / C) + (total_ops % C);
                } else {
                    num = total_ops / C;
                }
                Consumidor c = new Consumidor(buf,tc,num);
                tscs[i]= new Thread(c);
            }

            long timeI = System.currentTimeMillis();

            try{
                for(int i=0;i<P;i++){
                    tsps[i].start();
                }

                for(int i=0;i<C;i++){
                    tscs[i].start();
                }

                for(int i=0;i<P;i++){
                    tsps[i].join();
                }

                for(int i=0;i<C;i++){
                    tscs[i].join();
                }

                long time = (System.currentTimeMillis()-timeI);

                double debito = (double) total_ops/time;

                System.out.println("Para a iteracao com um P=" + P + ", C=" + C + ", Tempo=" + time + " e um Debito=" + debito);

                if(maxDebito<debito){
                    maxDebito=debito;
                    maxProd=P;
                }

            } catch (InterruptedException e){
                System.out.println("Fim");
            }
        }

        System.out.println("Débito máximo: " + maxDebito);
        System.out.println("Numero de Produtores: " + maxProd);
    }
}
