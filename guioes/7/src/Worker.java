import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

/**
 * Classe criada para servir o servidor e poder
 * atender varios clientes em simultaneo, sendo esta a classe trabalhadora que se
 * encarrega de prestar servico a um cliente em especifico
 * @author nelson
 */

public class Worker implements Runnable {

    // Classe onde esta toda a logica do banco
    private Banco b;
    // Socket do servidor para comun icacao com o cliente
    private Socket client;

    /**
     * Construtor parametrizado de Worker
     * @param clientSocket
     * @param banco
     */
    public Worker(Socket clientSocket,Banco banco){
        this.b=banco;
        this.client = clientSocket;
    }

    /**
     * Metodo auxiliar que serve para verificar qual o tipo de
     * operacao que o cliente pretende efetuar
     * @param args
     * @return
     */
    public String verificaOperacao(
            String[] args, BufferedReader in, PrintWriter out){

        String line = " ";

        switch (args[0]){
            /**Consultar uma certa conta*/
            case "consultar": {
                //DEBUG!!!
                System.out.println("Entrou no 1");

                try {
                    double saldo = b.consultar(Integer.parseInt(args[1]));
                    // Para que a resposta seja enviada por aqui
                    line = Double.toString(saldo);
                } catch (ContaInvalida e) {
                    // Enviar para o cliente!
                    out.println("ContaInvalida");
                    out.flush();
                    line = " ";
                } catch (NullPointerException e){
                    out.println("Dados inseridos inválidos!!");
                    out.flush();
                } catch (ArrayIndexOutOfBoundsException e){
                    out.println("Dados inseridos inválidos!!");
                    out.flush();
                } catch (NumberFormatException e){
                    out.println("Dados inseridos inválidos!!");
                    out.flush();
                }
                break;
            }
            /**Consultar todas as contas (Rever o metodo consultarTotal)*/
            case "consultarTotal": {
                //DEBUG!!!
                System.out.println("Entrou no 2");

                try {
                    int[] contas = new int[args.length - 1];
                    for (int i = 0; i < args.length - 1; i++) {
                        contas[i] = Integer.parseInt(args[i+1]);
                    }
                    double saldo = b.consultarTotal(contas);

                    // Para que a resposta seja enviada por aqui
                    line = Double.toString(saldo);
                }  catch (Exception e){
                    out.println("Dados inseridos inválidos!!");
                    out.flush();
                    line = " ";
                }
                break;
            }
            /**Criar uma certa conta*/
            case "criarConta": {
                //DEBUG!!!
                System.out.println("Entrou no 3");

                try {
                    // Segundo elemento do args é o valor
                    double saldo = Double.parseDouble(args[1]);
                    int conta = b.criarConta(saldo);

                    // Para que a resposta seja enviada por aqui
                    line = Integer.toString(conta);
                } catch (Exception e){
                    out.println("Dados inseridos inválidos!!");
                    out.flush();
                    line = " ";
                }
                break;
            }
            /**Depositar dinheiro numa certa conta*/
            case "depositar": {
                //DEBUG!!!
                System.out.println("Entrou no 4");

                try {
                    int conta = Integer.parseInt(args[1]);
                    double saldo = Double.parseDouble(args[2]);
                    b.depositar(conta, saldo);
                    line = "Deposito realizado com sucesso!!!";
                } catch (ContaInvalida e){
                    out.println("ContaInvalida");
                    out.flush();
                    line = " ";
                } catch (Exception e){
                    out.println("Dados inseridos inválidos!!");
                    out.flush();
                    line = " ";
                }
                break;
            }
            /**Fechar uma certa conta*/
            case "fecharConta": {
                //DEBUG!!!
                System.out.println("Entrou no 5");
                try {
                    int conta = Integer.parseInt(args[1]);
                    double saldo = b.fecharConta(conta);

                    // Para que a resposta seja enviada por aqui
                    line = Double.toString(saldo);
                    //DEBUG!!!
                    System.out.println(line);
                } catch (ContaInvalida e) {
                    // Enviar para o cliente!
                    out.println("ContaInvalida");
                    out.flush();
                    line = " ";
                } catch (Exception e){
                    out.println("Dados inseridos inválidos!!");
                    out.flush();
                    line = " ";
                }
                break;
            }
            /**Levantar dinheiro de uma certa conta*/
            case "levantar": {
                //DEBUG!!!
                System.out.println("Entrou no 6");
                try {
                    int conta = Integer.parseInt(args[1]);
                    double saldo = Double.parseDouble(args[2]);
                    b.levantar(conta, saldo);
                    line = "Levantamento realizado com sucesso!!!";
                } catch (DinheiroInsuficiente e) {
                    // Enviar para o cliente!
                    out.println("No Money");
                    out.flush();
                    line = " ";
                } catch (ContaInvalida e){
                    out.println("ContaInvalida");
                    out.flush();
                    line = " ";
                } catch (Exception e){
                    out.println("Dados inseridos inválidos!!");
                    out.flush();
                    line = " ";
                }
                break;
            }
            /** Transferencia de dinheiro entre 2 contas */
            case "transferir": {
                //DEBUG!!!
                System.out.println("Entrou no 7");
                try {
                    int conta1 = Integer.parseInt(args[1]);
                    int conta2 = Integer.parseInt(args[2]);
                    double saldo = Double.parseDouble(args[3]);
                    b.transferir(conta1, conta2, saldo);
                    line = "Transferencia realizada com sucesso!!!";
                } catch (DinheiroInsuficiente e) {
                    // Enviar para o cliente!
                    out.println("No Money");
                    out.flush();
                    line = " ";
                } catch (ContaInvalida e) {
                    // Enviar para o cliente!
                    out.println("ContaInvalida");
                    out.flush();
                    line = " ";
                } catch (Exception e){
                    out.println("Dados inseridos inválidos!!");
                    out.flush();
                    line = " ";
                }
                break;
            }
            /** Pede a lista de todos os movimentos*/
            case "movimentos": {

                // Debug
                System.out.println("Entrou no 8");
                try {
                    int id = Integer.parseInt(args[1]);
                    List<Movimento> movs = b.movimentos(id);
                    // Envio dos dados
                    StringBuilder movimentos = new StringBuilder();
                    for(Movimento mov:movs){
                        movimentos.append(mov.getId());
                        movimentos.append(",");
                        movimentos.append(mov.getDescricao());
                        movimentos.append(",");
                        movimentos.append(mov.getMontante_resultante());
                        movimentos.append("@");
                    }
                    System.out.println(movimentos.toString());
                    out.println(movimentos.toString());
                    out.flush();

                } catch (ContaInvalida e){
                    // Enviar para o cliente!
                    out.println("ContaInvalida");
                    out.flush();
                    line = " ";
                } catch (Exception e){
                    out.println("Dados inseridos inválidos!!");
                    out.flush();
                    line = " ";
                }
                break;
            }
            /** Caso nao seja nenhuma destas opcoes */

            default:
                line = "Operacao inserida invalida!!!";
        }

        return line;
    }

    @Override
    public void run() {
        String line = "";

        System.out.println("Thread entrou no worker");

        try{
            // Serve para receber os dados do cliente
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(this.client.getInputStream()));

            // Responder ao cliente
            PrintWriter out = new PrintWriter(
                    new OutputStreamWriter(this.client.getOutputStream()));

            while((line = in.readLine())!=null && !line.equals("quit")){


                /** converter o que recebi para um metodo */

                // Separar a string recebida por varios campos
                // que veem separados por 'espaço'
                String[] args = line.split(" ");


                //DEBUG
                System.out.println("STRING LIDA: " + line);

                // Ver qual o tipo de operacao a realizar e executa
                // o metodo correspondente, dando como resultado o
                // valor a responder ao cliente
                line = verificaOperacao(args,in,out);

                if(!line.equals(" ")) {
                    //DEBUG
                    System.out.println("Resposta a enviar: " + line);
                    out.println(line);
                    out.flush();
                }
            }

            // Nao esquecer de encerrar o output aberto para o socket do cliente
            this.client.shutdownOutput();
            // Nao esquecer de encerrar o input aberto para o socket do cliente
            this.client.shutdownInput();
            // Nao esquecer de fechar o socket do cliente
            this.client.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
