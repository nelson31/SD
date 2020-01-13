package ex1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Usar o comando >telnet 127.0.0.1 12345
 * No terminal com o Servidor aberto
 */

public class Servidor {

    public static void main(String[] args) {

        try {
            String line = "";
            // Socket do servidor
            ServerSocket sSock = new ServerSocket(12345);

            while (true) {
                // Socket do cliente criado aquando da aceitacao da coneccao
                Socket clSock = sSock.accept();

                System.out.println("Srevidor aceitou");

                // Instancia criada para receber a informacao enviada pelo cliente
                BufferedReader in = new BufferedReader(new
                        InputStreamReader(clSock.getInputStream()));

                // Variavel usada para enviar a resposta ao cliente
                PrintWriter out = new PrintWriter(
                        clSock.getOutputStream());

                System.out.println("Streams abertas con sucesso");

                // Le a informacao recebida do cliente
                line = in.readLine();

                // Enquanto houver coisas para ler do socket
                while (line != null) {
                    // Escreve a informacao que se pretende enviar ao cliente
                    out.println("[Servidor]: " + line);
                    // Obriga a enviar a informacao ao cliente
                    out.flush();
                    // Le a informacao recebida do cliente
                    line = in.readLine();
                }

                // Nao esquecer de fechar o socket do cliente
                clSock.shutdownOutput();
                clSock.shutdownInput();
                clSock.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
