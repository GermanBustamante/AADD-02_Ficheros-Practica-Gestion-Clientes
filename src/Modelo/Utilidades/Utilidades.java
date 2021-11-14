package Modelo.Utilidades;

import java.io.File;

public class Utilidades {
    public static final char[] LETRASDNI = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};
    public static final int NUMERO_SUBSTRING = 0; //TODO MEJORAR NOMBRE

    public static String getDNICliente(int numerosDNICliente) {
        char letra;
        letra = LETRASDNI[numerosDNICliente % 23];
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append(String.valueOf(numerosDNICliente)).append(String.valueOf(letra)).toString();
    }

    public static String formatearString(int longitudCadena, String formatoCadena, String cadenaAFormatear) {
        return (cadenaAFormatear.length() <longitudCadena) ? String.format(formatoCadena, cadenaAFormatear) : cadenaAFormatear.substring(NUMERO_SUBSTRING, longitudCadena);
    }

    public static boolean existeFichero(String rutaFichero){
        return new File(rutaFichero).exists();
    }
}
