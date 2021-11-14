package Controlador;

import Modelo.Entidades.Cliente;
import Modelo.FileAccess.FileAccessCliente;
import Modelo.FileAccess.FileAccessExportacionClientes;
import Modelo.FileAccess.FileAccessFicheroConfig;
import Modelo.FileAccess.FileAccessIndiceClientes;
import Modelo.Utilidades.Utilidades;
import Vista.Menu;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int eleccion;
        boolean salida = false;

        //TODO NO TERNERLOS AQUÍ MÉTIDOS HOMBRE O INSTANCIAR SEGÚN ENTRE EN CADA MÉTODO
        FileAccessCliente fileAccessCliente = new FileAccessCliente();
        FileAccessIndiceClientes fileAccessIndiceClientes = new FileAccessIndiceClientes();
        FileAccessFicheroConfig fileAccessFicheroConfig = new FileAccessFicheroConfig();
        FileAccessExportacionClientes fileAccessExportacionClientes = new FileAccessExportacionClientes();

        do {
            eleccion = Menu.mostrarMenuPrincipalEingresarEntradaMenu();
            switch (eleccion) {
                case 1 -> agregarNuevoCliente(fileAccessCliente, fileAccessIndiceClientes);
                case 2 -> consultarCliente(fileAccessCliente, fileAccessIndiceClientes);
                case 3 -> borrarCliente(fileAccessIndiceClientes);
                case 4 -> configurarExportacion(fileAccessFicheroConfig);
                case 5 -> exportarClientes(fileAccessCliente, fileAccessIndiceClientes, fileAccessExportacionClientes, fileAccessFicheroConfig);
                case 0 -> salida = true;
                case 6 -> getDatosDefectos(fileAccessCliente, fileAccessIndiceClientes, fileAccessFicheroConfig, fileAccessExportacionClientes);
            }
        } while (!salida);
    }

    /**
     * @param fileAccessCliente
     * @param fileAccessIndiceClientes TERMINADO
     */
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

    /**
     * @param fileAccessCliente
     * @param fileAccessIndiceClientes
     */
    private static void consultarCliente(FileAccessCliente fileAccessCliente, FileAccessIndiceClientes fileAccessIndiceClientes) {
        if (Utilidades.existeFichero(FileAccessIndiceClientes.RUTA_FICHERO_INDICE_CLIENTES)) {
            String DNICliente = Utilidades.getDNICliente(Menu.ingresarNumerosDNICliente());
            int posicionClienteFicheroIndices = fileAccessIndiceClientes.getPosicionClienteFichero(DNICliente);

            if (posicionClienteFicheroIndices == -1) {
                Menu.mostrarMensajeDNINoEncontrado();
            } else if (posicionClienteFicheroIndices == 0) {
                Menu.motrarMensajeFicheroVacio();
            } else {
                Cliente clienteRecogido = fileAccessCliente.getClienteDadoIndice(posicionClienteFicheroIndices);
                Menu.mostrarCliente(clienteRecogido);
            }
        } else {
            Menu.mostrarMensajeFicheroInexistente();
        }

    }

    //TODO MODURALIZAR CON EL DE ARRIBA
    private static void borrarCliente(FileAccessIndiceClientes fileAccessIndiceClientes) {
        if (Utilidades.existeFichero(FileAccessIndiceClientes.RUTA_FICHERO_INDICE_CLIENTES)) {
            String DNICliente = Utilidades.getDNICliente(Menu.ingresarNumerosDNICliente());
            int posicionClienteFicheroIndices = fileAccessIndiceClientes.getPosicionClienteFichero(DNICliente);
            if (posicionClienteFicheroIndices == -1) {
                Menu.mostrarMensajeDNINoEncontrado();
            } else if (posicionClienteFicheroIndices == 0) {
                Menu.motrarMensajeFicheroVacio();
            } else {
                fileAccessIndiceClientes.borrarClienteFicheroIndices(posicionClienteFicheroIndices);
            }
        } else {
            Menu.mostrarMensajeFicheroInexistente();
        }
    }


    private static void configurarExportacion(FileAccessFicheroConfig fileAccessFicheroConfig) {
        fileAccessFicheroConfig.escribirFormatoFicheroConfiguracion(Menu.ingresarOpcionMenuFormato());
    }

    private static void exportarClientes(FileAccessCliente fileAccessCliente, FileAccessIndiceClientes fileAccessIndiceClientes, FileAccessExportacionClientes fileAccessExportacionClientes, FileAccessFicheroConfig fileAccessFicheroConfig) {
        List<Cliente> listaClientes = null;
        if (Utilidades.existeFichero(FileAccessIndiceClientes.RUTA_FICHERO_INDICE_CLIENTES) && Utilidades.existeFichero(FileAccessCliente.RUTA_FICHERO_CLIENTES)){
            listaClientes = fileAccessCliente.getListaFicheroClientes(fileAccessIndiceClientes.getListaIndicesClientesBorrados());
            if (Utilidades.existeFichero(FileAccessFicheroConfig.RUTA_FICHERO_CONFIGURACION)){
                fileAccessExportacionClientes.escribirClientesFicheroTexto(listaClientes, fileAccessFicheroConfig.getFormatoFicheroConfiguracion());
            }else {
                fileAccessExportacionClientes.escribirClientesFicheroTexto(listaClientes, Menu.UTF_8);
            }
        }else {
            Menu.mostrarMensajeFicheroInexistente();
        }
    }

    private static void getDatosDefectos(FileAccessCliente fileAccessCliente, FileAccessIndiceClientes fileAccessIndiceClientes, FileAccessFicheroConfig fileAccessFicheroConfig, FileAccessExportacionClientes fileAccessExportacionClientes){
        List<Cliente> rellenarClientesDefecto = new ArrayList<>();
        rellenarClientesDefecto.add(new Cliente("German                   ", "De Bustamante            ", "29565540Y", "674096436", "Virgen de Luján 39A      "));
        rellenarClientesDefecto.add(new Cliente("Fernando                 ", "Galiana                  ", "25447789Z", "671444852", "Binding 8                "));
        rellenarClientesDefecto.add(new Cliente("Paco                     ", "Para                     ", "254789547D","678254369","Plaza Cuba 84             "));
        rellenarClientesDefecto.add(new Cliente("Pepe                     ", "Mel                      ", "24785147W", "674088822", "Simanca 52               "));
        rellenarClientesDefecto.add(new Cliente("Luis                     ", "Versacce                 ", "24785447A", "617699449", "Bloste 37                "));

        for (Cliente c: rellenarClientesDefecto){
            fileAccessCliente.agregarClienteFichero(c);
            fileAccessIndiceClientes.agregarIndiceYDNIFicheroIndiceClientes(c.getDNI());
        }

    }

}
