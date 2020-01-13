package ExameRecurso;

import java.io.*;
import java.net.Socket;

public class Worker implements Runnable {

    // Variavel que ira guardar o canal de comunicacao com o cliente
    private Socket socket;
    // Variavel que ira conter o controlador
    private Control control;

    // Variavel que fara a conversao do que sera necessario escrever para o socket
    private PrintWriter out;
    // Variavel que sera responsavel por ler do socket
    private BufferedReader in;

    /**
     * Construtor parametrizado do Worker
     * @param s
     * @param c
     */
    public Worker(Socket s, Control c){
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

                    case "Barco":{
                        this.control.entra_barco();
                        System.out.println("Atravessando a ponte...");
                        try {
                            // Esperar os minutos pretendidos para atravessar
                            Thread.sleep(Integer.parseInt(args[1]));
                        } catch (InterruptedException e){
                            e.printStackTrace();
                        }
                        System.out.println("Já atravessei!!");
                        // Depois espera para chegar ao destino
                        this.control.sai_barco();
                        // Fim da viagem
                        this.out.println("O Barco já saiu!");
                        this.out.flush();
                        break;
                    }
                    case "Carro": {
                        this.control.entra_carro();
                        try {
                            // Esperar os minutos pretendidos para atravessar
                            Thread.sleep(Integer.parseInt(args[1]));
                        } catch (InterruptedException e){
                            e.printStackTrace();
                        }
                        // Depois espera para chegar ao destino
                        this.control.sai_carro();
                        // Fim da viagem
                        this.out.println("O Carro já saiu!");
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
