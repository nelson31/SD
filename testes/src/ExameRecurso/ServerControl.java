package ExameRecurso;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerControl {

    public static void main(String[] args) {

        try{

            ServerSocket serverSocket = new ServerSocket(12345);

            Control c = new ControlImpl();

            while(true){

                System.out.println("Aguardando coneccoes...");
                Socket s = serverSocket.accept();
                System.out.println("Conectado! ");

                Worker w = new Worker(s,c);

                Thread t = new Thread(w);

                t.start();
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
