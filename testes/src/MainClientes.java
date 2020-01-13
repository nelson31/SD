import java.io.*;
import java.net.Socket;

public class MainClientes {

    public static void main(String[] args) {

        try{
            Socket s = new Socket("127.0.0.1",12345);
            int o = 1;
            int d = 2;

            for(int i = 0;i<100;i++){
                Cliente c = new Cliente(o,d);
                Thread t = new Thread(c);
                t.start();

                if(o<5 && d<5) {
                    o++;
                    d++;
                } else if(d==5) {
                    o++;
                    d = 1;
                } else if(o==5){
                    d++;
                    o = 1;
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
