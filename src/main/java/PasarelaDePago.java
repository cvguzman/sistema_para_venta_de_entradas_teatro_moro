import java.util.ArrayList;

public class PasarelaDePago {
    public int precioFinal = 0;

    public void realizarCompra(ArrayList<String> asientosReservados) {
        // Calculamos el precio de la entrada según su categoría;
        int precioCategoria;

        for (String fila: asientosReservados) {
            precioCategoria = switch (fila.charAt(0)) {
                case 'A', 'B' -> Constantes.PRECIOS[0]; // VIP
                case 'C', 'D' -> Constantes.PRECIOS[1]; // PLATEA BAJA
                case 'E', 'F', 'G', 'H' -> Constantes.PRECIOS[2]; // PLATEA ALTA
                case 'I', 'J' -> Constantes.PRECIOS[3]; // PALCO
                default -> 0;
            };
            precioFinal += precioCategoria;
        }
    }
}
