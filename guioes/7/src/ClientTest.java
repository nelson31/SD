import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Classe criada para testar o banco com threads de clientes
 */

public class ClientTest implements Runnable{

    // Numero de clientes abertos
    private int numCli=0;

    // String com a operacao que se pretende efetuar
    private String op;

    /**
     * Construtor
     * @param operacao
     */
    public ClientTest(String operacao){
        this.numCli++;
        this.op = operacao;
    }

    /**
     * Metodo por onde comeca a execucao de uma thread
     */
    public void run(){

        try {

            String line;

            System.out.println("Aguardando conecao..");
            Socket socket = new Socket("127.0.0.1", 12345);
            System.out.println("Conectado");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            PrintWriter out = new PrintWriter(
                    new OutputStreamWriter(socket.getOutputStream()));


            /**
             * Script de testes
             */
            for(int i=0;i<10000;i++) {
                // Levantar da conta 0 o valor 1
                out.println(op);
                out.flush();
                line=in.readLine();
                System.out.println(line);
            }

            socket.shutdownOutput();
            socket.shutdownInput();
            socket.close();

        } catch (IOException e){
            System.out.println("Conecao falhou");
            e.printStackTrace();
        }

    }

}
