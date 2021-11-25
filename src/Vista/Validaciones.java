package Vista;
/**
 * Clase que contendrá métodos estáticos que usaremos para validar entradas de datos
 * @author germanbustamante_
 * @version 1.0
 */
public class Validaciones {
    /**
     * <b>Cabecera:</b> public static boolean esEntero(String cadenaNumerica)<br/>
     * <b>Precondiciones:</b> ninguna<br/>
     * <b>Postcondiciones:</b> Devuelve un booleano en función de si es entero o no el número pasado por parámetro<br/>
     * @param cadenaNumerica cadena a comprobar
     * @return true si es un número entero o false en caso contrario
     */
    public static boolean esEntero(String cadenaNumerica) {
        boolean esEntero = true;
        for (int i = 0; i < cadenaNumerica.length() && esEntero; i++) {
            if (!Character.isDigit(cadenaNumerica.charAt(i))) {
                esEntero = false;
            }
        }
        return esEntero;
    }

    /**
     * <b>Cabecera:</b> public static boolean esNumeroEnRango(String cadenaNumerica, int minimoRango, int maximoRango)<br/>
     * <b>Precondiciones:</b> los parámetros deben ser válidos<br/>
     * <b>Postcondiciones:</b> Devuelve un booleano en función de si la cadena pasada es un número entre un rango<br/>
     * @param cadenaNumerica la cadena a comprobar
     * @param minimoRango minima longitud de la cadena
     * @param maximoRango maxima longitud de la cadena
     * @return true si es un numero en el rango especificado y false si alguno de las 2 condiciones anteriores no son correctas
     */
    public static boolean esNumeroEnRango(String cadenaNumerica, int minimoRango, int maximoRango) {
        boolean esNumeroEnRango = false;
        int numero = 0;//Así si la cadena no es numérico, nunca entrará en el if y no podrá ser true
        if (esEntero(cadenaNumerica) && !cadenaNumerica.equals("")) {
            numero = Integer.parseInt(cadenaNumerica);
            if (numero >= minimoRango && numero <= maximoRango) {
                esNumeroEnRango = true;
            }
        }
        return esNumeroEnRango;
    }

    /**
     * <b>Cabecera:</b> public static boolean esNumeroEnRango(String cadenaNumerica, int longitud)<br/>
     * <b>Precondiciones:</b> los parámetros deben ser válidos<br/>
     * <b>Postcondiciones:</b> Devuelve un booleano en función de si la cadena pasada es un número con X longitud<br/>
     * @param cadenaNumerica la cadena a comprobar
     * @param longitud longitud que la cadena comprueba
     * @return true si es un numero en el de X longitud y false en caso contrario
     */
    public static boolean esNumeroEnRango(String cadenaNumerica, int longitud) {
        return (esEntero(cadenaNumerica) && cadenaNumerica.length() == longitud);
    }

    /**
     * <b>Cabecera:</b> public static boolean validarMenuFormato(String eleccion)<br/>
     * <b>Precondiciones:</b> ninguna<br/>
     * <b>Postcondiciones:</b> Retorna verdadero si el parámetro es igual a alguno de los formatos de codificación<br/>
     * @param eleccion cadena de la codificación
     * @return true en caso es igual a alguno de los formatos de codificación o false en caso contrario
     */
    public static boolean validarMenuFormato(String eleccion) {
        return eleccion.equals(Menu.US_ASCII) || eleccion.equals(Menu.ISO_8859_1) || eleccion.equals(Menu.UTF_8) || eleccion.equals(Menu.UTF_16LE) || eleccion.equals(Menu.UTF_16BE) || eleccion.equals(Menu.UTF_16);
    }

    /**
     * <b>Cabecera:</b> public static boolean esCampoVacio(String cadena)<br/>
     * <b>Precondiciones:</b> ninguna<br/>
     * <b>Postcondiciones:</b> Retorna verdadero si el parámetro está en blanco o vacío<br/>
     * @param cadena cadena a comprobar
     * @return true en caso de que el parámetro está en blanco o vacío o false en caso contrario
     */
    public static boolean esCampoVacio(String cadena) {
        return cadena.isBlank() || cadena.isEmpty();
    }
}
