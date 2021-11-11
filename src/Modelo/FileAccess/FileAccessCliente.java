package Modelo.FileAccess;

import Modelo.Entidades.Cliente;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileAccessCliente {
    private File ficheroClientes;

    public static final String RUTA_FICHERO_CLIENTES= "clientes.bin";
    public static final int LONGITUD_NOMBRE_CLIENTE_BYTES= 25;
    public static final int LONGITUD_APELLIDOS_CLIENTE_BYTES= 25;
    public static final int LONGITUD_DNI_CLIENTE_BYTES= 9;
    public static final int LONGITUD_TELEFONO_CLIENTE_BYTES= 9;
    public static final int LONGITUD_DIRECCION_CLIENTE_BYTES= 30;

    public FileAccessCliente() {
        this.ficheroClientes = new File(RUTA_FICHERO_CLIENTES);
    }

    public void agregarClienteFichero(Cliente cliente) {
        try(FileOutputStream fileOutputStream = new FileOutputStream(ficheroClientes,true);
            DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream)){
            dataOutputStream.write(cliente.getNombre().getBytes());
            dataOutputStream.write(cliente.getApellidos().getBytes());
            dataOutputStream.write(cliente.getDNI().getBytes());
            dataOutputStream.write(cliente.getTelefono().getBytes());
            dataOutputStream.write(cliente.getDireccion().getBytes());
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private int getLongitudBytesCliente (){
        return LONGITUD_NOMBRE_CLIENTE_BYTES+LONGITUD_APELLIDOS_CLIENTE_BYTES+LONGITUD_DIRECCION_CLIENTE_BYTES+LONGITUD_DNI_CLIENTE_BYTES+LONGITUD_TELEFONO_CLIENTE_BYTES;
    }


    public Cliente getClienteDadoIndice() {
        return null;
    }
}
