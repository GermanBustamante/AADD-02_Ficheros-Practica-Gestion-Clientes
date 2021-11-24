package Modelo.Utilidades;

import java.io.*;
/**
 * <b>Clase que contendrá métodos que reutilizaremos en distintas clases<b>
 * @author germanbustamante_
 * @version 1.0
 */

public class Utilidades {
    public static final char[] LETRASDNI = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};
    public static final int NUMERO_SUBSTRING = 0; //TODO MEJORAR NOMBRE

    public static String getDNICliente(int numerosDNICliente) {
        char letra;
        letra = LETRASDNI[numerosDNICliente % 23];
        return new StringBuilder().append(numerosDNICliente).append(letra).toString();
    }

    public static String formatearString(int longitudCadena, String formatoCadena, String cadenaAFormatear) {
        return (cadenaAFormatear.length() < longitudCadena) ? String.format(formatoCadena, cadenaAFormatear) : cadenaAFormatear.substring(NUMERO_SUBSTRING, longitudCadena);
    }

    public static boolean existeFichero(File fichero) {
        return fichero.exists();
    }

    public static boolean estaVacioFichero(File fichero) {
        return fichero.length() == 0;
    }

    public static void vaciarFichero(File fichero) {
        try(BufferedWriter bw =new BufferedWriter(new FileWriter(fichero))){
            bw.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
