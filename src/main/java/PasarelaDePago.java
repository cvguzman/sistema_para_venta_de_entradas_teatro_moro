public class PasarelaDePago {
    private boolean esTerceraEdad = false;

    public int precioCategoria = 0;
    public int precioFinal = 0;

    public void realizarCompra(int indiceDeFila) {
        // Calculamos el precio de la entrada según su categoría y descuento asociado;
        precioFinal = switch (indiceDeFila) {
            case 0, 1 -> Constantes.PRECIOS[0]; // VIP
            case 2, 3 -> Constantes.PRECIOS[1]; // PLATEA BAJA
            case 4, 5, 6, 7 -> Constantes.PRECIOS[2]; // PLATEA ALTA
            case 8, 9 -> Constantes.PRECIOS[3]; // PALCO
            default -> precioCategoria;
        };

        precioFinal = precioCategoria;
    }
}
