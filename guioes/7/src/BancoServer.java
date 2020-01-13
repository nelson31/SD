import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Classe servidor do Banco
 * @author nelson
 */

public class BancoServer {

    public static void main(String[] args){

        Banco b = new Banco();
        String line = "";

        try{
            // Criacao do socket do servidor do banco
            ServerSocket serverSocket = new ServerSocket(12345);

            while(true){

                System.out.println("Aguardando por clientes...");
                Socket clSocket = serverSocket.accept();
                System.out.println("Pedido aceite!!");

                // Criaçao de uma thread para correr os varios clientes
                // que podem aceder ao banco a partir a classe worker
                Worker w = new Worker(clSocket,b);
                Thread t = new Thread(w);

                // Iniciar a execucao da thread
                t.start();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }
}
