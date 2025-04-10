
package tablamultiplicar;

import java.util.Scanner;


public class TablaMultiplicar {

    
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese un numero para ver su tabla de multiplicar");
        
        int tabla = scanner.nextInt();
        System.out.println("Tabla de multiplicar del " + tabla + ":");
        
        for(int i =1; i <= 10; i++){
            System.out.println(tabla + " x " + i + " = " + (tabla*i));
        }
    }
    
}
