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
        boolean esEstudiante = false;
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
            // ::::::::::::::: PASO 1 :::::::::::::::

            do {
                System.out.println(Constantes.Instrucciones.TEXTO_INICIO);
                System.out.println(Constantes.ESPACIO);

                System.out.println(Constantes.Instrucciones.TEXTO_PASO1);

                for (int indice = 0; indice < Constantes.Opciones.OPCIONES_PASO1.length; indice++) {
                    System.out.println((indice + 1)
                            + Constantes.PUNTO
                            + Constantes.ESPACIO
                            + Constantes.Opciones.OPCIONES_PASO1[indice]);
                }

                if (scanner.hasNextInt()) {
                    opcionElegida = scanner.nextInt();

                    switch (opcionElegida) {
                        case 2:
                            System.out.println(Constantes.Instrucciones.TEXTO_PROMOCIONES);
                            break;
                        case 3:
                            if (gestorDeAsientos.asientosReservados.size() > 0) {
                                System.out.println(Constantes.Instrucciones.TEXTO_ELIMINACION);
                                for (String asiento: gestorDeAsientos.asientosReservados) {
                                    System.out.println("-" + Constantes.ESPACIO + asiento);
                                }
                            } else {
                                System.out.println(Constantes.Errores.ERROR_SIN_ASIENTOS_RESERVADOS);
                            }
                            break;
                        default:
                            // Opción de Salir termina el programa.
                            return;
                    }
                    puedeAvanzar = opcionElegida > 0 && opcionElegida <= Constantes.Opciones.OPCIONES_PASO1.length;
                } else {
                    scanner.next();
                }
                if (!puedeAvanzar) {
                    System.out.println(Constantes.Errores.ERROR_OPCION_INCORRECTA);
                }
            } while (!puedeAvanzar);

            // Punto de partida por cada iteración de compra.
            do {
                // ::::::::::::::: PASO 2 :::::::::::::::

                System.out.println(Constantes.Instrucciones.TEXTO_PASO2);

                // Dibujamos el mapa del teatro.
                DibujadorDeMapa.dibujarMapa(TEATRO_FILAS, grupoColumnas);

                System.out.println("Ingrese asiento (ej: B5):");
                scanner.nextLine();

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

                // ::::::::::::::: PASO 3 :::::::::::::::

                System.out.println(Constantes.Instrucciones.TEXTO_PASO3_A);

                do {
                    if (scanner.hasNextInt()) {
                        opcionElegida = scanner.nextInt();
                        scanner.nextLine();
                        // Validamos edad por si aplica descuento.
                        puedeAvanzar = pasarelaDePago.validarDescuento(opcionElegida);
                    } else {
                        scanner.next();
                        puedeAvanzar = false;
                    }
                    if (!puedeAvanzar) {
                        System.out.println(Constantes.Errores.ERROR_EDAD_INVALIDA);
                    }
                } while (!puedeAvanzar);

                // Preguntamos si el cliente es estudiante

                System.out.println(Constantes.Instrucciones.TEXTO_PASO3_B);

                for (int indice = 0; indice < Constantes.Opciones.OPCIONES_PASO3.length; indice++) {
                    System.out.println((indice + 1)
                            + Constantes.PUNTO
                            + Constantes.ESPACIO
                            + Constantes.Opciones.OPCIONES_PASO3[indice]);
                }

                do {
                    if (scanner.hasNextInt()) {
                        opcionElegida = scanner.nextInt();
                        scanner.nextLine();
                        esEstudiante = opcionElegida == 1;
                        puedeAvanzar = opcionElegida == 1 || opcionElegida == 2;
                    } else {
                        scanner.next();
                        puedeAvanzar = false;
                    }
                    if (!puedeAvanzar) {
                        System.out.println(Constantes.Errores.ERROR_OPCION_INCORRECTA);
                    }
                } while (!puedeAvanzar);

                pasarelaDePago.realizarCompra(gestorDeAsientos.indiceDeFila, esEstudiante);

                // Limpiamos los valores para una próxima compra.
                esEstudiante = false;

                // Mostramos un resumen de la transacción.
                System.out.println(":::: Resumen de la transacción ::::");
                System.out.println("Ubicación del asiento:" + Constantes.ESPACIO + gestorDeAsientos.asientoElegido);
                System.out.println("Precio base de la entrada:" + Constantes.ESPACIO + formatoCLP.format(pasarelaDePago.precioCategoria));
                System.out.println("Descuento aplicado:" + Constantes.ESPACIO + pasarelaDePago.descuentoAplicado + "%");
                System.out.println("Precio final a pagar: " + formatoCLP.format(pasarelaDePago.precioFinal));

                // ::::::::::::::: PASO 4 :::::::::::::::

                System.out.println(Constantes.ESPACIO);
                System.out.println(Constantes.Instrucciones.TEXTO_PASO4);

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
                        volverAlMenu = opcionElegida == 2;
                        puedeAvanzar = opcionElegida > 0 && opcionElegida <= Constantes.Opciones.OPCIONES_PASO4.length;
                    } else {
                        scanner.next();
                    }
                    if (!puedeAvanzar) {  System.out.println(Constantes.Errores.ERROR_OPCION_INCORRECTA); }
                } while (!puedeAvanzar);
            } while (volverAComprar);
        } while (volverAlMenu);

        System.out.println(Constantes.Instrucciones.TEXTO_SALIDA);
    }
}