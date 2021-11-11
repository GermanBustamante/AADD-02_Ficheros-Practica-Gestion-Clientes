package Modelo.Utilidades;

public class Utilidades {
    public static final char[] LETRASDNI = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};
    public static final int NUMERO_SUBSTRING = 0; //TODO MEJORAR NOMBRE

    public static String getDNICliente(int numerosDNICliente) {
        char letra;
        letra = LETRASDNI[numerosDNICliente % 23];
        return String.format(String.valueOf(numerosDNICliente), letra);
    }

    public static String formatearString(int longitudCadena, String formatoCadena, String cadenaAFormatear) {
        //TODO OPERADOR TERNARIO
        if (cadenaAFormatear.length() <longitudCadena){
            cadenaAFormatear = String.format(formatoCadena, cadenaAFormatear);
        }else {
            cadenaAFormatear = cadenaAFormatear.substring(NUMERO_SUBSTRING, longitudCadena);
        }
        return cadenaAFormatear;
    }
}
