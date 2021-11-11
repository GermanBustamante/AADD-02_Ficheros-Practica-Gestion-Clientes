package Controlador;

import Modelo.Entidades.Cliente;
import Modelo.FileAccess.FileAccessCliente;
import Modelo.FileAccess.FileAccessFicheroConfig;
import Modelo.FileAccess.FileAccessIndiceClientes;
import Modelo.Utilidades.Utilidades;
import Vista.Menu;

public class Main {
    //TODO NO DEBE TENER MÁS DE 10 LINEAS Y ENTRE 3-6 ESTA PERFE
    public static void main(String[] args) {
        int eleccion;
        boolean salida = false;

        FileAccessCliente fileAccessCliente = new FileAccessCliente();
        FileAccessIndiceClientes fileAccessIndiceClientes = new FileAccessIndiceClientes();
        FileAccessFicheroConfig fileAccessFicheroConfig = new FileAccessFicheroConfig();

        do {
            eleccion = Menu.mostrarMenuPrincipalEingresarEntradaMenu();
            switch (eleccion) {
                case 1 -> agregarNuevoCliente(fileAccessCliente, fileAccessIndiceClientes);
                case 2 -> consultarCliente(fileAccessCliente,fileAccessIndiceClientes);
                case 3 -> borrarCliente();
                case 4 -> configurarExportacion();
                case 5 -> exportarClientes();
                case 0 -> salida = true;
            }
        } while (!salida);
    }

    private static void agregarNuevoCliente(FileAccessCliente fileAccessCliente, FileAccessIndiceClientes fileAccessIndiceClientes) {
        String nombreCliente = Menu.ingresarNombreCliente();
        String apellidosCliente = Menu.ingresarApellidosCliente();
        String DNICliente = Utilidades.getDNICliente(Menu.ingresarNumerosDNICliente());
        String telefonoCliente = Menu.ingresarTelefonoCliente();
        String direccionCliente = Menu.ingresarDireccionCliente();
        Cliente cliente = new Cliente(nombreCliente, apellidosCliente, DNICliente, telefonoCliente, direccionCliente);
        fileAccessCliente.agregarClienteFichero(cliente);
        fileAccessIndiceClientes.agregarIndiceYDNIFicheroIndiceClientes(cliente.getDNI());
    }

    private static void consultarCliente(FileAccessCliente fileAccessCliente, FileAccessIndiceClientes fileAccessIndiceClientes) {
        String DNICliente = Utilidades.getDNICliente(Menu.ingresarNumerosDNICliente());
        int posicionClienteFicheroIndices = fileAccessIndiceClientes.getPosicionClienteFichero(DNICliente);
        if (posicionClienteFicheroIndices==-1){
            Menu.mostrarMensajeDNINoEncontrado();
        }else {
            //Cliente clienteEncontrado = fileAccessCliente.getClienteDadoIndice(posicionClienteFicheroIndices);
        }
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
