import java.io.*;
import java.net.Socket;

public class Worker implements Runnable {

    // Variavel que ira guardar o canal de comunicacao com o cliente
    private Socket socket;
    // Variavel que ira conter o controlador necessario para realizar as
    // operacoes sobre o shuttle
    private ControladorImpl4 control;

    // Variavel que fara a conversao do que sera necessario escrever para o socket
    private PrintWriter out;
    // Variavel que sera responsavel por ler do socket
    private BufferedReader in;

    /**
     * Construtor parametrizado do Worker
     * @param s
     * @param c
     */
    public Worker(Socket s, ControladorImpl4 c){
        this.control = c;
        this.socket=s;
        try {
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e){
            System.out.println("Problemas na coneccao!!!");
            e.printStackTrace();
        }
    }

    /**
     * Dara inicio a execucao do Worker responsavel por responder
     * aos pedidos do cliente
     */
    @Override
    public void run() {

        String line;

        try {

            while ((line = this.in.readLine())!=null) {

                String[] args = line.split(" ");

                switch (args[0]) {

                    case "requisita_viagem":{
                        this.control.requisita_viagem(
                                Integer.parseInt(args[1]), Integer.parseInt(args[2]));
                        // Depois espera para chegar ao destino
                        this.control.espera(Integer.parseInt(args[2]));
                        // Fim da viagem
                        this.out.println("Chegou ao destino!");
                        this.out.flush();
                        break;
                    }
                    case "parte": {
                        this.control.parte();
                        // Shuttle partiu para um novo destino
                        this.out.println("Partiu para um novo destino!");
                        this.out.flush();
                        break;
                    } default:
                        this.out.println("Insira uma operacao valida!");
                        this.out.flush();
                }

            }
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
