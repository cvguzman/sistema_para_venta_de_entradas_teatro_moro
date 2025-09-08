import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;

public class Sistema_Para_Venta_De_Entradas {
    public static void main(String[] args) {

        // ::::::::::::::: CONSTANTES :::::::::::::::

        // Instrucciones para mostrar en la consola.
        final var TEXTO_INICIO = "Bienvenido al sistema de entradas del Teatro Moro";
        final var TEXTO_PASO1 = "Seleccione el proceso que desea realizar, ingresando un número:";
        final var TEXTO_PASO2 = """
                ::::::: RESERVA DE ASIENTOS:::::::
                Seleccione un asiento ingresando la letra de la fila y el número de la columna (ejemplo: C3):
                Disponibilidad: [_] Disponible, [ X ] No disponible
                """;
        final var TEXTO_PASO3 = "Ingrese su edad:";
        final var TEXTO_PASO4 = "Desea proceder con la compra? Seleccione una opción ingresando su número:";

        // Opciones para elegir en la consola.
        final String[] OPCIONES_PASO1 = {"Comprar entrada", "Salir"};
        final String[] OPCIONES_PASO4 = {"Si", "No"};

        // Utilitarios
        final var ESPACIO = " ";
        final var PUNTO = ".";
        final var ENTRADA = new Scanner(System.in);
        final var ASIENTO_DISPONIBLE = "[_]";
        final var ASIENTO_NO_DISPONIBLE = "[X]";
        final var DESCUENTO_MENOR_DE_EDAD = 10; // Este número tiene contexto de porcentaje.
        final var DESCUENTO_TERCERA_EDAD = 15;
        final int[] PRECIOS = {35000, 25000, 15000, 11000};
        Locale chile = Locale.forLanguageTag("es-CL"); // Necesario para que el formateador de número sepa adaptarse a precios CLP.
        NumberFormat formatoCLP = NumberFormat.getCurrencyInstance(chile);

        // Errores
        final var ERROR_OPCION_INCORRECTA = "Error: Por favor, ingrese una opción válida.";
        final var ERROR_ASIENTO_INVALIDO = "Error: Por favor, ingrese una fila y columna válida.";
        final var ERROR_EDAD_INVALIDA = "Error: Por favor, ingrese una edad válida.";

        // Mapa del teatro
        final String[] TEATRO_FILAS = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        final String[] TEATRO_COLUMNAS = {
                ASIENTO_DISPONIBLE,
                ASIENTO_DISPONIBLE,
                ASIENTO_DISPONIBLE,
                ASIENTO_DISPONIBLE,
                ASIENTO_DISPONIBLE,
                ASIENTO_DISPONIBLE,
                ASIENTO_DISPONIBLE,
                ASIENTO_DISPONIBLE,
                ASIENTO_DISPONIBLE,
                ASIENTO_DISPONIBLE
        };

        // ::::::::::::::: VARIABLES :::::::::::::::

        int opcionElegida;
        int indiceDeFila = 0;
        String asientoElegido = "";
        String[] asientosElegidos = {};
        boolean esMenorDeEdad = false;
        boolean esTerceraEdad = false;
        boolean puedeAvanzar = false;
        boolean compraFinalizada = false;

        // ::::::::::::::: PASO 1 :::::::::::::::

        System.out.println(TEXTO_INICIO);
        System.out.println(ESPACIO);

        do {
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
                    ENTRADA.nextLine();
                    puedeAvanzar = opcionElegida > 0 && opcionElegida <= OPCIONES_PASO1.length;
                } else {
                    ENTRADA.next();
                }
                if (!puedeAvanzar) {
                    System.out.println(ERROR_OPCION_INCORRECTA);
                }
            } while (!puedeAvanzar);

            // ::::::::::::::: PASO 2 :::::::::::::::

            System.out.println(TEXTO_PASO2);

            for (int indice = 0; indice <= TEATRO_FILAS.length; indice++) {
                switch (indice) {
                    case 0:
                        System.out.print(ESPACIO + ESPACIO + ESPACIO);
                        break;
                    case 1, 3, 6, 8, 10:
                        System.out.print(ESPACIO + indice + ESPACIO);
                        break;
                    case 2, 7:
                        System.out.print(ESPACIO + ESPACIO + ESPACIO + indice + ESPACIO + ESPACIO + ESPACIO);
                        break;
                    case 4, 5, 9:
                        System.out.print(ESPACIO + ESPACIO + indice + ESPACIO + ESPACIO);
                        break;
                }
            }

            System.out.println(ESPACIO);

            for (String fila : TEATRO_FILAS) {
                String filaCompleta = fila + ESPACIO;
                for (String asiento : TEATRO_COLUMNAS) {
                    filaCompleta = filaCompleta + asiento + ESPACIO;
                }
                System.out.println(filaCompleta);
            }

            System.out.println("Ingrese asiento (ej: B5):");

            do {
                if (ENTRADA.hasNextLine()) {
                    asientoElegido = ENTRADA.nextLine().trim().toUpperCase();

                    if (asientoElegido.length() > 1 && asientoElegido.length() <= 3) {
                        char filaElegida = asientoElegido.charAt(0);
                        int columnaElegida = Integer.parseInt(asientoElegido.substring(1));

                        for (int indice = 0; indice <= TEATRO_FILAS.length; indice++) {
                            if (TEATRO_FILAS[indice].equals(String.valueOf(filaElegida))) {
                                puedeAvanzar = columnaElegida > 0 && columnaElegida <= TEATRO_COLUMNAS.length;
                                indiceDeFila = indice;
                                break;
                            } else {
                                puedeAvanzar = false;
                            }
                        }
                    } else {
                        puedeAvanzar = false;
                    }
                } else {
                    ENTRADA.nextLine();
                }
                if (!puedeAvanzar) {
                    System.out.println(ERROR_ASIENTO_INVALIDO);
                }
            } while (!puedeAvanzar);

            // ::::::::::::::: PASO 3 :::::::::::::::

            System.out.println(TEXTO_PASO3);

            do {
                if (ENTRADA.hasNextInt()) {
                    opcionElegida = ENTRADA.nextInt();
                    ENTRADA.nextLine();
                    if (opcionElegida >= 12 && opcionElegida < 18) {
                        esMenorDeEdad = true;
                    } else if (opcionElegida >= 60 && opcionElegida < 100) {
                        esTerceraEdad = true;
                    }
                    puedeAvanzar = opcionElegida >= 12 && opcionElegida < 100;
                } else {
                    ENTRADA.next();
                }
                if (!puedeAvanzar) {
                    System.out.println(ERROR_EDAD_INVALIDA);
                }
            } while (!puedeAvanzar);


            // Calculamos el precio de la entrada según su categoría y descuento asociado;
            int precioCategoria = 0;
            int descuentoAplicado = 0;
            int precioFinal = 0;

            switch (indiceDeFila) {
                case 0, 1:
                    precioCategoria = PRECIOS[0]; // VIP
                    break;
                case 2, 3:
                    precioCategoria = PRECIOS[1]; // PLATEA BAJA
                    break;
                case 4, 5, 6, 7:
                    precioCategoria = PRECIOS[2]; // PLATEA ALTA
                    break;
                case 8, 9:
                    precioCategoria = PRECIOS[3]; // PALCO
                    break;
            }

            if (esMenorDeEdad) {
                descuentoAplicado = DESCUENTO_MENOR_DE_EDAD;
                precioFinal = precioCategoria - (precioCategoria * descuentoAplicado / 100);
            } else if (esTerceraEdad) {
                descuentoAplicado = DESCUENTO_TERCERA_EDAD;
                precioFinal = precioCategoria - (precioCategoria * descuentoAplicado / 100);
            } else {
                precioFinal = precioCategoria;
            }

            System.out.println(":::: Resumen de la transacción ::::");
            System.out.println("Ubicación del asiento:" + ESPACIO + asientoElegido);
            System.out.println("Precio base de la entrada:" + ESPACIO + formatoCLP.format(precioCategoria));
            System.out.println("Descuento aplicado:" + ESPACIO + descuentoAplicado + "%");
            System.out.println("Precio final a pagar: " + formatoCLP.format(precioFinal));


            // ::::::::::::::: PASO 4 :::::::::::::::

            System.out.println(ESPACIO);
            System.out.println(TEXTO_PASO4);

            for (int indice = 0; indice < OPCIONES_PASO4.length; indice++) {
                System.out.println((indice + 1)
                        + PUNTO
                        + ESPACIO
                        + OPCIONES_PASO4[indice]);
            }

            do {
                if (ENTRADA.hasNextInt()) {
                    opcionElegida = ENTRADA.nextInt();
                    ENTRADA.nextLine();
                    compraFinalizada = opcionElegida == 1;
                    puedeAvanzar = opcionElegida > 0 && opcionElegida <= OPCIONES_PASO4.length;
                } else {
                    ENTRADA.next();
                }
                if (!puedeAvanzar) {  System.out.println(ERROR_OPCION_INCORRECTA); }
            } while (!puedeAvanzar);
        } while (!compraFinalizada);
        System.out.println("Gracias por su compra, disfrute de la función.");
    }
}