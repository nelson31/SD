package guiao1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Thread;
import java.lang.Runnable;

public class Ex1 implements Runnable{

    int I;

    public void run(){
        for(int i=0;i<I;i++)
            System.out.println(i+1);
    }

    public Ex1(int n){
        I = n;
    }


    public static void main(String args[]){

        BufferedReader in
                = new BufferedReader(new InputStreamReader(System.in));
        String line1 ="";
        String line2 ="";
        try{
            System.out.print("Quantas threads?: ");
            line1 = in.readLine();
            System.out.print("Quantos valores a imprimir?: ");
            line2 = in.readLine();
        } catch(IOException e) {
            System.err.println("Error reading line form System.in");
            e.printStackTrace();
        }

        int N = Integer.parseInt(line1);
        int n = Integer.parseInt(line2);


        Thread[] arr = new Thread[N];

        for(int i=0;i<N;i++) {
            Ex1 inc = new Ex1(n);
            arr[i] = new Thread(inc);
        }

        for(int i=0;i<N;i++) {
            arr[i].start();
        }


        for(Thread t:arr)
            try{
                t.join();
            }catch (InterruptedException exc){
                System.out.println("Fim");
            }


    }
}

