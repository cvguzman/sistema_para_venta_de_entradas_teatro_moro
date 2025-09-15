public class DibujadorDeMapa {
    public static void dibujarMapa(String[] filas, String[][] columnas) {
        for (int indice = 0; indice <= filas.length; indice++) {
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

        for (int indice = 0; indice < filas.length; indice++) {
            String filaCompleta = filas[indice] + Constantes.ESPACIO;
            for (String asiento : columnas[indice]) {
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
    }
}
