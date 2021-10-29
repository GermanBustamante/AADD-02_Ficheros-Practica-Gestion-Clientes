package Modelo.FileAccess;

import Modelo.Entidades.Cliente;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileAccessIndiceClientes {
    private File ficheroIndiceClientes;
    private int numeroClientes;

    public static final String RUTA_FICHERO_INDICE_CLIENTES= "fichero_indice_clientes.bin";

    public FileAccessIndiceClientes() {
        this.ficheroIndiceClientes = new File(RUTA_FICHERO_INDICE_CLIENTES);
        numeroClientes = FileAccessCliente.getNumeroClientes();
    }

    public void agregarIndiceClienteFichero(Cliente cliente) {
        try(FileOutputStream fileOutputStream = new FileOutputStream(ficheroIndiceClientes,true);
            DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream)){

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
