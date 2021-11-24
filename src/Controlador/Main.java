package Controlador;

import Modelo.Entidades.Cliente;
import Modelo.FileAccess.FileAccessCliente;
import Modelo.FileAccess.FileAccessExportacionClientes;
import Modelo.FileAccess.FileAccessConfiguracion;
import Modelo.FileAccess.FileAccessIndiceClientes;
import Modelo.Utilidades.Utilidades;
import Vista.Menu;

import java.io.EOFException;
import java.io.IOException;
import java.util.List;

/**
 * <b>Clase cumplirá el papel de Controladora en nuestro programa MVC<b>
 * NOTA: CUANDO ESCRIBEN DATOS SOLO SE CONTROLA LA IOEXCEPTION, PERO CUANDO SE RECOGE DEBE CONTROLAR
 * LA IO COMO ERROR GENERAL O EOF CUANDO HA TERMINADO DE LEER EN CASO DE QUE EL MÉTODO DEBE LEER EL FICHERO ENTERO
 *
 * @author germanbustamante_
 * @version 1.0
 */
public class Main {

    public static void main(String[] args) {
        int eleccion;
        boolean salida = false;
        FileAccessCliente fileAccessCliente = new FileAccessCliente();
        FileAccessIndiceClientes fileAccessIndiceClientes = new FileAccessIndiceClientes();
        FileAccessConfiguracion fileAccessConfiguracion = new FileAccessConfiguracion();
        FileAccessExportacionClientes fileAccessExportacionClientes = new FileAccessExportacionClientes();
        do {
            eleccion = Menu.mostrarMenuPrincipalEingresarEntradaMenu();
            switch (eleccion) {
                case 1 -> aniadirCliente(fileAccessCliente, fileAccessIndiceClientes);
                case 2 -> consultarOBorrarCliente(fileAccessCliente, fileAccessIndiceClientes, true);
                case 3 -> consultarOBorrarCliente(fileAccessCliente, fileAccessIndiceClientes, false);
                case 4 -> configurarExportacion(fileAccessConfiguracion);
                case 5 -> exportarClientes(fileAccessCliente, fileAccessIndiceClientes, fileAccessExportacionClientes, fileAccessConfiguracion);
                case 0 -> salida = true;
            }
        } while (!salida);
    }

    private static void aniadirCliente(FileAccessCliente fileAccessCliente, FileAccessIndiceClientes fileAccessIndiceClientes) {
        Cliente cliente = pedirDatosClienteInstanciado();
        try {
            fileAccessCliente.escribirClienteFicheroBinario(cliente);
            fileAccessIndiceClientes.escribirIndiceYDNIFicheroBinario(cliente.getDNI());
        } catch (IOException e) {
            Menu.mostrarMensajeError(e.getMessage());
        }
    }

    private static Cliente pedirDatosClienteInstanciado() {
        String nombreCliente = Menu.ingresarNombreCliente();
        String apellidosCliente = Menu.ingresarApellidosCliente();
        String dniCliente = Utilidades.getDNICliente(Menu.ingresarNumerosDNICliente());
        String telefonoCliente = Menu.ingresarTelefonoCliente();
        String direccionCliente = Menu.ingresarDireccionCliente();
        return new Cliente(nombreCliente, apellidosCliente, dniCliente, telefonoCliente, direccionCliente);
    }

    private static void consultarOBorrarCliente(FileAccessCliente fileAccessCliente, FileAccessIndiceClientes fileAccessIndiceClientes, boolean esConsulta) {
        if (Utilidades.existeFichero(fileAccessIndiceClientes.getFichero()) && !Utilidades.estaVacioFichero(fileAccessIndiceClientes.getFichero())) {
            String dniCliente = Utilidades.getDNICliente(Menu.ingresarNumerosDNICliente());
            int posicionClienteFicheroIndices = 0;
            try {
                posicionClienteFicheroIndices = fileAccessIndiceClientes.getPosicionClienteFicheroBinario(dniCliente);
                if (esConsulta){
                    Cliente clienteRecogido = fileAccessCliente.getClienteFicheroBinario(posicionClienteFicheroIndices);
                    Menu.mostrarCliente(clienteRecogido);
                }else {
                    fileAccessIndiceClientes.borrarClienteFicheroBinario(posicionClienteFicheroIndices);
                }
            } catch (EOFException e) {//Cuando no encuentra el indice a borrar
                Menu.mostrarMensaje(Menu.MENSAJE_DNI_NO_ENCONTRADO);
            } catch (IOException e) {
                Menu.mostrarMensajeError(e.getMessage());
            }
        } else {
            Menu.mostrarMensaje(Menu.MENSAJE_FICHERO_INEXISTENTE_VACIO);
        }
    }

    private static void configurarExportacion(FileAccessConfiguracion fileAccessConfiguracion) {
        try {
            fileAccessConfiguracion.escribirFormatoFicheroConfiguracion(Menu.ingresarOpcionMenuFormato());
        } catch (IOException e) {
            Menu.mostrarMensajeError(e.getMessage());
        }
    }

    //TODO MODURALIZAR
    private static void exportarClientes(FileAccessCliente fileAccessCliente, FileAccessIndiceClientes fileAccessIndiceClientes, FileAccessExportacionClientes fileAccessExportacionClientes, FileAccessConfiguracion fileAccessConfiguracion) {
        List<Cliente> listaClientes = null;
        List<Integer> listaIndicesClientesBorrados = null;
        String formato = null;
        //Comprueba si existen y hay datos en los 2 ficheros
        if (Utilidades.existeFichero(fileAccessIndiceClientes.getFichero()) && Utilidades.existeFichero(fileAccessCliente.getFichero())
                && !(Utilidades.estaVacioFichero(fileAccessIndiceClientes.getFichero()) && Utilidades.estaVacioFichero(fileAccessCliente.getFichero()))) {
            try {
                listaIndicesClientesBorrados = fileAccessIndiceClientes.getListaIndicesClientesBorradosFicheroBinario();
                if (listaIndicesClientesBorrados != null) {//Puede dar null por alguna excepción
                    listaClientes = fileAccessCliente.getListaClientesNoBorradosFicheroBinario(listaIndicesClientesBorrados);
                    //Si existe el fichero de configuración y no está vacío
                    if (Utilidades.existeFichero(fileAccessConfiguracion.getFichero()) &&
                            !Utilidades.estaVacioFichero(fileAccessConfiguracion.getFichero()) && listaClientes != null) {
                        formato = fileAccessConfiguracion.getFormatoFicheroConfiguracion();
                        fileAccessExportacionClientes.escribirClientesFicheroTexto(listaClientes, formato);

                    } else if (!(Utilidades.existeFichero(fileAccessConfiguracion.getFichero()) &&
                            !Utilidades.estaVacioFichero(fileAccessConfiguracion.getFichero()))) {//Si la lista de clientes está vacía
                        Menu.mostrarMensaje(Menu.MENSAJE_FICHERO_CONGFIGURACION_NO_LISTO);
                    } else {
                        Menu.mostrarMensaje(Menu.MENSAJE_CLIENTES_NO_CARGADOS);
                    }
                }
            } catch (IOException e) {
                Menu.mostrarMensajeError(e.getMessage());
            }
        } else {
            Menu.mostrarMensaje(Menu.MENSAJE_FICHERO_INEXISTENTE_VACIO);
        }
    }

}
