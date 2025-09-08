import java.util.Scanner;

public class Sistema_Para_Venta_De_Entradas {
    public static void main(String[] args) {

        // ::::::::::::::: CONSTANTES :::::::::::::::

        // Instrucciones para mostrar en la consola.
        final var TEXTO_INICIO = "Bienvenido al sistema de entradas del Teatro Moro";
        final var TEXTO_PASO1 = "Seleccione el proceso que desea realizar, ingresando un número:";

        // Opciones para elegir en la consola.
        final String[] OPCIONES_PASO1 = {"Comprar entrada", "Salir"};


        // Utilitarios
        final var ESPACIO = " ";
        final var PUNTO = ".";
        final var ENTRADA = new Scanner(System.in);

        // Errores
        final var ERROR_OPCION_INCORRECTA = "Error: Por favor, ingrese una opción válida";

        // ::::::::::::::: VARIABLES :::::::::::::::

        int opcionElegida;
        boolean puedeAvanzar = false;

        // ::::::::::::::: PASO 1 :::::::::::::::

        System.out.println(TEXTO_INICIO);
        System.out.println(ESPACIO);
        System.out.println(TEXTO_PASO1);

        for (int indice = 0; indice < OPCIONES_PASO1.length; indice++) {
            System.out.println((indice + 1)
                    + PUNTO
                    + ESPACIO
                    + OPCIONES_PASO1[indice]);
        }

        do {
            if (ENTRADA.hasNextInt()) {
                opcionElegida = ENTRADA.nextInt();
                puedeAvanzar = opcionElegida > 0 && opcionElegida <= OPCIONES_PASO1.length;
            } else {
                ENTRADA.next();
            }
            if (!puedeAvanzar) {  System.out.println(ERROR_OPCION_INCORRECTA); }
        } while (!puedeAvanzar);
    }
}
