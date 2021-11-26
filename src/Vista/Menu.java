package Vista;

import Modelo.Entidades.Cliente;
import Modelo.Utilidades.Utilidades;

import java.util.Scanner;
/**
 * Clase que contendrá métodos estáticos y constantes para imprimir y validar
 * @author germanbustamante_
 * @version 1.0
 */
public class Menu {
    //Constantes
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
    public static final String MENU_FICHERO_CONFIGURACION = """
                    1 -> US-ASCII
                    2 -> ISO-8859-1
                    3 -> UTF-8
                    4 -> UTF-16BE
                    5 -> UTF-16LE
                    6 -> UFT-16
                    NOTA: SI NO COINCIDE NINGUNO DE LOS DATOS, SE TOMARA POR DEFECTO UTF-8
                    """;
    public static final String MENSAJE_DNI_NO_ENCONTRADO = "No se ha encontrado ningún DNI";
    public static final String US_ASCII = "US-ASCII";
    public static final String ISO_8859_1 = "ISO-8859-1";
    public static final String UTF_8 = "UTF-8";
    public static final String UTF_16BE = "UTF-16BE";
    public static final String UTF_16LE = "UTF-16LE";
    public static final String UTF_16 = "UTF-16";
    public static final String INGRESO_NOMBRE_CLIENTE = "Ingresa el nombre del cliente";
    public static final String INGRESO_APELLIDOS_CLIENTE = "Ingresa los apellidos del cliente";
    public static final String INGRESO_DIRECCION_CLIENTE = "Ingresa la dirección del cliente";
    public static final String INGRESO_DNI_CLIENTE = "Ingresa el DNI del cliente";
    public static final String INGRESO_TELEFONO_CLIENTE = "Ingresa el teléfono del cliente";
    public static final String FORMATO_NOMBRE = "%-25s";
    public static final String FORMATO_APELLIDOS = "%-25s";
    public static final String FORMATO_DIRECCION = "%-30s";
    public static final String MENSAJE_FICHERO_INEXISTENTE_VACIO = "Los ficheros a buscar no existen o están vacíos, rellenelos o creelos añadiendo algún cliente";
    public static final String MENSAJE_FICHERO_CONGFIGURACION_NO_LISTO = "El fichero de configuración no está preparado";
    public static final String MENSAJE_CLIENTES_NO_CARGADOS = "Los clientes no se han cargado correctamente";
    public static final int MINIMA_ELECCION_MENU_PRINCIPAL = 0;
    public static final int MAXIMA_ELECCION_MENU_PRINCIPAL = 5;
    public static final int LONGITUD_NOMBRE_CLIENTE = 25;
    public static final int LONGITUD_APELLIDOS_CLIENTE = 25;
    public static final int LONGITUD_DIRECCION_CLIENTE = 30;
    public static final int LONGITUD_NUMEROS_DNI = 8;
    public static final int LONGITUD_TELEFONO = 9;

    //Atributos
    private static Scanner teclado = new Scanner(System.in);

    //Metodos públicos
    /**
     * <b>Cabecera:</b> public static int imprimirMenuPrincipalEingresarOpcion()<br/>
     * <b>Precondiciones:</b> ninguna<br/>
     * <b>Postcondiciones:</b> Imprime por pantalla el menú principal y pide por teclado una elección, no saldrá de dicho método hasta que elija una opción válida<br/>
     * @return eleccionCadena la elección que ha dado el usuario en forma numérica
     */
    public static int imprimirMenuPrincipalEingresarOpcion() {
        String eleccionCadena = null;
        do {
            System.out.println(MENU_PRINCIPAL);
            eleccionCadena = teclado.nextLine();
        } while (!Validaciones.esNumeroEnRango(eleccionCadena, MINIMA_ELECCION_MENU_PRINCIPAL, MAXIMA_ELECCION_MENU_PRINCIPAL));
        return Integer.parseInt(eleccionCadena);
    }

    /**
     * <b>Cabecera:</b> public static String obtenerNombreClienteValido()<br/>
     * <b>Precondiciones:</b> ninguna<br/>
     * <b>Postcondiciones:</b> Imprime un mensaje al usuario diciendo que ingrese un nombre, y hasta que ingrese algún carácter no saldrá, al final, devolverá dicha cadena <br/>
     * @return String, el nombre formateado dependiendo de la longitud y el formato
     */
    public static String obtenerNombreClienteValido() {
        String nombre = null;
        do {
            System.out.println(INGRESO_NOMBRE_CLIENTE);
            nombre = teclado.nextLine();
        } while (Validaciones.esCadenaVacia(nombre));
        return Utilidades.formatearString(LONGITUD_NOMBRE_CLIENTE, FORMATO_NOMBRE, nombre);
    }

    /**
     * <b>Cabecera:</b> public static String obtenerApellidosClienteValido()<br/>
     * <b>Precondiciones:</b> ninguna<br/>
     * <b>Postcondiciones:</b> Imprime un mensaje al usuario diciendo que ingrese unos apellidos, y hasta que ingrese algún carácter no saldrá, al final, devolverá dicha cadena <br/>
     * @return String, los apellidos formateados dependiendo de la longitud y el formato
     */
    public static String obtenerApellidosClienteValido() {
        String apellidos = null;
        do {
            System.out.println(INGRESO_APELLIDOS_CLIENTE);
            apellidos = teclado.nextLine();
        } while (Validaciones.esCadenaVacia(apellidos));
        return Utilidades.formatearString(LONGITUD_APELLIDOS_CLIENTE, FORMATO_APELLIDOS, apellidos);
    }

    /**
     * <b>Cabecera:</b> public static String obtenerApellidosClienteValido()<br/>
     * <b>Precondiciones:</b> ninguna<br/>
     * <b>Postcondiciones:</b> Imprime un mensaje al usuario diciendo que ingrese los números del dni, y hasta que ingrese unos datos válidos no saldrá del método, una vez validado los devuelve <br/>
     * @return int números del dni validados
     */
    public static int obtenerNumerosDNIClienteValido() {
        String numerosDNIClienteCadena = null;
        do {
            System.out.println(INGRESO_DNI_CLIENTE);
            numerosDNIClienteCadena = teclado.nextLine();
        } while (Validaciones.esNumeroEnRango(numerosDNIClienteCadena, LONGITUD_NUMEROS_DNI));
        return Integer.parseInt(numerosDNIClienteCadena);
    }

    /**
     * <b>Cabecera:</b> public static String obtenerTelefonoClienteValido()<br/>
     * <b>Precondiciones:</b> ninguna<br/>
     * <b>Postcondiciones:</b> Imprime un mensaje al usuario diciendo que ingrese los números de teléfono, y hasta que ingrese unos datos válidos no saldrá del método, una vez validado los devuelve <br/>
     * @return int números del teléfono validados
     */
    public static String obtenerTelefonoClienteValido() {
        String numerosTelefonoClienteCadena = null;
        do {
            System.out.println(INGRESO_TELEFONO_CLIENTE);
            numerosTelefonoClienteCadena = teclado.nextLine();
        } while (Validaciones.esNumeroEnRango(numerosTelefonoClienteCadena, LONGITUD_TELEFONO));
        return numerosTelefonoClienteCadena;
    }

    /**
     * <b>Cabecera:</b> public static String obtenerDireccionClienteValido()<br/>
     * <b>Precondiciones:</b> ninguna<br/>
     * <b>Postcondiciones:</b> Imprime un mensaje al usuario diciendo que ingrese una dirección, y hasta que ingrese algún carácter no saldrá, al final, devolverá dicha cadena <br/>
     * @return String, la dirección formateada dependiendo de la longitud y el formato
     */
    public static String obtenerDireccionClienteValido() {
        String direccion = null;
        do {
            System.out.println(INGRESO_DIRECCION_CLIENTE);
            direccion = teclado.nextLine();
        } while (Validaciones.esCadenaVacia(direccion));
        return Utilidades.formatearString(LONGITUD_DIRECCION_CLIENTE, FORMATO_DIRECCION, direccion);
    }

    /**
     * <b>Cabecera:</b> public static String imprimirMenuConfiguracionEingresarOpcion()<br/>
     * <b>Precondiciones:</b> ninguna<br/>
     * <b>Postcondiciones:</b> Imprime por pantalla el menú de configuración y pide por teclado una elección, no saldrá de dicho método hasta que elija una opción válida<br/>
     * @return eleccion, la elección que ha dado el usuario en forma de cadena
     */
    public static String imprimirMenuConfiguracionEingresarOpcion() {
        String eleccion;
        System.out.println(MENU_FICHERO_CONFIGURACION);
        eleccion = teclado.nextLine();
        if (!Validaciones.validarMenuFormato(eleccion)) {//Si no cumple ninguno de los formatos
            eleccion = UTF_8;
        }
        return eleccion;
    }

    /**
     * <b>Cabecera:</b> public static void imprimirCliente(Cliente cliente)<br/>
     * <b>Precondiciones:</b> ninguna<br/>
     * <b>Postcondiciones:</b> Dado un cliente, imprime por pantalla su Cliente.toString()<br/>
     * @param cliente cliente a  imprimir
     */
    public static void imprimirCliente(Cliente cliente) {
        System.out.println(cliente);
    }

    /**
     * <b>Cabecera:</b> public static void imprimirMensajeError(String message)<br/>
     * <b>Precondiciones:</b> ninguna<br/>
     * <b>Postcondiciones:</b> Dado una cadena, la imprime por pantalla en formato de error<br/>
     * @param message cadena a imprimir
     */
    public static void imprimirMensajeError(String message) {
        System.err.println(message);
    }

    /**
     * <b>Cabecera:</b> public static void imprimirMensaje(String message)<br/>
     * <b>Precondiciones:</b> ninguna<br/>
     * <b>Postcondiciones:</b> Dado una cadena, la imprime por pantalla<br/>
     * @param message cadena a imprimir
     */
    public static void imprimirMensaje(String message) {
        System.out.println(message);
    }
}
