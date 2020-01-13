/**
 * Programa que substitui o comando 'echo'
 * Lê linha a linha da consola e imprime para stdout
 */

package warmup;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

public class Main {

    public static void main(String args[]){
        String line;

        BufferedReader in
                = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.print("> ");
            while ((line = in.readLine()) != null) {
                System.out.println("Echo: " + line);
                System.out.print("> ");
            }
        } catch (IOException e){
            System.err.println("Error reading line form System.in");
            e.printStackTrace();
        }
    }
}
