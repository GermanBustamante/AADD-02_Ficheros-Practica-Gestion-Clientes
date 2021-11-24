package Modelo.FileAccess;

import Modelo.Entidades.Cliente;
import Modelo.Utilidades.Utilidades;

import java.io.*;
import java.util.List;

public class FileAccessExportacionClientes {
    //Atributos
    private final File fichero;
    //Constantes
    public static final String RUTA_FICHERO_CLIENTES_TEXTO = "clientes.txt";
    //Constructores
    public FileAccessExportacionClientes() {
        this.fichero = new File(RUTA_FICHERO_CLIENTES_TEXTO);
    }
    //Metodos públicos
    public void escribirClientesFicheroTexto(List<Cliente> listaClientes, String formatoFichero) throws IOException {
        OutputStreamWriter outputStreamWriter;
        try (FileOutputStream fileOutputStream = new FileOutputStream(this.fichero)) {
            outputStreamWriter = new OutputStreamWriter(fileOutputStream, formatoFichero);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            for (Cliente cliente : listaClientes) {
                bufferedWriter.write(cliente.toString());
                bufferedWriter.newLine();
            }
        }
    }
}
