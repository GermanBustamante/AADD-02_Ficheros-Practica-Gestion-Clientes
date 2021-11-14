package Modelo.FileAccess;

import Modelo.Entidades.Cliente;

import java.io.*;
import java.util.List;

public class FileAccessExportacionClientes {
    private final File fichero;

    public static final String RUTA_FICHERO_CLIENTES_TEXTO = "clientes.txt";
    public FileAccessExportacionClientes() {
        this.fichero = new File(RUTA_FICHERO_CLIENTES_TEXTO);
    }

    public void escribirClientesFicheroTexto(List<Cliente> listaClientes, String formatoFichero) {
        try(BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.fichero), formatoFichero))){
            for (Cliente cliente:listaClientes) {
                bufferedWriter.write(cliente.toString());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
