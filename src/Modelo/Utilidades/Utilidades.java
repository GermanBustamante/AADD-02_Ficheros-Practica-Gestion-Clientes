package Modelo.Utilidades;

public class Utilidades {
    public static final char[] LETRASDNI = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};

    public static String getDNICliente(int numerosDNICliente) {
        char letra;
        letra = LETRASDNI[numerosDNICliente % 23];
        return String.format(String.valueOf(numerosDNICliente), letra);
    }
}
