import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Cliente implements Runnable {


    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    //origem
    private int o;
    // destino
    private int d;

    public Cliente(int origem, int destino){
        try {
            this.socket = new Socket("127.0.0.1", 12345);
            this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.out = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream()));
            this.o = origem;
            this.d = destino;
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    @Override
    public void run() {

        try {
            String line = "requisita_viagem " + this.o + " " + this.d;
            out.println(line);
            out.flush();

            // Ler a resposta
            line = in.readLine();

            System.out.println(line);
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
