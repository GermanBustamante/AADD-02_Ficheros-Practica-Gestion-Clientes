package Vista;

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
    public static final int MINIMA_ELECCION_MENU_PRINCIPAL = 0;
    public static final int MAXIMA_ELECCION_MENU_PRINCIPAL = 5;
    public static final int MINIMA_LONGITUD_CADENA= 1;
    public static final int MAXIMA_LONGITUD_NOMBRE = 25;
    public static final int MAXIMA_LONGITUD_APELLIDOS = 25;
    public static final int LONGITUD_NUMEROS_DNI = 8;
    public static final int LONGITUD_TELEFONO = 9;
    public static final int MAXIMA_LONGITUD_DIRECCION = 30;

    public static final String INGRESO_NOMBRE_CLIENTE = "Ingresa el nombre del cliente";
    public static final String INGRESO_APELLIDOS_CLIENTE = "Ingresa los apellidos del cliente";
    private static final String INGRESO_DIRECCION_CLIENTE = "Ingresa la dirección del cliente";

    private static Scanner teclado = new Scanner(System.in);

    public static int mostrarMenuPrincipalEingresarEntradaMenu() {
        String eleccionCadena = null;
        do {
            System.out.println(MENU_PRINCIPAL);
            eleccionCadena = teclado.nextLine();
        }while (!Validaciones.esNumeroEnRango(eleccionCadena, MINIMA_ELECCION_MENU_PRINCIPAL, MAXIMA_ELECCION_MENU_PRINCIPAL));
        return Integer.parseInt(eleccionCadena);
    }

    public static String ingresarNombreCliente() {
        String nombreCliente;
        do {
            System.out.println(INGRESO_NOMBRE_CLIENTE);
            nombreCliente = teclado.nextLine();
        }while (Validaciones.longitudCadenaCorrecta(nombreCliente, MAXIMA_LONGITUD_NOMBRE));
        return nombreCliente;
    }

    public static String ingresarApellidosCliente() {
        String apellidosCliente;
        do {
            System.out.println(INGRESO_APELLIDOS_CLIENTE);
            apellidosCliente = teclado.nextLine();
        }while (Validaciones.longitudCadenaCorrecta(apellidosCliente, MAXIMA_LONGITUD_APELLIDOS));
        return apellidosCliente;
    }

    public static int ingresarNumerosDNICliente() {
        String numerosDNIClienteCadena = null;
        do {
            System.out.println(MENU_PRINCIPAL);
            numerosDNIClienteCadena = teclado.nextLine();
        }while (!Validaciones.esNumeroEnRango(numerosDNIClienteCadena,LONGITUD_NUMEROS_DNI));
        return Integer.parseInt(numerosDNIClienteCadena);
    }

    public static String  ingresarTelefonoCliente() {
        String numerosTelefonoClienteCadena = null;
        do {
            System.out.println(MENU_PRINCIPAL);
            numerosTelefonoClienteCadena = teclado.nextLine();
        }while (!Validaciones.esNumeroEnRango(numerosTelefonoClienteCadena,LONGITUD_TELEFONO));
        return numerosTelefonoClienteCadena;
    }

    public static String ingresarDireccionCliente() {
        String direccionCliente;
        do {
            System.out.println(INGRESO_DIRECCION_CLIENTE);
            direccionCliente = teclado.nextLine();
        }while (Validaciones.longitudCadenaCorrecta(direccionCliente, MAXIMA_LONGITUD_DIRECCION));
        return direccionCliente;
    }


}
