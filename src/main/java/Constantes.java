public class Constantes {
    public class Instrucciones {
        // Instrucciones para mostrar en la consola.
        public static final String TEXTO_INICIO = "Bienvenido al sistema de entradas del Teatro Moro";
        public static final String TEXTO_PASO2 = """
                ::::::: RESERVA DE ASIENTOS:::::::
                Seleccione un asiento del mapa ingresando la letra de la fila y el número de la columna:
                Disponibilidad: [_] Disponible, [ X ] No disponible
                
                Tiempo máximo para reservar: 2 minutos.
                """;
        public static final String TEXTO_PASO3 = "¿Desea realizar otra reserva ó modificación? Seleccione una opción ingresando su número:";
        public static final String TEXTO_RESERVA_EXITOSA = "Asiento %s reservado con exito.";
        public static final String TEXTO_COMPRA_FINALIZADA = "Gracias por su compra, disfrute de la función.";
        public static final String TEXTO_TIMEOUT = "El tiempo para reservar ha expirado, por favor, vuelva a intentarlo.";
    }

    public class Opciones {
        // Opciones para elegir en la consola.
        public static final String[] OPCIONES_PASO4 = {"Realizar otra reserva", "Modificar reserva", "Realizar compra"};
    }

    public class Errores {
        // Errores
        public static final String ERROR_OPCION_INCORRECTA = "Error: Por favor, ingrese una opción válida.";
        public static final String ERROR_ASIENTO_INVALIDO = "Error: Por favor, ingrese una fila y una columna válidas y disponibles.";
    }

    // Utilitarios
    public static final String ESPACIO = " ";
    public static final String PUNTO = ".";
    public static final String ASIENTO_DISPONIBLE = "[_]";
    public static final String ASIENTO_NO_DISPONIBLE = "[X]";
    public static final int[] PRECIOS = {35000, 25000, 15000, 11000};
}
