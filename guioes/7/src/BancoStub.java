import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class BancoStub implements IBanco {

    private Socket sClient;
    private BufferedReader in;
    private PrintWriter out;

    /**
     * Construtor parametrizado
     */
    public BancoStub(String name, int port) throws IOException{
        this.sClient= new Socket(name,port);
        this.in= new BufferedReader(
                new InputStreamReader(this.sClient.getInputStream()));
        this.out= new PrintWriter(
                new OutputStreamWriter(this.sClient.getOutputStream()));
    }

    /**
     * Metodo que serve oara pedir ao servidor que seja criada uma nova conta
     * @param saldo
     * @return
     */
    @Override
    public int criarConta(double saldo) throws BancoRemotoException{

        String line;
        int idConta = -1;

        //DEBUG
        System.out.println("Entrei no criarConta");

        //Envia a mensagem ao servidor
        this.out.println("criarConta " + saldo);
        this.out.flush();
        try {
            // Recebe a resposta
            idConta=Integer.parseInt(this.in.readLine());

        } catch(IOException e){
            throw new BancoRemotoException("Nao consegue ler a resposta...");
        } catch (NumberFormatException e){
            throw new BancoRemotoException("Formato da resposta invalido..");
        }

        return idConta;
    }

    /**
     *
     * @param id
     * @return
     * @throws ContaInvalida
     */
    @Override
    public double fecharConta(int id) throws ContaInvalida,BancoRemotoException {

        String line;
        double saldo = -1;

        try{
            // Pedir ao servidor que feche a conta
            this.out.println("fecharConta " + id);
            this.out.flush();

            // Esperar pela resposta
            line = this.in.readLine();

            if(line.equals("ContaInvalida")){
               throw new ContaInvalida("Conta Inválida!!!");
            } else if(line.equals("Dados inseridos inválidos!!")){
                saldo = -1;
            } else{
                saldo = Double.parseDouble(line);
            }

        } catch (IOException e) {
            throw new BancoRemotoException("Nao consegue ler a resposta...");
        } catch (NumberFormatException e){
            throw new BancoRemotoException("Formato da resposta invalido..");
        }

        return saldo;
    }

    /**
     *
     * @param contas
     * @return
     */
    @Override
    public double consultarTotal(int[] contas) throws BancoRemotoException{
        String line;
        double valor=0;

        try{
            // Preparar a string a enviar ao servidor
            line = "consultarTotal";
            for(Integer c:contas){
                line += " " + c;
            }
            // Pedir que o servidor me consulte as contas todas
            this.out.println(line);
            this.out.flush();

            // Esperar pela resposta do servidor
            line = this.in.readLine();

            if(line.equals("Dados inseridos inválidos!!")){
                throw new BancoRemotoException(line);
            }else {
                valor = Double.parseDouble(line);
            }

        } catch (IOException e) {
            throw new BancoRemotoException("Nao consegue ler a resposta...");
        }

        return valor;
    }

    /**
     *
     * @param c
     * @return
     * @throws ContaInvalida
     */
    @Override
    public double consultar(int c) throws ContaInvalida,BancoRemotoException {
        String line;
        double valor = 0;

        //DEBUG
        System.out.println("Entrei no consultar");

        //Envia a mensagem ao servidor
        this.out.println("consultar " + c);
        this.out.flush();

        try {
            // Recebe a resposta
            if((line=this.in.readLine()).equals("ContaInvalida")){
                throw new ContaInvalida("Conta Inválida!!!");
            } else if(line.equals("Dados inseridos inválidos!!")){
                throw new BancoRemotoException(line);
            } else {
                valor = Double.parseDouble(line);
            }

        } catch(IOException e){
            throw new BancoRemotoException("Nao consegue ler a resposta...");
        } catch (NumberFormatException e){
            throw new BancoRemotoException("Formato da resposta invalido..");
        }

        return valor;
    }

    /**
     *
     * @param c
     * @param valor
     * @throws ContaInvalida
     */
    @Override
    public void depositar(int c, double valor) throws ContaInvalida,BancoRemotoException {

        String line;

        //DEBUG
        System.out.println("Entrei no depositar");

        //Envia a mensagem ao servidor
        this.out.println("depositar " + c + " " + valor);
        this.out.flush();

        try {
            // Recebe a resposta
            if((line=this.in.readLine()).equals("ContaInvalida")){
                throw new ContaInvalida("Conta Inválida!!!");
            } else if(line.equals("Dados inseridos inválidos!!")){
                throw new BancoRemotoException(line);
            }

        } catch(IOException e){
            throw new BancoRemotoException("Nao consegue ler a resposta...");
        } catch (NumberFormatException e){
            throw new BancoRemotoException("Formato da resposta invalido..");
        }
    }

    /**
     *
     * @param c
     * @param valor
     * @throws DinheiroInsuficiente
     * @throws ContaInvalida
     */
    @Override
    public void levantar(int c, double valor) throws DinheiroInsuficiente, ContaInvalida, BancoRemotoException {

        String line;

        //DEBUG
        System.out.println("Entrei no levantar");

        //Envia a mensagem ao servidor
        this.out.println("levantar " + c + " " + valor);
        this.out.flush();

        try {
            // Recebe a resposta
            if((line=this.in.readLine()).equals("ContaInvalida")){
                throw new ContaInvalida("Conta Inválida!!!");
            } else if(line.equals("No Money")) {
                throw new DinheiroInsuficiente("Não há dinheiro suficiente!!!");
            }

        } catch(IOException e){
            throw new BancoRemotoException("Nao consegue ler a resposta...");
        } catch (NumberFormatException e){
            throw new BancoRemotoException("Formato da resposta invalido...");
        }
    }

    /**
     *
     * @param c1 origem
     * @param c2 destino
     * @param valor
     * @throws ContaInvalida
     * @throws DinheiroInsuficiente
     */
    @Override
    public void transferir(int c1, int c2, double valor) throws ContaInvalida, DinheiroInsuficiente, BancoRemotoException {

        String line = "transferir " + c1 + " " + c2 + " " + valor;


        // Enviar o pedido ao servidor
        this.out.println(line);
        this.out.flush();

        try {
            // Aguardar reposta ...
            line = this.in.readLine();

            if(line.equals("ContaInvalida")){
                throw new ContaInvalida("Contas fornecidas inválidas!");
            } else if(line.equals("No Money")) {
                throw new DinheiroInsuficiente("Não há dinheiro suficiente!!!");
            } if(line.equals("Dados inseridos inválidos!!")){
                throw new BancoRemotoException(line);
            }

        } catch (IOException e){
            throw new BancoRemotoException("Erro ao processar transferencia!");
        }
    }

    /**
     *
     * @param id
     * @return
     * @throws ContaInvalida
     */
    @Override
    public List<Movimento> movimentos(int id) throws ContaInvalida, BancoRemotoException {

        String line = "movimentos " + id;
        List<Movimento> movs = new ArrayList<>();

        try{
            // Enviar a query
            this.out.println(line);
            this.out.flush();

            // Esperar pela lista de movimentos
            if((line=this.in.readLine()).equals("ContaInvalida")){
                throw new ContaInvalida("Conta Invalida!!!");
            } else {

                String[] args = line.split("@");

                int i = 0;

                if(args.length>1) {
                    for (String s : args) {
                        String[] campos = args[i].split(",");
                        Movimento m = new Movimento(Integer.parseInt(campos[0]), campos[1], Double.parseDouble(campos[2]));
                        movs.add(m);
                        i++;
                    }
                }
            }
        } catch(IOException e){
            throw new BancoRemotoException("Nao consegue ler a resposta...");
        }
        return movs;
    }

    /**
     * Metodo usado para fechar as extremidades do socket
     * @throws IOException
     */
    public void fecharSockets() throws IOException{

        // Nao esquecer de encerrar o output aberto para o socket do servidor
        this.sClient.shutdownOutput();
        // Nao esquecer de encerrar o input aberto para o socket do servidor
        this.sClient.shutdownInput();
        // Fechar o socket de acesso ao servidor
        this.sClient.close();
    }
}
