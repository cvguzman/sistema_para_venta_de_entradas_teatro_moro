import java.util.ArrayList;

public class GestorDeAsientos {
    private String[] teatroFilas;
    private String[][] teatroColumnas;

    public int indiceDeFila = 0;
    public String asientoElegido;
    public ArrayList<String> asientosReservados = new ArrayList<>();

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
                    System.out.println(Constantes.Errores.ERROR_ASIENTO_INVALIDO);
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
                            // Guardamos el indice de la fila para luego conseguir su precio correspondiente.
                            indiceDeFila = indice;
                            asientoValido = true;
                        }
                        asientosReservados.add(asiento);
                        break;
                    } else {
                       return  false;
                    }
                }
                return asientoValido;
            } catch(Exception error) {
                return  false;
            }
        } else {
            return false;
        }
    }
}
