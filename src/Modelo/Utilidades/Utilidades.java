package Modelo.Utilidades;

import java.io.*;
/**
 * Clase que contendrá métodos estáticos que reutilizaremos en distintas clases
 * @author germanbustamante_
 * @version 1.0
 */
public class Utilidades {
    //Constantes
    public static final char[] LETRASDNI = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};
    public static final int NUMERO_SUBSTRING = 0; //TODO MEJORAR NOMBRE
    public static final String CADENA_VACIA = "";

    /**
     * <b>Cabecera:</b> public static String getDNICompleto(int numerosDNICliente)<br/>
     * <b>Precondiciones:</b> ninguna<br/>
     * <b>Postcondiciones:</b> Abre un flujo de datos y escribe en el fichero binario "clientes.bin" al cliente correspondiente
     * en bytes<br/>
     * @param numerosDNICliente los 8 digitos de un dni
     * @return String dni con su letra correspondiente
     * */
    public static String getDNICompleto(int numerosDNICliente) {
        char letra;
        letra = LETRASDNI[numerosDNICliente % 23];
        return new StringBuilder().append(numerosDNICliente).append(letra).toString();
    }

    /**
     * <b>Cabecera:</b> public static String formatearString(int longitudCadena, String formatoCadena, String cadenaAFormatear)<br/>
     * <b>Precondiciones:</b> los parámetros deben ser válidos<br/>
     * <b>Postcondiciones:</b> Formatea o corta una cadena pasada por parámetro en función de si pasa una longitud<br/>
     * @param longitudCadena longitud máxima de la cadena, actuará como semáforo para decidir si se formatea o se corta la cadena
     * @param formatoCadena formato de la cadena a cambiar en caso de que sobrepase @longitudCadena
     * @param cadenaAFormatear cadena que va a ser formateada
     * @return String cadena formateada
     * */
    public static String formatearString(int longitudCadena, String formatoCadena, String cadenaAFormatear) {
        return (cadenaAFormatear.length() < longitudCadena) ? String.format(formatoCadena, cadenaAFormatear) : cadenaAFormatear.substring(NUMERO_SUBSTRING, longitudCadena);
    }

    /**
     * <b>Cabecera:</b> public static boolean existeFichero(File fichero)<br/>
     * <b>Precondiciones:</b> ninguna<br/>
     * <b>Postcondiciones:</b> Retorna un booleano en función de si el fichero existe<br/>
     * @param fichero fichero a comprobar
     * @return true si el fichero existe o false en el caso contrario
     * */
    public static boolean existeFichero(File fichero) {
        return fichero.exists();
    }

    /**
     * <b>Cabecera:</b> public static boolean estaVacioFichero(File fichero)<br/>
     * <b>Precondiciones:</b> ninguna<br/>
     * <b>Postcondiciones:</b> Retorna un booleano en función de si el fichero está vacío<br/>
     * @param fichero fichero a comprobar
     * @return true si el fichero está vacío o false en el caso contrario
     * */
    public static boolean estaVacioFichero(File fichero) {
        return fichero.length() == 0;
    }

    /**
     * <b>Cabecera:</b> public static void vaciarFichero(File fichero)<br/>
     * <b>Precondiciones:</b> ninguna<br/>
     * <b>Postcondiciones:</b> Dado un fichero, deja dicho fichero vacío<br/>
     * @param fichero fichero a vaciar
     * */
    public static void vaciarFichero(File fichero) {
        try(BufferedWriter bw =new BufferedWriter(new FileWriter(fichero))){
            bw.write(CADENA_VACIA);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
