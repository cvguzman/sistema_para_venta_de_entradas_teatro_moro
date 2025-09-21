import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;

public class Sistema_Para_Venta_De_Entradas {
    public static void main(String[] args) {

        // Mapa del teatro
        final String[] TEATRO_FILAS = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        final String[] TEATRO_COLUMNAS = {
                Constantes.ASIENTO_NO_DISPONIBLE,
                Constantes.ASIENTO_NO_DISPONIBLE,
                Constantes.ASIENTO_DISPONIBLE,
                Constantes.ASIENTO_DISPONIBLE,
                Constantes.ASIENTO_DISPONIBLE,
                Constantes.ASIENTO_DISPONIBLE,
                Constantes.ASIENTO_DISPONIBLE,
                Constantes.ASIENTO_NO_DISPONIBLE,
                Constantes.ASIENTO_NO_DISPONIBLE,
                Constantes.ASIENTO_NO_DISPONIBLE
        };

        // ::::::::::::::: VARIABLES :::::::::::::::

        int opcionElegida;
        boolean puedeAvanzar = false;
        boolean volverAComprar = false;
        boolean volverAlMenu = false;
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

        // ::::::::::::::: VARIABLES DE INSTANCIA :::::::::::::::

        final var scanner = new Scanner(System.in);
        final var gestorDeAsientos = new GestorDeAsientos(TEATRO_FILAS, grupoColumnas);
        final var pasarelaDePago = new PasarelaDePago();

        // ::::::::::::::: VARIABLES DE CLASE :::::::::::::::

        Locale chile = Locale.forLanguageTag("es-CL"); // Necesario para que el formateador de número sepa adaptarse a precios CLP.
        NumberFormat formatoCLP = NumberFormat.getCurrencyInstance(chile);

        do {
            // :::::::::::::::  MENU  :::::::::::::::
            System.out.println(Constantes.Instrucciones.TEXTO_INICIO);
            System.out.println(Constantes.ESPACIO);

            // :::::::::::::::  COMPRA DE ENTRADAS  :::::::::::::::
            // Punto de partida por cada iteración de compra.
            do {
                System.out.println(Constantes.Instrucciones.TEXTO_PASO2);

                // Dibujamos el mapa del teatro.
                DibujadorDeMapa.dibujarMapa(TEATRO_FILAS, grupoColumnas);

                System.out.println("Ingrese asiento (ej: B5):");

                // Validamos el asiento elegido.
                do {
                    if (scanner.hasNextLine()) {
                        puedeAvanzar = gestorDeAsientos.validarAsiento(scanner.nextLine().trim().toUpperCase());
                    } else {
                        scanner.nextLine();
                        puedeAvanzar = false;
                    }
                    if (!puedeAvanzar) {
                        System.out.println(Constantes.Errores.ERROR_ASIENTO_INVALIDO);
                    }
                } while (!puedeAvanzar);

                System.out.println(Constantes.Instrucciones.TEXTO_RESERVA_EXITOSA.formatted(gestorDeAsientos.asientoElegido));

                // ::::::::::::::: VALIDACION DE RESERVA :::::::::::::::
                // Preguntamos si desea realizar otra reserva ó proceder a la compra.

                System.out.println(Constantes.ESPACIO);
                System.out.println(Constantes.Instrucciones.TEXTO_PASO3);

                for (int indice = 0; indice < Constantes.Opciones.OPCIONES_PASO4.length; indice++) {
                    System.out.println((indice + 1)
                            + Constantes.PUNTO
                            + Constantes.ESPACIO
                            + Constantes.Opciones.OPCIONES_PASO4[indice]);
                }

                do {
                    if (scanner.hasNextInt()) {
                        opcionElegida = scanner.nextInt();
                        volverAComprar = opcionElegida == 1;
                        puedeAvanzar = opcionElegida > 0 && opcionElegida <= Constantes.Opciones.OPCIONES_PASO4.length;
                        if (opcionElegida == 3) {
                            pasarelaDePago.realizarCompra(gestorDeAsientos.indiceDeFila);
                            volverAlMenu = false;
                        }

                    } else {
                        scanner.next();
                    }
                    if (!puedeAvanzar) {  System.out.println(Constantes.Errores.ERROR_OPCION_INCORRECTA); }
                } while (!puedeAvanzar);
            } while (volverAComprar);
        } while (volverAlMenu);

        // ::::::::::::::: IMPRIMIR RECIBO :::::::::::::::
        // Mostramos un resumen de la transacción.

        System.out.println(":::: Resumen de la transacción ::::");
        System.out.println("Ubicación del asiento:" + Constantes.ESPACIO + gestorDeAsientos.asientoElegido);
        System.out.println("Precio base de la entrada:" + Constantes.ESPACIO + formatoCLP.format(pasarelaDePago.precioCategoria));
        System.out.println("Precio final a pagar: " + formatoCLP.format(pasarelaDePago.precioFinal));
        System.out.println(Constantes.ESPACIO);
        System.out.println(Constantes.Instrucciones.TEXTO_COMPRA_FINALIZADA);
        System.out.println(Constantes.ESPACIO);

        scanner.close();
    }
}