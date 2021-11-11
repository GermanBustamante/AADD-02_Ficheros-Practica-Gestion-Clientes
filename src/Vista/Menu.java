package Vista;

import Modelo.Utilidades.Utilidades;

import java.util.Scanner;

public class Menu {
    public static final String MENU_PRINCIPAL = """
            Gestor de clientes:
            ------------------------
            1. Nuevo cliente.
            2. Consultar cliente.
            3. Borrar cliente.
            4. Configuración de exportación.
            5. Exportar clientes.
            0. Salir.
            """;

    public static final String MENU_FORMATO_FICHERO = """
            1 -> US-ASCII
            2 -> ISO-8859-1
            3 -> UTF-8
            4 -> UTF-16BE
            5 -> UTF-16LE
            6 -> UFT-16
            """;

    public static final String MENSAJE_DNI_NO_ENCONTRADO = "No se ha encontrado ningún DNI";
    public static final int MINIMA_ELECCION_MENU_PRINCIPAL = 0;
    public static final int MAXIMA_ELECCION_MENU_PRINCIPAL = 5;
    public static final int MINIMA_LONGITUD_CADENA = 1;
    public static final int LONGITUD_NOMBRE_CLIENTE = 25;
    public static final int LONGITUD_APELLIDOS_CLIENTE = 25;
    public static final int LONGITUD_DIRECCION_CLIENTE = 30;
    public static final int LONGITUD_NUMEROS_DNI = 8;
    public static final int LONGITUD_TELEFONO = 9;
    public static final String US_ASCII = "US-ASCII";
    public static final String ISO_8859_1 = "ISO-8859-1";
    public static final String UTF_8 = "UTF-8";
    public static final String UTF_16BE = "UTF-16BE";
    public static final String UTF_16LE = "UTF-16LE";
    public static final String UTF_16 = "UTF-16";

    public static final String INGRESO_NOMBRE_CLIENTE = "Ingresa el nombre del cliente";
    public static final String INGRESO_APELLIDOS_CLIENTE = "Ingresa los apellidos del cliente";
    private static final String INGRESO_DIRECCION_CLIENTE = "Ingresa la dirección del cliente";
    private static final String INGRESO_DNI_CLIENTE = "Ingresa el DNI del cliente";
    private static final String INGRESO_TELEFONO_CLIENTE = "Ingresa el teléfono del cliente";

    public static final String FORMATO_NOMBRE = "%-25s";
    private static final String FORMATO_APELLIDOS = "%-25s";
    private static final String FORMATO_DIRECCION = "%-30s";

    private static StringBuilder stringBuilder;
    private static Scanner teclado = new Scanner(System.in);

    public static int mostrarMenuPrincipalEingresarEntradaMenu() {
        String eleccionCadena = null;
        do {
            System.out.println(MENU_PRINCIPAL);
            eleccionCadena = teclado.nextLine();
        } while (!Validaciones.esNumeroEnRango(eleccionCadena, MINIMA_ELECCION_MENU_PRINCIPAL, MAXIMA_ELECCION_MENU_PRINCIPAL));
        return Integer.parseInt(eleccionCadena);
    }

    public static String ingresarNombreCliente() {
        System.out.println(INGRESO_NOMBRE_CLIENTE);
        return Utilidades.formatearString(LONGITUD_NOMBRE_CLIENTE, FORMATO_NOMBRE,teclado.nextLine());
    }

    public static String ingresarApellidosCliente() {
        System.out.println(INGRESO_APELLIDOS_CLIENTE);
        return Utilidades.formatearString(LONGITUD_APELLIDOS_CLIENTE, FORMATO_APELLIDOS,teclado.nextLine());
    }

    public static int ingresarNumerosDNICliente() {
        String numerosDNIClienteCadena = null;
        do {
            System.out.println(INGRESO_DNI_CLIENTE);
            numerosDNIClienteCadena = teclado.nextLine();
        } while (!Validaciones.esNumeroEnRango(numerosDNIClienteCadena, LONGITUD_NUMEROS_DNI));
        return Integer.parseInt(numerosDNIClienteCadena);
    }

    public static String ingresarTelefonoCliente() {
        String numerosTelefonoClienteCadena = null;
        do {
            System.out.println(INGRESO_TELEFONO_CLIENTE);
            numerosTelefonoClienteCadena = teclado.nextLine();
        } while (!Validaciones.esNumeroEnRango(numerosTelefonoClienteCadena, LONGITUD_TELEFONO));
        return numerosTelefonoClienteCadena;
    }

    public static String ingresarDireccionCliente() {
        System.out.println(INGRESO_DIRECCION_CLIENTE);
        return Utilidades.formatearString(LONGITUD_DIRECCION_CLIENTE, FORMATO_DIRECCION,teclado.nextLine());
    }


    public static String ingresarOpcionMenuFormato() {
        String eleccion;
        do {
            System.out.println(MENU_FORMATO_FICHERO);
            eleccion = teclado.nextLine();
        } while (!Validaciones.validarMenuFormato(eleccion));
        return eleccion;
    }

    public static void mostrarMensajeDNINoEncontrado() {
        System.out.println(MENSAJE_DNI_NO_ENCONTRADO);
    }
}
