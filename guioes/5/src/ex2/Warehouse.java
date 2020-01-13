package ex2;

import java.util.HashMap;

public class Warehouse {

    // Variavel que guarda o stock
    private HashMap<String, Item> stock;


    public Warehouse(){
        this.stock = new HashMap<>();
    }

    /**
     * Adicona um Item ao armazem
     * ATENCAO: SO PODE SER USADO POR UMA THREAD(nao lida com a concorrencia)!!!!!!
     * @param i
     */
    public void adicionar(Item i){

        if(!this.stock.containsKey(i.getNome())) {
            this.stock.put(i.getNome(), i);
        }
    }


    /**
     * aumenta a quantidade do item
     *
     * @param item
     * @param quantity
     */
    public void supply(String item, int quantity) {

        System.out.println("Supply do item: " + item + " com a quantidade: " + quantity);
        this.stock.get(item).supply(quantity);
    }

    /**
     * Consome 1 unidade de cada iem passado no array
     * (bloqueia caso nao haja unidades disponiveis)
     *
     * @param items
     */
    public void consume(String[] items) {

        for (String i : items) {
            System.out.println("Consume do item: " + i);
            this.stock.get(i).consume();
        }
    }

}
