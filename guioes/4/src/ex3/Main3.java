package ex3;

import java.util.Scanner;

public class Main3 {

    public static void main(String[] args){

        System.out.println("Quantas threads deseja: ");

        Scanner sc = new Scanner(System.in);
        int numT = sc.nextInt();

        BarreiraAula b = new BarreiraAula(10);

        Thread[] arr = new Thread[numT];

        // Criar as Threads
        for(int i=0;i<numT;i++) {
            Cliente c = new Cliente(b);
            arr[i] = new Thread(c);
        }

        // Dar inicio a execucao das Threads
        for(int i=0;i<numT;i++) {
            arr[i].start();
        }

        try{
            for(Thread t:arr)
                t.join();
        } catch (InterruptedException e){System.out.println("FIM");}

    }
}
