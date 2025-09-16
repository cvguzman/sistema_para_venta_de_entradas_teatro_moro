public class Constantes {
    public class Instrucciones {
        // Instrucciones para mostrar en la consola.
        public static final String TEXTO_INICIO = "Bienvenido al sistema de entradas del Teatro Moro";
        public static final String TEXTO_PASO1 = "Seleccione el proceso que desea realizar. Ingrese un número:";
        public static final String TEXTO_PASO2 = """
                ::::::: RESERVA DE ASIENTOS:::::::
                Seleccione un asiento ingresando la letra de la fila y el número de la columna:
                Disponibilidad: [_] Disponible, [ X ] No disponible
                """;
        public static final String TEXTO_PASO3_A = "Ingrese su edad:";
        public static final String TEXTO_PASO3_B = "¿Es usted estudiante?";
        public static final String TEXTO_PASO4 = "¿Desea realizar otra compra? Seleccione una opción ingresando su número:";
        public static final String TEXTO_COMPRA_FINALIZADA = "Gracias por su compra, disfrute de la función.";

        public static final String TEXTO_PROMOCIONES = """
                ::::::: PROMOCIONES :::::::
                - 10% para estudiantes.
                - 15% para tercera edad (desde los 65 años).
                - Lleva 3 entradas por el precio de 2.
                """;
        public static final String TEXTO_ELIMINACION = """
                ::::::: ELIMINACION DE ENTRADAS :::::::
                Elimine un asiento ingresando la letra de la fila y el número de la columna:
                """;
        public static final String TEXTO_ELIMINACION_EXITO = "Asiento eliminado con exito.\n";
        public static final String TEXTO_ELIMINACION_SALIDA = "Todas tus reservas fueron eliminadas.\n";
    }

    public class Opciones {
        // Opciones para elegir en la consola.
        public static  final String[] OPCIONES_PASO1 = {"Comprar entrada", "Promociones", "Eliminación de entradas", "Salir"};
        public static final String[] OPCIONES_PASO3 = {"Si", "No"};
        public static final String[] OPCIONES_PASO4 = {"Realizar otra compra", "Salir"};
    }

    public class Errores {
        // Errores
        public static final String ERROR_OPCION_INCORRECTA = "Error: Por favor, ingrese una opción válida.";
        public static final String ERROR_ASIENTO_INVALIDO = "Error: Por favor, ingrese una fila y una columna válidas y disponibles.";
        public static final String ERROR_EDAD_INVALIDA = "Error: Por favor, ingrese una edad válida.";
        public static final String ERROR_SIN_ASIENTOS_RESERVADOS = "Error: Actualmente no tienes asientos reservados.";
    }

    // Utilitarios
    public static final String ESPACIO = " ";
    public static final String PUNTO = ".";
    public static final String ASIENTO_DISPONIBLE = "[_]";
    public static final String ASIENTO_NO_DISPONIBLE = "[X]";
    public static final int DESCUENTO_ESTUDIANTE = 10; // Este número tiene contexto de porcentaje.
    public static final int DESCUENTO_TERCERA_EDAD = 15; // Este número tiene contexto de porcentaje.
    public static final int[] PRECIOS = {35000, 25000, 15000, 11000};
}
