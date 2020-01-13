package ExameMuseu;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Pergunta 2 do exame Museu
 */

public class ServerMuseu {


    public static void main(String[] args) {

        try{

            ServerSocket serverS = new ServerSocket(12345);

            Museu m = new MuseuImpl();

            while(true){

                System.out.println("Aguardando coneccoes...");
                // Espera por pedidos de acesso
                Socket socket = serverS.accept();
                System.out.println("Coneccao aceite!!!");

                Worker w = new Worker(socket,m);

                Thread t = new Thread(w);

                t.start();
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
