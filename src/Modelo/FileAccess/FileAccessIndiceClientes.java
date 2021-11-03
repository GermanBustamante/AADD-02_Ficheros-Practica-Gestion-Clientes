package Modelo.FileAccess;

import Modelo.Entidades.Cliente;

import java.io.*;

public class FileAccessIndiceClientes {
    private File ficheroIndiceClientes;
    private static int numeroClientes;

    public static final String RUTA_FICHERO_INDICE_CLIENTES= "indice_clientes.bin";

    public FileAccessIndiceClientes() {
        this.ficheroIndiceClientes = new File(RUTA_FICHERO_INDICE_CLIENTES);
    }

    public void agregarIndiceClienteFichero(Cliente cliente) {
        try(FileOutputStream fileOutputStream = new FileOutputStream(ficheroIndiceClientes,true);
            DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream)){
           // numeroClientes =
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
