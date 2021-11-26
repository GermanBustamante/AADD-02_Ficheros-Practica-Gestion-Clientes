package Modelo.FileAccess;

import Modelo.Entidades.Cliente;

import java.io.*;
import java.util.List;
/**
 * Clase que nos servirá para gestionar el fichero "clientes.txt" donde gestionaremos la forma de escribir la exportación de cliente de nuestro programa
 * @author germanbustamante_
 * @version 1.0
 */
public class FileAccessExportacionClientes {
    //Atributos
    private final File fichero;

    //Constantes
    public static final String RUTA_FICHERO_CLIENTES_TEXTO = ".//Ficheros//clientes.txt";

    //Constructores
    public FileAccessExportacionClientes() {
        this.fichero = new File(RUTA_FICHERO_CLIENTES_TEXTO);
    }
    public FileAccessExportacionClientes(String rutaFichero) {
        this.fichero = new File(rutaFichero);
    }

    //Getters
    public File getFichero() {
        return fichero;
    }

    //Metodos públicos
    /**
     * <b>Cabecera:</b> public void escribirClientesFicheroTexto(List*Cliente* listaClientes, String formatoFichero)<br/>
     * <b>Precondiciones:</b> ninguna<br/>
     * <b>Postcondiciones:</b> Dado una lista de clientes y un formato, escribe en el fichero correspondiente dichos clientes
     * en dicho formato<br/>
     * @param listaClientes clientes a escribir
     * @throws IOException ha ocurrido un error con el flujo de datos
     */
    public void escribirClientesFicheroTexto(List<Cliente> listaClientes, String formatoFichero) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(this.fichero);
             OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, formatoFichero);
             BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter)) {
            for (Cliente cliente : listaClientes) {
                bufferedWriter.write(cliente.toString());
                bufferedWriter.newLine();
            }
        }
    }
}
