package ex3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Classe Cliente que comunica com o servidor
 */

public class Cliente {

    public static void main(String[] args){

        try{
            String line = "";

            System.out.println("Aguardando coneccao...");
            Socket socket = new Socket("127.0.0.1",12345);
            System.out.println("Conectado!!!");

            // Escrever o input do teclado
            BufferedReader in = new BufferedReader(new
                    InputStreamReader(System.in));

            // RECEBER A RESPOSTA DO SERVIDOR
            BufferedReader inSocket = new BufferedReader(new
                    InputStreamReader(socket.getInputStream()));

            // Enviar dados ao servidor
            PrintWriter out = new PrintWriter(
                    socket.getOutputStream());

            System.out.print("> ");

            line = in.readLine();

            // Enquanto houver coisas a ler do System.in
            while(!line.equals("quit") && line!=null){
                // Enviar o que escrevi no teclado para o servidor
                out.println(line);
                out.flush();
                // Ler a resposta do servidor
                line = inSocket.readLine();
                System.out.println("RESPOSTA: " + line);
                System.out.print("> ");
                line = in.readLine();
            }

            socket.shutdownOutput();
            socket.shutdownInput();
            socket.close();

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
