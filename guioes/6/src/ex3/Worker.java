package ex3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Classe criada para servir o servidor e poder
 * atender varios cliente s em simultaneo
 */

public class Worker implements Runnable {

    // Variavel que guarda o socket do cliente
    private Socket cCliente;

    private int numWorker;

    public Worker(Socket clSocket, int num){

        this.cCliente = clSocket;
        this.numWorker = num;
    }

    @Override
    public void run() {

        String line = "";

        System.out.println("Worker nº " + numWorker + " criado!!!");

        try{
            // Instancia criada para receber a informacao enviada pelo cliente
            BufferedReader in = new BufferedReader(new
                    InputStreamReader(this.cCliente.getInputStream()));

            // Variavel usada para enviar a resposta ao cliente
            PrintWriter out = new PrintWriter(
                    this.cCliente.getOutputStream());

            //System.out.println("Streams abertas con sucesso");

            // Le a informacao recebida do cliente
            line = in.readLine();

            // Enquanto houver coisas para ler do socket
            while (line != null) {
                // Escreve a informacao que se pretende enviar ao cliente
                out.println("[Servidor]: " + line);
                //out.write(line);
                //out.newLine();
                // Obriga a enviar a informacao ao cliente
                out.flush();
                // Le a informacao recebida do cliente
                line = in.readLine();
            }

            // Nao esquecer de fechar o socket do cliente
            this.cCliente.shutdownOutput();
            this.cCliente.shutdownInput();
            this.cCliente.close();

            System.out.println("Worker nº " + numWorker + " saiu!!!");
        }
        catch (IOException e){
            System.out.println("IOERROR");
            e.printStackTrace();
        }
    }
}
