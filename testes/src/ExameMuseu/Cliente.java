package ExameMuseu;

import java.io.*;
import java.net.Socket;

public class Cliente implements Runnable {


    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    //origem
    private String tipo;

    public Cliente(String t){
        try {
            this.socket = new Socket("127.0.0.1", 12345);
            this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.out = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream()));
            this.tipo=t;
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    @Override
    public void run() {

        try {
            String line;

            if(tipo.equals("Guia")) {
                line = "enterGuide";
                out.println(line);
                out.flush();
            } else if(tipo.equals("Poly")) {
                line = "enterPoly";
                out.println(line);
                out.flush();
            } else if(tipo.equals("EN")) {
                line = "enterEN";
                out.println(line);
                out.flush();
            } else if(tipo.equals("PT")) {
                line = "enterPT";
                out.println(line);
                out.flush();
            }

            // Ler a resposta
            line = in.readLine();

            System.out.println(line);
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
