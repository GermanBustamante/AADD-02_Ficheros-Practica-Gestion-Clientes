package Controlador;

import Modelo.Entidades.Cliente;
import Modelo.FileAccess.FileAccessCliente;
import Modelo.FileAccess.FileAccessFicheroConfig;
import Modelo.FileAccess.FileAccessIndiceClientes;
import Modelo.Utilidades.Utilidades;
import Vista.Menu;

public class Main {
    public static void main(String[] args) {
        int eleccion;
        boolean salida = false;

        FileAccessCliente fileAccessCliente = new FileAccessCliente();
        FileAccessIndiceClientes fileAccessIndiceClientes = new FileAccessIndiceClientes();
        FileAccessFicheroConfig fileAccessFicheroConfig = new FileAccessFicheroConfig();

        do {
            eleccion = Menu.mostrarMenuPrincipalEingresarEntradaMenu();
            switch (eleccion) {
                case 1 -> agregarNuevoCliente(fileAccessCliente);
                case 2 -> consultarCliente(fileAccessIndiceClientes);
                case 3 -> borrarCliente();
                case 4 -> configurarExportacion();
                case 5 -> exportarClientes();
                case 0 -> salida = true;
            }
        } while (!salida);
    }

    private static void agregarNuevoCliente(FileAccessCliente fileAccessCliente) {
        String nombreCliente = Menu.ingresarNombreCliente();
        String apellidosCliente = Menu.ingresarApellidosCliente();
        String DNICliente = Utilidades.getDNICliente(Menu.ingresarNumerosDNICliente());
        String telefonoCliente = Menu.ingresarTelefonoCliente();
        String direccionCliente = Menu.ingresarDireccionCliente();
        Cliente cliente = new Cliente(nombreCliente,apellidosCliente,DNICliente,telefonoCliente,direccionCliente);
        fileAccessCliente.agregarClienteFichero(cliente);
    }

    private static void consultarCliente(FileAccessIndiceClientes fileAccessIndiceClientes) {
        String DNICliente = Utilidades.getDNICliente(Menu.ingresarNumerosDNICliente());
        int indiceCliente = fileAccessIndiceClientes.getIndiceCliente(DNICliente);
        /*
        Busca en el fichero de indices el DNI del cliente,y si lo encuentra, devolverá un valor, dicho valor
        lo tomará otro método con RamdomAccessFile saltará hasta el registro X y ahí tendrá el cliente
        */
    }

    private static void borrarCliente() {
        /*
        * Setearemos la letra del DNI del ficheroClientes y ficheroIndices a "O" y cuando le de a actualizarDatos()
        * creará un nuevo fichero con todos los datos menos los DNIs q tengan la O.
        * NOTA: Atributo que recoja el número de cambios q se deben hacer TODO nose como lo haría
        * */
    }

    private static void configurarExportacion() {
        String formatoFichero = Menu.ingresarOpcionMenuFormato();
    }

    private static void exportarClientes() {

    }

}
