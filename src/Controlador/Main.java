package Controlador;

import Modelo.Entidades.Cliente;
import Modelo.FileAccess.FileAccessCliente;
import Modelo.FileAccess.FileAccessIndiceClientes;
import Modelo.Utilidades.Utilidades;
import Vista.Menu;

public class Main {
    public static void main(String[] args) {
        int eleccion;
        boolean salida = false;

        FileAccessCliente fileAccessCliente = new FileAccessCliente();
        FileAccessIndiceClientes fileAccessIndiceClientes = new FileAccessIndiceClientes();

        do {
            eleccion = Menu.mostrarMenuPrincipalEingresarEntradaMenu();
            switch (eleccion) {
                case 1 -> agregarNuevoCliente(fileAccessCliente);
                case 2 -> consultarCliente();
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

    private static void consultarCliente() {
    }

    private static void borrarCliente() {
    }

    private static void configurarExportacion() {
    }

    private static void exportarClientes() {
    }

}
