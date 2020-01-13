import java.io.*;
import java.net.Socket;
import java.util.Set;

/**
 * Classe Worker que serve para correr o servidor em threads
 *
 * @author nelson
 */

public class Worker implements Runnable{

    /** Variavel que guarda a logica de negocio do sistema */
    private SoudCloud soudCloud;
    /** Socket usado para comunicar com o cliente*/
    private Socket sClient;

    /** Tamanho do ficheiro max a transferir (escolhi 2Mb)*/
    public final static int TAMANHO_MAX = 2097152;

    /** Guarda o path onde se colocam os ficheiros recebidos*/
    public final static String
            PATH_PASTA_FICH = "/home/utilizador/Sistemas Distribuídos/myTPSD/Fich_Server/";

    /**
     * Construtor por omissao
     */
    public Worker(SoudCloud sc,Socket socket){
        this.soudCloud=sc;
        this.sClient = socket;
    }


    /**
     * Metodo auxiliar que serve para o efetuar de um upload
     * @param args
     * @param out
     * @return
     */
    public String upload(String[] args,BufferedReader in,PrintWriter out){

        String line = " ";

        // Guardar o ficheiro recebido caso haja um upload
        FileOutputStream fos = null;

        // Gravacao de bytes no fluxo de saida
        BufferedOutputStream bos = null;

        try {
            int bytesRead=0;
            // Rever isto das etiquetas
            String[] etqs = args[4].split("@");

            out.println("Insira os dados do ficheiro!");
            out.flush();

            // Se for possivel realizar upload
            if((line=in.readLine()).equals("OK")) {

                /** Agora vou aguardar que o cliente me envie o tamanho total
                 * do ficheiro a transmitir!*/
                // Acho os sockets so aceitam perguntas e respostas?!!!!??
                out.println("Qual o tamanho do ficheiro?");
                out.flush();
                //Aguardando resposta...
                line=in.readLine();
                int tamanhoFich = Integer.parseInt(line);

                // Array para onde se escreve o que se le do socket
                byte[] arrByteslidos;
                // Receber a input stream of bytes do cliente
                InputStream is = this.sClient.getInputStream();
                // Criar o ficheiro na pasta com o nome do titulo da musica
                fos = new FileOutputStream(PATH_PASTA_FICH + args[1]);
                // Usado para escrever o conteudo lido do socket para o ficheiro
                bos = new BufferedOutputStream(fos);

                //DEBUG
                System.out.println("Consegui criar as variaveis");

                // Quantidade de bytes recebidos pelo socket
                int quantRecebida = 0;

                //DEBUG
                System.out.println("Tamanho Fich=" + tamanhoFich);

                /** Se nao for necessario tanto espaco em memoria*/
                if(tamanhoFich<TAMANHO_MAX){

                    arrByteslidos = new byte[TAMANHO_MAX];

                    bytesRead = is.read(arrByteslidos, 0 ,arrByteslidos.length);

                    //DEBUG
                    System.out.println("Conseguiu ler");

                    bos.write(arrByteslidos,0,bytesRead);

                    bos.flush();

                } else {
                    arrByteslidos=new byte[TAMANHO_MAX];
                    //Enquanto nao acabar de escrever tudo no ficheiro
                    while (bytesRead < tamanhoFich) {
                        // Lendo os bytes do socket
                        quantRecebida = is.read(arrByteslidos, 0, TAMANHO_MAX);

                        System.out.println("quantRecebida=" + quantRecebida);

                        //DEBUG
                        System.out.println("Conseguiu ler");

                        bytesRead += quantRecebida;

                        // Escreve o que se leu para o ficheiro
                        bos.write(arrByteslidos, 0, quantRecebida);
                        bos.flush();
                    }
                }

                //DEBUG
                System.out.println("Conseguiu escrever");

                // Pode entao adicionar uma musica
                int id = this.soudCloud.addMusica(
                        args[1], args[2], Integer.parseInt(args[3]), etqs);

                //DEBUG
                System.out.println("Conseguiu criar Musica");

                fos.close();
                bos.close();

                //DEBUG
                System.out.println("Conseguiu fechar");

                line = "Upload Musica com id: " + id + " de tamanho: " + bytesRead;
            }

        } catch (Exception e){
            out.println("Dados do upload inseridos invalidos!");
            out.flush();
        }

        return line;
    }

    /**
     * Metodo usado para a realizacaop de um download
     * @param id
     * @return
     */
    public String download(int id){

        String line = "";
        return line;
    }



    /**
     * Metodo por onde comeca a execucao da thread
     */
    @Override
    public void run() {

        try {

            String line,nomeUtilizador="";

            // Recebendo texto pelo socket
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(sClient.getInputStream()));

            // Envio de texto pelo socket
            PrintWriter out = new PrintWriter(
                    new OutputStreamWriter(sClient.getOutputStream()));

            String[] args;

            boolean valido = false;

            /* Enquanto nao for quit e null e ainda nao for valido o utilizador*/
            while((line = in.readLine())!=null && !line.equals("quit") && !valido) {

                args = line.split(" ");

                /* Verificar se o cliente esta a querer autenticar-se*/
                if (args[0].equals("login")) {
                    try {
                        // Debug
                        System.out.println("Entrou login");
                        nomeUtilizador=args[1];
                        valido=this.soudCloud.login(args[1], args[2]);
                    } catch (UtilizadorInvalido e) {
                        out.println("Utilizador Invalido");
                        out.flush();
                    } catch (PasswordIncorreta e){
                        out.println("Password Incorreta!");
                        out.flush();
                    } catch (Exception e){
                        out.println("Dados inseridos Invalidos!!");
                        out.flush();
                    }
                } else if(args[0].equals("criarConta")){
                    /* Pretende criar uma conta*/
                    try {
                        this.soudCloud.addUtilizador(args[1], args[2]);
                        out.println("Conta Criada!");
                        out.flush();
                    } catch (ContaJaExistente e){
                        out.println("Username ja existente!");
                        out.flush();
                    } catch (Exception e){
                        out.println("Dados inseridos invalidos!");
                        out.flush();
                    }
                } else {
                    out.println("Operacao Inserida Invalida!");
                    out.flush();
                }

                /* Se o utilizador que pretende aceder ao sistema for valido!*/
                if (valido) {
                    out.println("Bem-Vindo ao SoundCloud!");
                    out.flush();
                    /* Vai lendo os pedidos do utilizador*/
                    while((line = in.readLine())!=null && !line.equals("quit")){

                        args = line.split(" ");

                        line = " ";

                        switch (args[0]){

                            /* Adicao de uma musica*/
                            case "upload":
                            {
                                line = this.upload(args,in,out);
                                break;
                            }
                            case "procurar":
                            {
                                // Ver melhor esta questao das etiquetas
                                Set<Musica> musicas = this.soudCloud.searchByEtiquetas(args);
                                line = "";
                                for(Musica m:musicas){
                                    line = line + m.toString() + "@";
                                }
                                break;
                            }
                            case "download":
                            {
                                // Faz o download
                                break;
                            }
                            default:
                                out.println("Operacao inserida Invalida!");
                                out.flush();
                        }

                        /* Envio da resposta ao cliente*/
                        if(!line.equals(" ")){
                            out.println("[RESPOSTA]: " + line);
                            out.flush();
                        }
                    }

                }
            }

            // Fazer logout do utilizador
            this.soudCloud.logout(nomeUtilizador);

            // Nao esquecer de encerrar o output aberto para o socket do cliente
            this.sClient.shutdownOutput();
            // Nao esquecer de encerrar o input aberto para o socket do cliente
            this.sClient.shutdownInput();
            // Nao esquecer de fechar o socket do cliente
            this.sClient.close();

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
