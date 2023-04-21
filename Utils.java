import java.util.Date;
import java.util.Scanner;

public class Utils{
  
   public static int lerInt(String perguntaInteiro) {
      Scanner teclado = new Scanner(System.in);
      imprimirTexto(perguntaInteiro);
      while (!teclado.hasNextInt()) {
         teclado.nextLine(); // consome a entrada inválida
         imprimirTexto(perguntaInteiro); // pede novamente que o usuário digite um número inteiro
      }
      int valorInteiro = teclado.nextInt();
      return valorInteiro;
   }
   
   public static String lerTexto(String perguntaTexto){
      Scanner teclado = new Scanner(System.in);
      imprimirTexto(perguntaTexto);    
      return teclado.nextLine();
   }
   
   public static void imprimirTexto(String texto) {
        System.out.println(texto);
   }
}
