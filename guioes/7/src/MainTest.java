import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class MainTest {

    /**
     * Main de testes
     */
    public static void main(String[] args) throws Exception{


        Socket s = new Socket("127.0.0.1",12345);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(s.getInputStream()));

        PrintWriter out = new PrintWriter(
                new OutputStreamWriter(s.getOutputStream()));

        // Criar uma conta com 1000 lá dentro
        out.println("criarConta 1000");
        out.flush();
        System.out.println("Criada conta com o ID = " + in.readLine());

        s.shutdownInput();
        s.shutdownOutput();
        s.close();


        ClientTest ct1 = new ClientTest("levantar 0 1");
        ClientTest ct2 = new ClientTest("depositar 0 1");
        Thread[] arr = new Thread[2];

        arr[0] = new Thread(ct1);
        arr[1] = new Thread(ct2);


        for(Thread t:arr){
            t.start();
        }

        // Nao precisa de exceçao por causa de a main fazer o throws
        for(Thread t: arr)
            t.join();

    }
}
