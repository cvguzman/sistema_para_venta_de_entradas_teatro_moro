import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Sistema_Para_Venta_De_Entradas {
    public static void main(String[] args) {
        final var ENTRADA = new Scanner(System.in);
        Locale chile = Locale.forLanguageTag("es-CL"); // Necesario para que el formateador de número sepa adaptarse a precios CLP.
        NumberFormat formatoCLP = NumberFormat.getCurrencyInstance(chile);

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
        int indiceDeFila = 0;
        String asientoElegido = "";
        ArrayList<String> asientosReservados = new ArrayList<>();
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

                if (ENTRADA.hasNextInt()) {
                    opcionElegida = ENTRADA.nextInt();

                    switch (opcionElegida) {
                        case 2:
                            System.out.println(Constantes.Instrucciones.TEXTO_PROMOCIONES);
                            break;
                        case 3:
                            if (asientosReservados.size() > 0) {
                                System.out.println(Constantes.Instrucciones.TEXTO_ELIMINACION);
                                for (String asiento: asientosReservados) {
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
                    ENTRADA.next();
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
                for (int indice = 0; indice <= TEATRO_FILAS.length; indice++) {
                    switch (indice) {
                        case 0:
                            System.out.print(Constantes.ESPACIO + Constantes.ESPACIO);
                            break;
                        case 1, 3, 7, 10:
                            System.out.print(Constantes.ESPACIO + indice + Constantes.ESPACIO);
                            break;
                        case 2, 6, 8:
                            System.out.print(Constantes.ESPACIO + Constantes.ESPACIO + indice + Constantes.ESPACIO + Constantes.ESPACIO);
                            break;
                        case 4, 5, 9:
                            System.out.print(Constantes.ESPACIO + Constantes.ESPACIO + indice + Constantes.ESPACIO);
                            break;
                    }
                }

                System.out.println(Constantes.ESPACIO);

                for (int indice = 0; indice < TEATRO_FILAS.length; indice++) {
                    String filaCompleta = TEATRO_FILAS[indice] + Constantes.ESPACIO;
                    for (String asiento : grupoColumnas[indice]) {
                        filaCompleta = filaCompleta + asiento + Constantes.ESPACIO;
                    }
                    // Validamos para agregar la categoría asociada a la fila.
                    filaCompleta = switch (indice) {
                        case 0, 1 -> filaCompleta + Constantes.ESPACIO +         "|      V.I.P.       |";
                        case 2, 3 -> filaCompleta + Constantes.ESPACIO +         "| PLATEA BAJA |";
                        case 4, 5, 6, 7 -> filaCompleta + Constantes.ESPACIO + "| PLATEA ALTA |";
                        case 8, 9 -> filaCompleta + Constantes.ESPACIO +         "|     PALCO      |";
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
                                    System.out.println(Constantes.Errores.ERROR_ASIENTO_INVALIDO);
                                    puedeAvanzar = false;
                                    continue;
                                }

                                // Validamos que la fila elegida se encuentre dentro de nuestro arreglo correspondiente.
                                for (int indice = 0; indice < TEATRO_FILAS.length; indice++) {
                                    if (TEATRO_FILAS[indice].equals(String.valueOf(filaElegida))) {

                                        // Validamos que el asiento no se encuentre ocupado.
                                        // Si está disponible, lo marcamos como no disponible para futuras compras.
                                        if (grupoColumnas[indice][columnaElegida - 1].equals(Constantes.ASIENTO_NO_DISPONIBLE)) {
                                            puedeAvanzar = false;
                                        } else {
                                            grupoColumnas[indice][columnaElegida - 1] = Constantes.ASIENTO_NO_DISPONIBLE;
                                            // Guardamos el indice de la fila para luego conseguir su precio correspondiente.
                                            indiceDeFila = indice;
                                            puedeAvanzar = true;
                                        }
                                        asientosReservados.add(asientoElegido);
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
                        System.out.println(Constantes.Errores.ERROR_ASIENTO_INVALIDO);
                    }
                } while (!puedeAvanzar);

                // ::::::::::::::: PASO 3 :::::::::::::::

                System.out.println(Constantes.Instrucciones.TEXTO_PASO3);

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
                        System.out.println(Constantes.Errores.ERROR_EDAD_INVALIDA);
                    }
                } while (!puedeAvanzar);


                // Calculamos el precio de la entrada según su categoría y descuento asociado;
                int precioCategoria = 0;
                int descuentoAplicado = 0;
                int precioFinal;

                precioCategoria = switch (indiceDeFila) {
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
                System.out.println("Ubicación del asiento:" + Constantes.ESPACIO + asientoElegido);
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
                    if (ENTRADA.hasNextInt()) {
                        opcionElegida = ENTRADA.nextInt();
                        volverAComprar = opcionElegida == 1;
                        volverAlMenu = opcionElegida == 2;
                        puedeAvanzar = opcionElegida > 0 && opcionElegida <= Constantes.Opciones.OPCIONES_PASO4.length;
                    } else {
                        ENTRADA.next();
                    }
                    if (!puedeAvanzar) {  System.out.println(Constantes.Errores.ERROR_OPCION_INCORRECTA); }
                } while (!puedeAvanzar);
            } while (volverAComprar);
        } while (volverAlMenu);

        System.out.println(Constantes.Instrucciones.TEXTO_SALIDA);
    }
}