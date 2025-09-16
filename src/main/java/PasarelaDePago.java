public class PasarelaDePago {
    private boolean esTerceraEdad = false;

    public int precioCategoria = 0;
    public int descuentoAplicado = 0;
    public int precioFinal = 0;

    public boolean validarDescuento(int edad) {
        if (edad > 64) {
            esTerceraEdad = true;
        } else {
            esTerceraEdad = false;
        }
         return edad > 5 && edad < 100;
    }

    public void realizarCompra(int indiceDeFila, boolean esEstudiante) {
        // Calculamos el precio de la entrada según su categoría y descuento asociado;
        precioCategoria = switch (indiceDeFila) {
            case 0, 1 -> Constantes.PRECIOS[0]; // VIP
            case 2, 3 -> Constantes.PRECIOS[1]; // PLATEA BAJA
            case 4, 5, 6, 7 -> Constantes.PRECIOS[2]; // PLATEA ALTA
            case 8, 9 -> Constantes.PRECIOS[3]; // PALCO
            default -> precioCategoria;
        };

        if (esTerceraEdad) {
            descuentoAplicado = Constantes.DESCUENTO_TERCERA_EDAD;
            precioFinal = precioCategoria - (precioCategoria * descuentoAplicado / 100);
        } else if (esEstudiante) {
            descuentoAplicado = Constantes.DESCUENTO_ESTUDIANTE;
            precioFinal = precioCategoria - (precioCategoria * descuentoAplicado / 100);
        } else {
            descuentoAplicado = 0;
            precioFinal = precioCategoria;
        }

        // Limpiamos los valores para una próxima compra.
        esTerceraEdad = false;
    }
}
