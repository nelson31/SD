import java.io.*;
import java.net.Socket;

/**
 * Classe cliente que serve para comunicar com o Servidor
 *
 * @author nelson
 */

public class Cliente {

    // Porto do socket
    private final static int PORTSOCKET = 12345;
    // Endereco do servidor(localhost)
    private final static String SERVER = "127.0.0.1";
    // Local onde os ficheiros a enviar se encontram (posso mudar isto)
    private final static String PATH_FICH_ENVIAR = "/home/utilizador/Sistemas Distribuídos/myTPSD/Fich_Client/";

    /** Tamanho do ficheiro max a transferir (escolhi 2Mb)*/
    public final static int TAMANHO_MAX = 2097152;


    /**
     * Metodo auxiliar a realizacao de um upload por parte de um cliente
     * Coloqueio estatico simplesmente para dividir codigoo
     * @param campos
     * @param out
     * @param in
     * @return
     */
    public static void upload(String[] campos,PrintWriter out,BufferedReader in, Socket socket){
        String line = "";

        // Ficheiro que se pretende enviar pelo socket num upload
        FileInputStream fis = null;

        // Usado para colocar os valores lidos do ficheiro num arrya de bytes
        BufferedInputStream bis = null;
        // Usado para o envio dos dados numa stream
        OutputStream os = null;

        try {
            // send file (Partindo do principio que o
            // ficheiro tem o nome do titulo)
            File fileEnviar = new File(PATH_FICH_ENVIAR + campos[1]);
            System.out.println("Conseguiu path");
            // Diz somente se é possivel ou nao realizar o upload
            out.println("OK");
            out.flush();

            /** Envia ao servidor a quantidade de bytes que vai transmitir*/
            line=in.readLine();
            if(line.equals("Qual o tamanho do ficheiro?")) {
                line = Integer.toString(((int) fileEnviar.length()));
                out.println(line);
                out.flush();
            }

            fis = new FileInputStream(fileEnviar);
            bis = new BufferedInputStream(fis);
            os = socket.getOutputStream();

            byte[] arrBytesEnvio;

            // Serve para ver quantos bytes eu ja li do ficheiro
            int byteslidos=0;

            /** Caso tenha menos bytes do que o minimo em memoria*/
            if(((int) fileEnviar.length())<TAMANHO_MAX){
                arrBytesEnvio = new byte[(int) fileEnviar.length()];
                bis.read(arrBytesEnvio, 0, arrBytesEnvio.length);
                System.out.println("Enviando " + PATH_FICH_ENVIAR + campos[1] + "(" + arrBytesEnvio.length + " bytes)");
                os.write(arrBytesEnvio, 0, arrBytesEnvio.length);
                os.flush();
                //System.out.println(arrBytesEnvio.length);
            } else{
                /** Aqui tenho de ir enviando aos poucos*/
                arrBytesEnvio=new byte[TAMANHO_MAX];
                int quantEnviada = 0;
                while(byteslidos<((int) fileEnviar.length())){
                    quantEnviada = bis.read(arrBytesEnvio,0,TAMANHO_MAX);
                    System.out.println("Enviando " + PATH_FICH_ENVIAR + campos[1] + "(" + quantEnviada + " bytes)");
                    os.write(arrBytesEnvio,0,quantEnviada);
                    os.flush();
                    // atualizo o numero de bytes lidos ate ao momento
                    byteslidos+=quantEnviada;
                }
            }

            System.out.println("Enviado!");

            // Imprimir a resposta
            line=in.readLine();
            System.out.println(line);
            bis.close();
            fis.close();
        } catch (Exception e){
            System.out.println("Impossivel concluir upload!");
        }
    }

    /**
     * Metodo queserve para a realizacao de um download
     */
    public static void download(int id){



    }


    /**
     * Daqui comeca a execucao dos clientes
     * @param args
     */
    public static void main(String[] args) {

        try {
            // Criando o socket de comunicacao com o servidor
            System.out.println("Aguardando coneccao...");
            Socket socket = new Socket(SERVER, PORTSOCKET);
            System.out.println("Conectado!!!");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            PrintWriter out = new PrintWriter(
                    new OutputStreamWriter(socket.getOutputStream()));

            BufferedReader inTeclado = new BufferedReader(
                    new InputStreamReader(System.in));
/*
            // Ficheiro que se pretende enviar pelo socket num upload
            FileInputStream fis = null;

            // Usado para
            BufferedInputStream bis = null;
            // Usado para o envio dos dados numa stream
            OutputStream os = null;
*/
            String line;

            System.out.print("> ");

            while(!(line=inTeclado.readLine()).equals("quit")){
                // Escrevo o que li do Stdin para o socket
                out.println(line);
                out.flush();
                String[] campos = line.split(" ");
                /* REcebendo a resposta*/
                line = in.readLine();
                System.out.println(line);

                /** Envio do ficheiro que se pretende fazer upload*/
                if(line.equals("Insira os dados do ficheiro!")){
                    upload(campos,out,in,socket);
                }
                System.out.print("> ");
            }

            // Nao esquecer de encerrar o output aberto para o socket do servidor
            socket.shutdownOutput();
            // Nao esquecer de encerrar o input aberto para o socket do servidor
            socket.shutdownInput();
            // Fechar o socket de acesso ao servidor
            socket.close();

        } catch (IOException e){
            System.out.println("Coneccao nao possivel");
            e.printStackTrace();
        }

    }
}
