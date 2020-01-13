import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Classe servidor que atende pedidos de conexao de clientes
 * e lhes responde -> uso de Sockets TCP
 *
 * @author nelson
 */

public class SoudCloudServer {

    // GUARDA A PORTO DO SERVERSOCKET CRIADO
    public final static int PORTSOCKET = 12345;


    public static void main(String[] args) throws IOException {

        /** Criacao da logica de negocio do sistema*/
        SoudCloud sc = new SoudCloud();

        /** Criacao do socket servidor*/
        ServerSocket sSocket = new ServerSocket(PORTSOCKET);

        while (true) {
            System.out.println("Aguardando conexoes...");
            Socket sClient = sSocket.accept();
            System.out.println("Conectado!");

            // Criaçao de uma thread para correr os varios utilizadores
            // que podem aceder ao soundcloud a partir a classe worker
            Worker w = new Worker(sc, sClient);
            Thread t = new Thread(w);

            // Iniciar a execucao da thread
            t.start();
        }
    }
}
