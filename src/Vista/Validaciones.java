package Vista;

public class Validaciones {

    public static boolean esEntero(String cadenaNumerica) {
        boolean esEntero = true;
        for (int i = 0; i < cadenaNumerica.length() && esEntero; i++) {
            if (!Character.isDigit(cadenaNumerica.charAt(i))) {
                esEntero = false;
            }
        }
        return esEntero;
    }

    public static boolean esNumeroEnRango(String cadenaNumerica, int numero1, int numero2) {
        boolean esNumeroEnRango = false;
        int numero = numero1--;//Así si la cadena no es numérico, nunca entrará en el if y no podrá ser true
        if (esEntero(cadenaNumerica)){
            numero = Integer.parseInt(cadenaNumerica);
        }
        if (numero>=numero1 && numero<=numero2){
            esNumeroEnRango = true;
        }
        return esNumeroEnRango;
    }

    public static boolean longitudCadenaCorrecta(String nombreCliente, int maximaLongitud) {
        return nombreCliente.length()>=Menu.MINIMA_LONGITUD_CADENA && nombreCliente.length()<=maximaLongitud;
    }

    //Método sobrecargado para el DNI y el teléfono, ya que no tienen una longitud entre X e Y, sino solo un número Z, cpn lo cual
    //solo necesitamos como parámetro la longitud exacta
    public static boolean esNumeroEnRango(String cadenaNumerica, int longitud) {
        return (esEntero(cadenaNumerica) && cadenaNumerica.length()==longitud);
    }

    public static boolean validarMenuFormato(String eleccion) {
        return eleccion.equals(Menu.US_ASCII) || eleccion.equals(Menu.ISO_8859_1) || eleccion.equals(Menu.UTF_8) ||
                eleccion.equals(Menu.UTF_16LE) || eleccion.equals(Menu.UTF_16BE) || eleccion.equals(Menu.UTF_16);
    }
}
