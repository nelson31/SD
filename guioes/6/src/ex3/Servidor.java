package ex3;

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

    private int numWorkers;

    public Servidor(){
        this.numWorkers=0;
    }

    public static void main(String[] args) {

        Servidor server = new Servidor();

        try {
            String line = "";
            // Socket do servidor
            ServerSocket sSock = new ServerSocket(12345);

            while (true) {
                System.out.println("Aguardando por cliente...");
                // Socket do cliente criado aquando da aceitacao da coneccao
                Socket clSock = sSock.accept();
                System.out.println("Cliente Aceite!!!");

                Worker w = new Worker(clSock,(++server.numWorkers));

                Thread t = new Thread(w);

                t.start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
