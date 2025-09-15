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
        boolean esMenorDeEdad = false;
        boolean esTerceraEdad = false;
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
                    }
                    if (!puedeAvanzar) {
                        System.out.println(Constantes.Errores.ERROR_ASIENTO_INVALIDO);
                    }
                } while (!puedeAvanzar);

                // ::::::::::::::: PASO 3 :::::::::::::::

                System.out.println(Constantes.Instrucciones.TEXTO_PASO3);

                do {
                    if (scanner.hasNextInt()) {
                        opcionElegida = scanner.nextInt();
                        scanner.nextLine();
                        if (opcionElegida >= 12 && opcionElegida < 18) {
                            esMenorDeEdad = true;
                        } else if (opcionElegida >= 60 && opcionElegida < 100) {
                            esTerceraEdad = true;
                        }
                        puedeAvanzar = opcionElegida >= 12 && opcionElegida < 100;
                    } else {
                        scanner.next();
                    }
                    if (!puedeAvanzar) {
                        System.out.println(Constantes.Errores.ERROR_EDAD_INVALIDA);
                    }
                } while (!puedeAvanzar);


                // Calculamos el precio de la entrada según su categoría y descuento asociado;
                int precioCategoria = 0;
                int descuentoAplicado = 0;
                int precioFinal;

                precioCategoria = switch (gestorDeAsientos.indiceDeFila) {
                    case 0, 1 -> Constantes.PRECIOS[0]; // VIP
                    case 2, 3 -> Constantes.PRECIOS[1]; // PLATEA BAJA
                    case 4, 5, 6, 7 -> Constantes.PRECIOS[2]; // PLATEA ALTA
                    case 8, 9 -> Constantes.PRECIOS[3]; // PALCO
                    default -> precioCategoria;
                };

                if (esMenorDeEdad) {
                    descuentoAplicado = Constantes.DESCUENTO_MENOR_DE_EDAD;
                    precioFinal = precioCategoria - (precioCategoria * descuentoAplicado / 100);
                } else if (esTerceraEdad) {
                    descuentoAplicado = Constantes.DESCUENTO_TERCERA_EDAD;
                    precioFinal = precioCategoria - (precioCategoria * descuentoAplicado / 100);
                } else {
                    precioFinal = precioCategoria;
                }

                // Mostramos un resumen de la transacción.
                System.out.println(":::: Resumen de la transacción ::::");
                System.out.println("Ubicación del asiento:" + Constantes.ESPACIO + gestorDeAsientos.asientoElegido);
                System.out.println("Precio base de la entrada:" + Constantes.ESPACIO + formatoCLP.format(precioCategoria));
                System.out.println("Descuento aplicado:" + Constantes.ESPACIO + descuentoAplicado + "%");
                System.out.println("Precio final a pagar: " + formatoCLP.format(precioFinal));

                // Limpiamos los valores para una próxima compra.
                esMenorDeEdad = false;
                esTerceraEdad = false;


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