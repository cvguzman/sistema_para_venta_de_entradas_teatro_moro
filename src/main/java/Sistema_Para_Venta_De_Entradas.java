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

        int opcionElegida = 0;
        boolean puedeAvanzar;
        boolean modificacionExitosa;
        boolean volverAComprar;
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
        final var temporizador = new Temporizador();

        // ::::::::::::::: VARIABLES DE CLASE :::::::::::::::

        Locale chile = Locale.forLanguageTag("es-CL"); // Necesario para que el formateador de número sepa adaptarse a precios CLP.
        NumberFormat formatoCLP = NumberFormat.getCurrencyInstance(chile);

        // :::::::::::::::  INICIO  :::::::::::::::
        System.out.println(Constantes.Instrucciones.TEXTO_INICIO);
        System.out.println(Constantes.ESPACIO);

        // :::::::::::::::  COMPRA DE ENTRADAS  :::::::::::::::
        // Punto de partida por cada iteración de compra.
        // Empezamos la cuenta regresiva para poder realizar reservas.
        temporizador.iniciar();

        do {
            System.out.println(Constantes.Instrucciones.TEXTO_PASO2);
            // Por cada interación de selección de un asiento, validamos si se acabó el tiempo para manejar reservas.
            // Si se agota el tiempo, limpiamos las reservas y dejamos el mapa a como estaba en un inicio.
            if (!temporizador.tiempoAgotado) {
                System.out.println(Constantes.Instrucciones.TEXTO_TIMER_EN_CURSO);
            } else {
                System.out.println(Constantes.Instrucciones.TEXTO_TIMEOUT);
                gestorDeAsientos.limpiarReservas();
                temporizador.iniciar();
            }
            System.out.println(Constantes.ESPACIO);

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

            if (!temporizador.tiempoAgotado) {
                System.out.println(Constantes.Instrucciones.TEXTO_RESERVA_EXITOSA.formatted(gestorDeAsientos.asientoElegido));
            }

            // Limpiamos estos valores para prevenir estancamientos mientras navegamos por las opciónes.
            puedeAvanzar = false;
            volverAComprar = false;
            modificacionExitosa = false;

            do {
                // Por cada interacción con el menú, validamos si se acabó el tiempo para manejar reservas.
                // Si se agota el tiempo, limpiamos las reservas y dejamos el mapa a como estaba en un inicio.
                if (temporizador.tiempoAgotado) {
                    System.out.println(Constantes.Instrucciones.TEXTO_TIMEOUT);
                    gestorDeAsientos.limpiarReservas();
                    temporizador.iniciar();
                }

                // ::::::::::::::: VALIDACION DE RESERVA :::::::::::::::
                // Preguntamos si desea realizar otra reserva ó proceder a la compra.
                if (!gestorDeAsientos.asientosReservados.isEmpty()) {
                    System.out.println(Constantes.ASIENTOS_RESERVADOS.formatted(gestorDeAsientos.asientosReservados));
                } else {
                    System.out.println(Constantes.ASIENTOS_RESERVADOS_VACIO);
                }
                System.out.println(Constantes.ESPACIO);
                System.out.println(Constantes.Instrucciones.TEXTO_PASO3);

                for (int indice = 0; indice < Constantes.Opciones.OPCIONES_PASO3.length; indice++) {
                    System.out.println((indice + 1)
                            + Constantes.PUNTO
                            + Constantes.ESPACIO
                            + Constantes.Opciones.OPCIONES_PASO3[indice]);
                }

                if (scanner.hasNextInt()) {
                    opcionElegida = scanner.nextInt();

                    if (opcionElegida == 1) {
                        puedeAvanzar = true;
                        volverAComprar = true;
                    }

                    if (opcionElegida == 2) {
                        if (!gestorDeAsientos.asientosReservados.isEmpty()) {
                            // ::::::::::::::: ELIMINAR RESERVA :::::::::::::::

                            System.out.println(Constantes.Instrucciones.TEXTO_ELIMINAR_RESERVA);
                            System.out.println(Constantes.ASIENTOS_RESERVADOS.formatted(gestorDeAsientos.asientosReservados));
                            System.out.println(Constantes.ESPACIO);
                            System.out.println("Ingrese un asiento de la lista:");

                            scanner.nextLine();

                            do {
                                if (scanner.hasNextLine()) {
                                    modificacionExitosa = gestorDeAsientos.eliminarReserva(scanner.nextLine().trim().toUpperCase());
                                } else {
                                    scanner.nextLine();
                                }
                                if (!modificacionExitosa) {
                                    System.out.println(Constantes.Errores.ERROR_ASIENTO_INVALIDO);
                                }
                            } while (!modificacionExitosa);

                            System.out.println(Constantes.Instrucciones.TEXTO_RESERVA_ELIMINADA.formatted(gestorDeAsientos.asientoEliminado));
                            System.out.println(Constantes.ESPACIO);

                        } else {
                            System.out.println(Constantes.Errores.ERROR_SIN_RESERVAS);
                            System.out.println(Constantes.ESPACIO);
                        }
                    }

                    if (opcionElegida == 3) {
                        if (!gestorDeAsientos.asientosReservados.isEmpty()) {
                            // Procede a realizar la compra.
                            pasarelaDePago.realizarCompra(gestorDeAsientos.asientosReservados);
                            temporizador.apagar();
                            puedeAvanzar = true;
                        } else {
                            System.out.println(Constantes.Errores.ERROR_SIN_RESERVAS);
                            System.out.println(Constantes.ESPACIO);
                        }
                    }
                } else {
                    scanner.next();
                    System.out.println(Constantes.Errores.ERROR_OPCION_INCORRECTA);
                }
                if (opcionElegida < 0 || opcionElegida > Constantes.Opciones.OPCIONES_PASO3.length) {
                    System.out.println(Constantes.Errores.ERROR_OPCION_INCORRECTA);
                }
            } while (!puedeAvanzar);

            // Limpiamos scanner para una nueva reserva.
            scanner.nextLine();

        } while (volverAComprar);

        // ::::::::::::::: IMPRIMIR RECIBO :::::::::::::::
        // Mostramos un resumen de la transacción.

        System.out.println(":::: BOLETA ::::");
        System.out.println("Asientos reservados:" + Constantes.ESPACIO + gestorDeAsientos.asientosReservados);
        System.out.println("Cantidad de entradas: " + gestorDeAsientos.asientosReservados.size());
        System.out.println("Precio final a pagar: " + formatoCLP.format(pasarelaDePago.precioFinal));

        System.out.println("::::::::::::::::::");
        System.out.println(Constantes.ESPACIO);
        System.out.println(Constantes.Instrucciones.TEXTO_COMPRA_FINALIZADA);
        System.out.println(Constantes.ESPACIO);

        scanner.close();
    }
}