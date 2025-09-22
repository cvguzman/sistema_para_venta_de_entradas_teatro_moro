public class Constantes {
    public static class Instrucciones {
        // Instrucciones para mostrar en la consola.
        public static final String TEXTO_INICIO = "Bienvenido al sistema de entradas del Teatro Moro";
        public static final String TEXTO_PASO2 = """
                ::::::: RESERVA DE ASIENTOS:::::::
                Seleccione un asiento del mapa ingresando la letra de la fila y el número de la columna:
                Disponibilidad: [_] Disponible, [X] No disponible
                
                ::: ALERTA ::: Tiempo máximo para reservar: 2 minutos.
                """;
        public static final String TEXTO_PASO3 = "¿Desea realizar otra reserva ó modificación? Seleccione una opción ingresando su número:";
        public static final String TEXTO_ELIMINAR_RESERVA = """
                ::::::: ELIMINAR RESERVA :::::::
                Elimine un asiento ingresando la letra de la fila y el número de la columna:
                """;
        public static final String TEXTO_RESERVA_EXITOSA = "Asiento %s reservado con exito.";
        public static final String TEXTO_RESERVA_ELIMINADA = "Asiento %s eliminado con exito.";
        public static final String TEXTO_COMPRA_FINALIZADA = "Gracias por su compra, disfrute de la función.";
        public static final String TEXTO_TIMER_EN_CURSO = "¡Cuenta regresiva en curso!";
        public static final String TEXTO_TIMEOUT = "::: ALERTA ::: El tiempo para reservar ha expirado y su sesión ha sido reiniciada. Por favor, vuelva a intentarlo.";
    }

    public static class Opciones {
        // Opciones para elegir en la consola.
        public static final String[] OPCIONES_PASO3 = {"Realizar otra reserva", "Eliminar una reserva", "Realizar compra"};
    }

    public static class Errores {
        // Errores
        public static final String ERROR_OPCION_INCORRECTA = "::: ERROR ::: Por favor, ingrese una opción válida.";
        public static final String ERROR_ASIENTO_INVALIDO = "::: ERROR ::: Por favor, ingrese una fila y una columna válidas y disponibles.";
        public static final String ERROR_SIN_RESERVAS = "::: ERROR ::: Debes tener al menos una reserva para continuar.";
    }

    // Utilitarios
    public static final String ESPACIO = " ";
    public static final String PUNTO = ".";
    public static final String ASIENTO_DISPONIBLE = "[_]";
    public static final String ASIENTO_NO_DISPONIBLE = "[X]";
    public static final int[] PRECIOS = {35000, 25000, 15000, 11000};
    public static  final String ASIENTOS_RESERVADOS = "::: ASIENTOS RESERVADOS ::: %s";
    public static  final String ASIENTOS_RESERVADOS_VACIO = "::: ASIENTOS RESERVADOS ::: No hay reservas pendientes.";
}
