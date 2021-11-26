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
 * Clase cumplirá el papel de Controladora en nuestro programa MVC<br/>
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
            eleccion = Menu.imprimirMenuPrincipalEingresarOpcion();
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

    //Recoge un cliente con los datos pasados por la clase Menú y luego escribe el cliente completo en el fichero de clientes
    //y el dni con su correspondiente índice en el fichero de índices
    private static void aniadirCliente(FileAccessCliente fileAccessCliente, FileAccessIndiceClientes fileAccessIndiceClientes) {
        Cliente cliente = obtenerDatosClienteInstanciado();
        try {
            fileAccessCliente.escribirClienteFicheroBinario(cliente);
            fileAccessIndiceClientes.escribirIndiceYDNIFicheroBinario(cliente.getDni());
        } catch (IOException e) {
            Menu.imprimirMensajeError(e.getMessage());
        }
    }

    //Obtiene los datos del cliente a partir de la clase Menú y retorna un cliente instanciado
    private static Cliente obtenerDatosClienteInstanciado() {
        String nombreCliente = Menu.obtenerNombreClienteValido();
        String apellidosCliente = Menu.obtenerApellidosClienteValido();
        String dniCliente = Utilidades.getDNICompleto(Menu.obtenerNumerosDNIClienteValido());
        String telefonoCliente = Menu.obtenerTelefonoClienteValido();
        String direccionCliente = Menu.obtenerDireccionClienteValido();
        return new Cliente(nombreCliente, apellidosCliente, dniCliente, telefonoCliente, direccionCliente);
    }

    //Dado una busqueda de un cliente en el fichero de índices, consulta y muestra por pantalla un cliente o lo borra del fichero índices,
    //en caso de no existir o estar vacío algún fichero que deba usar, avisará al usuario y saldrá del método
    private static void consultarOBorrarCliente(FileAccessCliente fileAccessCliente, FileAccessIndiceClientes fileAccessIndiceClientes, boolean esConsulta) {
        if (Utilidades.existeFichero(fileAccessIndiceClientes.getFichero()) && !Utilidades.estaVacioFichero(fileAccessIndiceClientes.getFichero())) {
            String dniCliente = Utilidades.getDNICompleto(Menu.obtenerNumerosDNIClienteValido());
            int posicionClienteFicheroIndices = 0;
            try {
                posicionClienteFicheroIndices = fileAccessIndiceClientes.getPosicionClienteFicheroBinario(dniCliente);
                if (esConsulta) {
                    Cliente clienteRecogido = fileAccessCliente.getClienteFicheroBinario(posicionClienteFicheroIndices);
                    Menu.imprimirCliente(clienteRecogido);
                } else {
                    fileAccessIndiceClientes.borrarClienteFicheroBinario(posicionClienteFicheroIndices);
                }
            } catch (EOFException e) {//Cuando no encuentra el indice a borrar
                Menu.imprimirMensaje(Menu.MENSAJE_DNI_NO_ENCONTRADO);
            } catch (IOException e) {
                Menu.imprimirMensajeError(e.getMessage());
            }
        } else {
            Menu.imprimirMensajeError(Menu.MENSAJE_FICHERO_INEXISTENTE_VACIO);
        }
    }

    //Escribe un formato en el fichero de configuración
    private static void configurarExportacion(FileAccessConfiguracion fileAccessConfiguracion) {
        try {
            fileAccessConfiguracion.escribirFormatoFicheroConfiguracion(Menu.imprimirMenuConfiguracionEingresarOpcion());
        } catch (IOException e) {
            Menu.imprimirMensajeError(e.getMessage());
        }
    }

    //TODO MODURALIZAR
    //Recoge una lista de clientes del fichero de clientes y lo escribe en un fichero de texto "clientes.txt" en el formato recogido en el fichero de configuración,
    //en caso de no existir o estar vacío algún fichero que deba usar, avisará al usuario y saldrá del método
    private static void exportarClientes(FileAccessCliente fileAccessCliente, FileAccessIndiceClientes fileAccessIndiceClientes, FileAccessExportacionClientes fileAccessExportacionClientes, FileAccessConfiguracion fileAccessConfiguracion) {
        List<Cliente> listaClientes = null;
        List<Integer> listaIndicesClientesBorrados = null;
        String formato = null;
        //Comprueba si existen y hay datos en los 2 ficheros
        if (Utilidades.existeFichero(fileAccessIndiceClientes.getFichero()) && Utilidades.existeFichero(fileAccessCliente.getFichero())
                && !(Utilidades.estaVacioFichero(fileAccessIndiceClientes.getFichero()) && Utilidades.estaVacioFichero(fileAccessCliente.getFichero()))) {
            try {
                listaIndicesClientesBorrados = fileAccessIndiceClientes.getListaIndicesClientesBorradosFicheroBinario();
                if (listaIndicesClientesBorrados != null) {//Puede dar null por haber lanzado alguna excepción
                    listaClientes = fileAccessCliente.getListaClientesNoBorradosFicheroBinario(listaIndicesClientesBorrados);
                    if (Utilidades.existeFichero(fileAccessConfiguracion.getFichero()) &&  //Si existe el fichero de configuración y no está vacío
                            !Utilidades.estaVacioFichero(fileAccessConfiguracion.getFichero()) && listaClientes != null) {
                        formato = fileAccessConfiguracion.getFormatoFicheroConfiguracion();
                        fileAccessExportacionClientes.escribirClientesFicheroTexto(listaClientes, formato);
                    } else if (!(Utilidades.existeFichero(fileAccessConfiguracion.getFichero()) &&
                            !Utilidades.estaVacioFichero(fileAccessConfiguracion.getFichero()))) {//Si la lista de clientes está vacía
                        Menu.imprimirMensaje(Menu.MENSAJE_FICHERO_CONGFIGURACION_NO_LISTO);
                    } else {
                        Menu.imprimirMensaje(Menu.MENSAJE_CLIENTES_NO_CARGADOS);
                    }
                }
            } catch (IOException e) {
                Menu.imprimirMensajeError(e.getMessage());
            }
        } else {
            Menu.imprimirMensajeError(Menu.MENSAJE_FICHERO_INEXISTENTE_VACIO);
        }
    }

}
