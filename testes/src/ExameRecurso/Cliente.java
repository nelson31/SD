package ExameRecurso;

import java.io.*;
import java.net.Socket;

public class Cliente {

    public static void main(String[] args) {

        try {
            Socket socket = new Socket("127.0.0.1", 12345);

            BufferedReader inTeclado = new BufferedReader(new InputStreamReader(System.in));

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

            String line;

            System.out.println("> ");

            while ((line = inTeclado.readLine())!=null && !(line.equals("quit"))){

                out.println(line);
                out.flush();

                line = in.readLine();

                System.out.println("RESPOSTA: " + line);
                System.out.println("> ");
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
