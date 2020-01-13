package ExameMuseu;

import java.io.*;
import java.net.Socket;

public class Worker implements Runnable {

    // Variavel que ira guardar o canal de comunicacao com o cliente
    private Socket socket;
    // Variavel que ira conter o controlador necessario para realizar as
    // operacoes sobre o museu
    private Museu museu;

    // Variavel que fara a conversao do que sera necessario escrever para o socket
    private PrintWriter out;
    // Variavel que sera responsavel por ler do socket
    private BufferedReader in;

    /**
     * Construtor parametrizado do Worker
     * @param s
     * @param m
     */
    public Worker(Socket s, Museu m){
        this.museu = m;
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

                    case "enterEN":{
                        this.museu.enterEN();
                        this.out.println("Entrou mais um ingles!");
                        this.out.flush();
                        break;
                    }
                    case "enterPT":{
                        this.museu.enterPT();
                        this.out.println("Entrou mais um portugues!");
                        this.out.flush();
                        break;
                    }
                    case "enterPoly":{
                        this.museu.enterPoly();
                        this.out.println("Entrou mais um polyglota!");
                        this.out.flush();
                        break;
                    }
                    case "enterGuide":{
                        this.museu.enterGuide();
                        this.out.println("Entrou mais um guia!");
                        this.out.flush();
                        break;
                    }
                    default:
                        this.out.println("Insira uma operacao valida!");
                        this.out.flush();
                }

            }
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
