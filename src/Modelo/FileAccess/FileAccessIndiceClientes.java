package Modelo.FileAccess;

import Modelo.Entidades.Cliente;

import java.io.*;

public class FileAccessIndiceClientes {
    private File ficheroIndiceClientes;
    private static int numeroClientes;
    public static final int LONGITUD_INT_BYTES = 4;
    public static final int LONGITUD_DNI_STRING_BYTES = 9;

    public static final String RUTA_FICHERO_INDICE_CLIENTES= "indice_clientes.bin";

    public FileAccessIndiceClientes() {
        this.ficheroIndiceClientes = new File(RUTA_FICHERO_INDICE_CLIENTES);
    }

    public void agregarIndiceClienteFichero(Cliente cliente) {
        try(FileOutputStream fileOutputStream = new FileOutputStream(ficheroIndiceClientes,true);
            DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream)){

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public int getIndiceCliente(String dniCliente) {
        //TODO TERMINAR
        int indiceCliente = 0;
        boolean dniEncontrado = false;
        try(FileInputStream fileInputStream = new FileInputStream(ficheroIndiceClientes);
            DataInputStream dataInputStream = new DataInputStream(fileInputStream)){
            while (!dniEncontrado){
                indiceCliente = dataInputStream.readInt();
                if (String.valueOf(dataInputStream.readNBytes(3)).equals(dniCliente)){
                    dniEncontrado = true;
                }
            }
        }catch (EOFException e){
            e.printStackTrace();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
