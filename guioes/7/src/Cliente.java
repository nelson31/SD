import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

/**
 * Classe cliente do Banco
 * @author nelson
 */

public class Cliente {

    public static void main(String[] args) {

        try {

            /** Criacao do Stub (Banco Remoto) */
            System.out.println("Aguardando coneccao...");
            BancoStub banco = new BancoStub("127.0.0.1",12345);
            System.out.println("Conectado!!!");

            // Ler do teclado
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(System.in));

            String line = "";
            String[] campos;
            System.out.print("> ");

            while ((line=in.readLine())!=null && !line.equals("quit")){
                /*
                // Escrevo o que li do Stdin para o socket
                outSocket.println(line);
                outSocket.flush();
                // Leio a resposta do socket
                line = inSocket.readLine();
                */
                campos = line.split(" ");

                try {
                    switch (campos[0]) {
                        case "criarConta": {
                            int id = banco.criarConta(Double.parseDouble(campos[1]));
                            line = "ID da conta = " + id;
                            break;
                        }
                        case "consultar": {
                            try {
                                double saldo = banco.consultar(Integer.parseInt(campos[1]));
                                line = "Saldo da conta " + campos[1] + " = " + saldo;
                            } catch (ContaInvalida e){
                                System.out.println(e.getMessage());
                                line = " ";
                            }
                            break;
                        }
                        case "levantar": {
                            try {
                                banco.levantar(Integer.parseInt(campos[1]),Double.parseDouble(campos[2]));
                                line = "Levantamento da conta " + campos[1] + " de = " + campos[2] + "€";
                            } catch (ContaInvalida e){
                                System.out.println(e.getMessage());
                                line = " ";
                            } catch (DinheiroInsuficiente e){
                                System.out.println(e.getMessage());
                                line = " ";
                            }
                            break;
                        }
                        case "depositar": {
                            try {
                                banco.depositar(Integer.parseInt(campos[1]),Double.parseDouble(campos[2]));
                                line = "Deposito na conta " + campos[1] + " de = " + campos[2] + "€";
                            } catch (ContaInvalida e){
                                System.out.println(e.getMessage());
                                line = " ";
                            }
                            break;
                        }
                        case "consultarTotal": {

                            int[] contas = new int[campos.length - 1];
                            for(int i=0;i<campos.length - 1;i++){
                                contas[i] = Integer.parseInt(campos[i+1]);
                            }
                            double valor = banco.consultarTotal(contas);
                            line = "Saldo Total das Contas = " + valor;
                            break;
                        }
                        case "transferir": {
                            try {
                                int c1 = Integer.parseInt(campos[1]);
                                int c2 = Integer.parseInt(campos[2]);
                                double valor = Double.parseDouble(campos[3]);
                                banco.transferir(c1, c2, valor);
                                line = "Transferencia concluída com sucesso";
                            } catch (ContaInvalida | DinheiroInsuficiente e){
                                System.out.println(e.getMessage());
                                line = " ";
                            }
                            break;
                        }
                        case "fecharConta": {
                            try {
                                double saldo = banco.fecharConta(Integer.parseInt(campos[1]));
                                line = "Saldo da conta fechada = " + saldo;
                            } catch (ContaInvalida e) {
                                System.out.println(e.getMessage());
                                line = " ";
                            }
                            break;
                        }
                        case "movimentos": {

                            int conta = Integer.parseInt(campos[1]);
                            try{
                                List<Movimento> movs = banco.movimentos(conta);
                                int i =1;
                                if(movs.size()>0) {
                                    for (Movimento m : movs) {
                                        System.out.println("Movimento " + i + "-> " + m.toString());
                                        i++;
                                    }
                                } else{
                                    System.out.println("Nao existem movimentos!");
                                }
                                line = " ";
                            } catch (ContaInvalida e){
                                System.out.println(e.getMessage());
                                line = " ";
                            }
                            break;
                        }
                        case "help": {
                            StringBuilder sb = new StringBuilder();
                            sb.append("***************COMANDOS DISPONÍVEIS***************\n");
                            sb.append("1) criarConta <saldoInicial>\n");
                            sb.append("2) fecharConta <idConta>\n");
                            sb.append("3) consultar <idConta>\n");
                            sb.append("4) consultarTotal <ids das contas pretendidas>\n");
                            sb.append("5) levantar <idConta> <montante>\n");
                            sb.append("6) depositar <idConta> <montante>\n");
                            sb.append("7) transferir <idContaOrigem> <idContaDestino> <montante>\n");
                            sb.append("8) movimentos <idConta>\n");
                            line = sb.toString();
                            break;
                        }
                        default:
                            line = "Operacao inserida inválida";
                    }

                    // Imprimir a resposta
                    int i = 0;

                    if(!line.equals(" ")){
                        System.out.println("[Resposta]: ");
                        System.out.println(line);
                    }
                    System.out.print("> ");

                } catch (BancoRemotoException e){
                    System.out.println(e.getMessage());
                    System.out.print("> ");
                } catch (Exception e){
                    System.out.println("Dados inseridos invalidos!!");
                    System.out.print("> ");
                }
            }

        } catch (IOException e){
            System.out.println("ERRO");
            e.printStackTrace();
        }
    }

}
