package ExameMuseu;

import java.io.IOException;
import java.net.Socket;

public class MainClientes {

    public static void main(String[] args) {

        try{
            Socket s = new Socket("127.0.0.1",12345);
            int o = 1;
            int d = 2;

            Thread[] threads = new Thread[100];

            for(int i = 0;i<40;i++){
                Cliente c = new Cliente("PT");
                threads[i] = new Thread(c);
            }
/*
            for(int i = 30;i<40;i++){
                Cliente c = new Cliente("Guia");
                threads[i] = new Thread(c);
            }
*/
            for(int i = 40;i<80;i++){
                Cliente c = new Cliente("EN");
                threads[i] = new Thread(c);
            }

            for(int i = 80;i<100;i++){
                Cliente c = new Cliente("Poly");
                threads[i] = new Thread(c);
            }

            for(Thread t:threads){
                t.start();
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
