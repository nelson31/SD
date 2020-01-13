import java.net.ServerSocket;
import java.net.Socket;

/**
 * Pergunta 2 do teste 2019
 */

public class ServerShuttle {


    public static void main(String[] args) {

        try{

            ServerSocket serverS = new ServerSocket(12345);

            ControladorImpl4 c = new ControladorImpl4();

            while(true){

                System.out.println("Aguardando coneccoes...");
                // Espera por pedidos de acesso
                Socket socket = serverS.accept();
                System.out.println("Coneccao aceite!!!");

                Worker w = new Worker(socket,c);

                Thread t = new Thread(w);

                t.start();
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
