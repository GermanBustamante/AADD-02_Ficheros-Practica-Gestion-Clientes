package Controlador;

import Modelo.Entidades.Cliente;
import Modelo.FileAccess.FileAccessCliente;
import Modelo.FileAccess.FileAccessExportacionClientes;
import Modelo.FileAccess.FileAccessFicheroConfig;
import Modelo.FileAccess.FileAccessIndiceClientes;
import Modelo.Utilidades.Utilidades;
import Vista.Menu;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
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
            }
        } while (!salida);
    }

    //CUANDO ESCRIBE SOLO SE CONTROLA LA IOEXCEPTION, PERO CUANDO SE RECOGE DEBE CONTROLAR
    //LA IO COMO ERROR GENERAL O EOF COMO QUE HA TERMINADO DE LEER SI EL MÉTODO DEBE LEER EL FICHERO ENTERO

    /**
     * @param fileAccessCliente
     * @param fileAccessIndiceClientes
     */
    //TODO DUDA TRY-CATCH
    private static void agregarNuevoCliente(FileAccessCliente fileAccessCliente, FileAccessIndiceClientes fileAccessIndiceClientes) {
        Cliente cliente = pedirDatosClienteInstanciado();
        try {
            fileAccessCliente.agregarClienteFichero(cliente);
            fileAccessIndiceClientes.agregarIndiceYDNIFicheroIndiceClientes(cliente.getDNI());
        } catch (IOException e) {
            Menu.mostrarMensajeError(e.getMessage());
        }
    }

    private static Cliente pedirDatosClienteInstanciado() {
        String nombreCliente = Menu.ingresarNombreCliente();
        String apellidosCliente = Menu.ingresarApellidosCliente();
        String DNICliente = Utilidades.getDNICliente(Menu.ingresarNumerosDNICliente());
        String telefonoCliente = Menu.ingresarTelefonoCliente();
        String direccionCliente = Menu.ingresarDireccionCliente();
        return new Cliente(nombreCliente, apellidosCliente, DNICliente, telefonoCliente, direccionCliente);
    }

    /**
     * @param fileAccessCliente
     * @param fileAccessIndiceClientes
     */
    private static void consultarCliente(FileAccessCliente fileAccessCliente, FileAccessIndiceClientes fileAccessIndiceClientes) {
        if (Utilidades.existeFichero(fileAccessIndiceClientes.getFicheroIndiceClientes()) && Utilidades.existeFichero(fileAccessCliente.getFicheroClientes())
                && !(Utilidades.estaVacioFichero(fileAccessIndiceClientes.getFicheroIndiceClientes()) && Utilidades.estaVacioFichero(fileAccessCliente.getFicheroClientes()))) {
            String DNICliente = Utilidades.getDNICliente(Menu.ingresarNumerosDNICliente());
            int posicionClienteFicheroIndices = 0;
            try {
                posicionClienteFicheroIndices = fileAccessIndiceClientes.getPosicionClienteFichero(DNICliente);
                Cliente clienteRecogido = fileAccessCliente.getClienteDadoIndice(posicionClienteFicheroIndices);
                Menu.mostrarCliente(clienteRecogido);
            } catch (EOFException e) {//Llega cuando no ha encontrado el DNI
                Menu.mostrarMensaje(Menu.MENSAJE_DNI_NO_ENCONTRADO);
            } catch (IOException e) {
                Menu.mostrarMensajeError(e.getMessage());
            }
        } else {
            Menu.mostrarMensaje(Menu.MENSAJE_FICHERO_INEXISTENTE_VACIO);
        }
    }

    //TODO MODURALIZAR CON EL DE ARRIBA
    private static void borrarCliente(FileAccessIndiceClientes fileAccessIndiceClientes) {
        if (Utilidades.existeFichero(fileAccessIndiceClientes.getFicheroIndiceClientes()) && !Utilidades.estaVacioFichero(fileAccessIndiceClientes.getFicheroIndiceClientes())) {
            String DNICliente = Utilidades.getDNICliente(Menu.ingresarNumerosDNICliente());
            int posicionClienteFicheroIndices = 0;
            try {
                posicionClienteFicheroIndices = fileAccessIndiceClientes.getPosicionClienteFichero(DNICliente);
                fileAccessIndiceClientes.borrarClienteFicheroIndices(posicionClienteFicheroIndices);
            } catch (EOFException e) {//Cuando no encuentra el indice a borrar
                Menu.mostrarMensaje(Menu.MENSAJE_DNI_NO_ENCONTRADO);
            } catch (IOException e) {
                Menu.mostrarMensajeError(e.getMessage());
            }
        } else {
            Menu.mostrarMensaje(Menu.MENSAJE_FICHERO_INEXISTENTE_VACIO);
        }
    }

    private static void configurarExportacion(FileAccessFicheroConfig fileAccessFicheroConfig) {
        try {
            fileAccessFicheroConfig.escribirFormatoFicheroConfiguracion(Menu.ingresarOpcionMenuFormato());
        } catch (IOException e) {
            Menu.mostrarMensajeError(e.getMessage());
        }
    }

    //TODO MODURALIZAR
    private static void exportarClientes(FileAccessCliente fileAccessCliente, FileAccessIndiceClientes fileAccessIndiceClientes, FileAccessExportacionClientes fileAccessExportacionClientes, FileAccessFicheroConfig fileAccessFicheroConfig) {
        List<Cliente> listaClientes = null;
        List<Integer> listaIndicesClientesBorrados = null;
        String formato = null;
        if (Utilidades.existeFichero(fileAccessIndiceClientes.getFicheroIndiceClientes()) && Utilidades.existeFichero(fileAccessCliente.getFicheroClientes())) {
            try {
                listaIndicesClientesBorrados = fileAccessIndiceClientes.getListaIndicesClientesBorrados();
            } catch (IOException e) {
                Menu.mostrarMensajeError(e.getMessage());
            }
            if (listaIndicesClientesBorrados != null) {
                try {
                    listaClientes = fileAccessCliente.getListaFicheroClientes(listaIndicesClientesBorrados);
                } catch (IOException e) {
                    Menu.mostrarMensajeError(e.getMessage());
                }
                if (Utilidades.existeFichero(fileAccessFicheroConfig.getFichero()) &&
                        !Utilidades.estaVacioFichero(fileAccessFicheroConfig.getFichero()) && listaClientes!=null) {
                    try {
                        formato = fileAccessFicheroConfig.getFormatoFicheroConfiguracion();
                        fileAccessExportacionClientes.escribirClientesFicheroTexto(listaClientes, formato);
                    } catch (IOException e) {
                        Menu.mostrarMensajeError(e.getMessage());
                    }
                }else if (!(Utilidades.existeFichero(fileAccessFicheroConfig.getFichero()) &&
                        !Utilidades.estaVacioFichero(fileAccessFicheroConfig.getFichero()))){//Si la lista de clientes está vacía
                    Menu.mostrarMensaje("EL FICHERO DE CONFIG NO ESTÁ LISTO :(");
                }else {
                    System.out.println("NO HAY CLIENTES WACHIN");
                }
            }
        } else {
            Menu.mostrarMensaje(Menu.MENSAJE_FICHERO_INEXISTENTE_VACIO);
        }
    }

}
