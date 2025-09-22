import java.util.ArrayList;

public class GestorDeAsientos {
    private String[] teatroFilas;
    private String[][] teatroColumnas;

    public String asientoElegido;
    public String asientoEliminado;
    public ArrayList<String> asientosReservados = new ArrayList<String>();

    // Constructor
    public GestorDeAsientos(String[] teatroFilas, String[][] teatroColumnas) {
        this.teatroFilas = teatroFilas;
        this.teatroColumnas = teatroColumnas;
    }

    public boolean validarAsiento(String asiento) {
        boolean asientoValido = false;

        if (asiento.length() > 1) {
            try {
                // Separamos asiento elegido en dos elementos (Letra asociada a una fila y número de columna)
                char filaElegida = asiento.charAt(0);
                int columnaElegida = Integer.parseInt(asiento.substring(1));

                if (columnaElegida > 10 || columnaElegida < 1) {
                    return false;
                }

                // Validamos que la fila elegida se encuentre dentro de nuestro arreglo correspondiente.
                for (int indice = 0; indice < teatroFilas.length; indice++) {
                    if (teatroFilas[indice].equals(String.valueOf(filaElegida))) {

                        // Validamos que el asiento no se encuentre ocupado.
                        // Si está disponible, lo marcamos como no disponible para futuras compras.
                        if (teatroColumnas[indice][columnaElegida - 1].equals(Constantes.ASIENTO_NO_DISPONIBLE)) {
                            asientoValido = false;
                        } else {
                            teatroColumnas[indice][columnaElegida - 1] = Constantes.ASIENTO_NO_DISPONIBLE;
                            // Guardamos el asiento para luego conseguir su precio correspondiente.
                            asientosReservados.add(asiento);
                            asientoValido = true;
                        }
                        break;
                    } else {
                        asientoValido = false;
                    }
                }
                if (asientoValido) {
                    asientoElegido = asiento;
                }
                return asientoValido;
            } catch(Exception error) {
                return  false;
            }
        } else {
            return false;
        }
    }

    public void limpiarReservas() {
        for (String asiento: asientosReservados) {
            intercambiarEstadoDeReserva(asiento);
        }
        asientosReservados = new ArrayList<String>();
    }

    public boolean eliminarReserva(String asiento) {
        // Validamos que la fila elegida se encuentre dentro de nuestro arreglo correspondiente.
        for (int indice = 0; indice < asientosReservados.size(); indice++) {
            if (asientosReservados.contains(String.valueOf(asiento))) {
                asientosReservados.remove(asiento);
                asientoEliminado = asiento;
                intercambiarEstadoDeReserva(asiento);
                return true;
            }
        }
        return false;
    }

    // Creado para cambiar una reserva ocupada a estado desocupado.
    private void intercambiarEstadoDeReserva(String asiento) {
        // Separamos asiento elegido en dos elementos (Letra asociada a una fila y número de columna)
        char filaElegida = asiento.charAt(0);
        int columnaElegida = Integer.parseInt(asiento.substring(1));

        for (int indice = 0; indice < teatroFilas.length; indice++) {
            if (teatroFilas[indice].equals(String.valueOf(filaElegida))) {
                teatroColumnas[indice][columnaElegida - 1] = Constantes.ASIENTO_DISPONIBLE;
            }
        }
    }
}
