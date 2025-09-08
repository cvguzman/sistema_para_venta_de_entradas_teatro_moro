import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;

public class Sistema_Para_Venta_De_Entradas {
    public static void main(String[] args) {

        // ::::::::::::::: CONSTANTES :::::::::::::::

        // Instrucciones para mostrar en la consola.
        final var TEXTO_INICIO = "Bienvenido al sistema de entradas del Teatro Moro";
        final var TEXTO_PASO1 = "Seleccione el proceso que desea realizar. Ingrese un número:";
        final var TEXTO_PASO2 = """
                ::::::: RESERVA DE ASIENTOS:::::::
                Seleccione un asiento ingresando la letra de la fila y el número de la columna:
                Disponibilidad: [_] Disponible, [ X ] No disponible
                """;
        final var TEXTO_PASO3 = "Ingrese su edad:";
        final var TEXTO_PASO4 = "¿Desea realizar otra compra? Seleccione una opción ingresando su número:";
        final var TEXTO_SALIDA = "Gracias por su compra, disfrute de la función.";

        // Opciones para elegir en la consola.
        final String[] OPCIONES_PASO1 = {"Comprar entrada", "Salir"};
        final String[] OPCIONES_PASO4 = {"Realizar otra compra", "Salir"};

        // Utilitarios
        final var ESPACIO = " ";
        final var PUNTO = ".";
        final var ENTRADA = new Scanner(System.in);
        final var ASIENTO_DISPONIBLE = "[_]";
        final var ASIENTO_NO_DISPONIBLE = "[X]";
        final var DESCUENTO_MENOR_DE_EDAD = 10; // Este número tiene contexto de porcentaje.
        final var DESCUENTO_TERCERA_EDAD = 15; // Este número tiene contexto de porcentaje.
        final int[] PRECIOS = {35000, 25000, 15000, 11000};
        Locale chile = Locale.forLanguageTag("es-CL"); // Necesario para que el formateador de número sepa adaptarse a precios CLP.
        NumberFormat formatoCLP = NumberFormat.getCurrencyInstance(chile);

        // Errores
        final var ERROR_OPCION_INCORRECTA = "Error: Por favor, ingrese una opción válida.";
        final var ERROR_ASIENTO_INVALIDO = "Error: Por favor, ingrese una fila y una columna válidas y disponibles.";
        final var ERROR_EDAD_INVALIDA = "Error: Por favor, ingrese una edad válida.";

        // Mapa del teatro
        final String[] TEATRO_FILAS = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        final String[] TEATRO_COLUMNAS = {
                ASIENTO_NO_DISPONIBLE,
                ASIENTO_NO_DISPONIBLE,
                ASIENTO_DISPONIBLE,
                ASIENTO_DISPONIBLE,
                ASIENTO_DISPONIBLE,
                ASIENTO_DISPONIBLE,
                ASIENTO_DISPONIBLE,
                ASIENTO_NO_DISPONIBLE,
                ASIENTO_NO_DISPONIBLE,
                ASIENTO_NO_DISPONIBLE
        };

        // ::::::::::::::: VARIABLES :::::::::::::::

        int opcionElegida;
        int indiceDeFila = 0;
        String asientoElegido = "";
        boolean esMenorDeEdad = false;
        boolean esTerceraEdad = false;
        boolean puedeAvanzar = false;
        boolean compraFinalizada = false;
        String[][] grupoColumnas = {
                TEATRO_COLUMNAS.clone(),
                TEATRO_COLUMNAS.clone(),
                TEATRO_COLUMNAS.clone(),
                TEATRO_COLUMNAS.clone(),
                TEATRO_COLUMNAS.clone(),
                TEATRO_COLUMNAS.clone(),
                TEATRO_COLUMNAS.clone(),
                TEATRO_COLUMNAS.clone(),
                TEATRO_COLUMNAS.clone(),
                TEATRO_COLUMNAS.clone(),
        };

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

                // Opción de Salir termina el programa.
                if (opcionElegida == 2) {
                    return;
                }

                puedeAvanzar = opcionElegida > 0 && opcionElegida <= OPCIONES_PASO1.length;
            } else {
                ENTRADA.next();
            }
            if (!puedeAvanzar) {
                System.out.println(ERROR_OPCION_INCORRECTA);
            }
        } while (!puedeAvanzar);

        // Punto de partida por cada iteración de compra.
        do {
            // ::::::::::::::: PASO 2 :::::::::::::::

            System.out.println(TEXTO_PASO2);

            // Dibujamos el mapa del teatro.
            for (int indice = 0; indice <= TEATRO_FILAS.length; indice++) {
                switch (indice) {
                    case 0:
                        System.out.print(ESPACIO + ESPACIO);
                        break;
                    case 1, 3, 7, 10:
                        System.out.print(ESPACIO + indice + ESPACIO);
                        break;
                    case 2, 6, 8:
                        System.out.print(ESPACIO + ESPACIO + indice + ESPACIO + ESPACIO);
                        break;
                    case 4, 5, 9:
                        System.out.print(ESPACIO + ESPACIO + indice + ESPACIO);
                        break;
                }
            }

            System.out.println(ESPACIO);

            for (int indice = 0; indice < TEATRO_FILAS.length; indice++) {
                String filaCompleta = TEATRO_FILAS[indice] + ESPACIO;
                for (String asiento : grupoColumnas[indice]) {
                    filaCompleta = filaCompleta + asiento + ESPACIO;
                }
                // Validamos para agregar la categoría asociada a la fila.
                filaCompleta = switch (indice) {
                    case 0, 1 -> filaCompleta + ESPACIO +         "|      V.I.P.       |";
                    case 2, 3 -> filaCompleta + ESPACIO +         "| PLATEA BAJA |";
                    case 4, 5, 6, 7 -> filaCompleta + ESPACIO + "| PLATEA ALTA |";
                    case 8, 9 -> filaCompleta + ESPACIO +         "|     PALCO      |";
                    default -> filaCompleta;
                };
                System.out.println(filaCompleta);
            }

            System.out.println("Ingrese asiento (ej: B5):");
            ENTRADA.nextLine();

            // Validamos el asiento elegido.
            do {
                if (ENTRADA.hasNextLine()) {
                    asientoElegido = ENTRADA.nextLine().trim().toUpperCase();

                    if (asientoElegido.length() > 1) {
                        try {
                            // Separamos asiento elegido en dos elementos (Letra asociada a una fila y número de columna)
                            char filaElegida = asientoElegido.charAt(0);
                            int columnaElegida = Integer.parseInt(asientoElegido.substring(1));

                            if (columnaElegida > 10 || columnaElegida < 1) {
                                System.out.println(ERROR_ASIENTO_INVALIDO);
                                puedeAvanzar = false;
                                continue;
                            }

                            // Validamos que la fila elegida se encuentre dentro de nuestro arreglo correspondiente.
                            for (int indice = 0; indice < TEATRO_FILAS.length; indice++) {
                                if (TEATRO_FILAS[indice].equals(String.valueOf(filaElegida))) {

                                    // Validamos que el asiento no se encuentre ocupado.
                                    // Si está disponible, lo marcamos como no disponible para futuras compras.
                                    if (grupoColumnas[indice][columnaElegida - 1].equals(ASIENTO_NO_DISPONIBLE)) {
                                        puedeAvanzar = false;
                                    } else {
                                        grupoColumnas[indice][columnaElegida - 1] = ASIENTO_NO_DISPONIBLE;
                                        // Guardamos el indice de la fila para luego conseguir su precio correspondiente.
                                        indiceDeFila = indice;
                                        puedeAvanzar = true;
                                    }
                                    break;
                                } else {
                                    puedeAvanzar = false;
                                }
                            }
                        } catch(Exception error) {
                            puedeAvanzar = false;
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
            int precioFinal;

            precioCategoria = switch (indiceDeFila) {
                case 0, 1 -> PRECIOS[0]; // VIP
                case 2, 3 -> PRECIOS[1]; // PLATEA BAJA
                case 4, 5, 6, 7 -> PRECIOS[2]; // PLATEA ALTA
                case 8, 9 -> PRECIOS[3]; // PALCO
                default -> precioCategoria;
            };

            if (esMenorDeEdad) {
                descuentoAplicado = DESCUENTO_MENOR_DE_EDAD;
                precioFinal = precioCategoria - (precioCategoria * descuentoAplicado / 100);
            } else if (esTerceraEdad) {
                descuentoAplicado = DESCUENTO_TERCERA_EDAD;
                precioFinal = precioCategoria - (precioCategoria * descuentoAplicado / 100);
            } else {
                precioFinal = precioCategoria;
            }

            // Mostramos un resumen de la transacción.
            System.out.println(":::: Resumen de la transacción ::::");
            System.out.println("Ubicación del asiento:" + ESPACIO + asientoElegido);
            System.out.println("Precio base de la entrada:" + ESPACIO + formatoCLP.format(precioCategoria));
            System.out.println("Descuento aplicado:" + ESPACIO + descuentoAplicado + "%");
            System.out.println("Precio final a pagar: " + formatoCLP.format(precioFinal));

            // Limpiamos los valores para una próxima compra.
            esMenorDeEdad = false;
            esTerceraEdad = false;


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
                    compraFinalizada = opcionElegida == 2;
                    puedeAvanzar = opcionElegida > 0 && opcionElegida <= OPCIONES_PASO4.length;
                } else {
                    ENTRADA.next();
                }
                if (!puedeAvanzar) {  System.out.println(ERROR_OPCION_INCORRECTA); }
            } while (!puedeAvanzar);
        } while (!compraFinalizada);

        System.out.println(TEXTO_SALIDA);
    }
}