package ExameRecurso;

import java.io.IOException;
import java.net.Socket;

public class MainClientes {

    public static void main(String[] args) {

        try{
            Socket s = new Socket("127.0.0.1",12345);

            String line;

            Thread[] ths = new Thread[100];

            for(int i = 0;i<100;i+=2){
                ClienteRunnable c1 = new ClienteRunnable("Carro");
                ClienteRunnable c2 = new ClienteRunnable("Barco");
                ths[i] = new Thread(c1);
                ths[i+1] = new Thread(c2);
            }

            for(Thread t:ths){
                t.start();
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
