package ExameRecurso;

import java.io.*;
import java.net.Socket;

public class ClienteRunnable implements Runnable {


    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    private String line;

    public ClienteRunnable(String s){
        try {
            this.socket = new Socket("127.0.0.1", 12345);
            this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.out = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream()));
            this.line = s;
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    @Override
    public void run() {

        try {
            String linha = this.line + " " + 2000;
            out.println(linha);
            out.flush();

            // Ler a resposta
            line = in.readLine();

            System.out.println(line);
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
