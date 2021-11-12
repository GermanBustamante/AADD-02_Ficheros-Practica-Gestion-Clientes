package Modelo.FileAccess;

import Modelo.Entidades.Cliente;
import Modelo.Utilidades.Utilidades;

import java.io.*;

public class FileAccessCliente {
    private File ficheroClientes;

    public static final String RUTA_FICHERO_CLIENTES= "clientes.bin";
    public static final int LONGITUD_NOMBRE_CLIENTE_BYTES= 25;
    public static final int LONGITUD_APELLIDOS_CLIENTE_BYTES= 25;
    public static final int LONGITUD_DNI_CLIENTE_BYTES= 9;
    public static final int LONGITUD_TELEFONO_CLIENTE_BYTES= 9;
    public static final int LONGITUD_DIRECCION_CLIENTE_BYTES= 30;

    public static final String RANDOMACCESSFILE_MODO_LECTURA = "r";
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

    //Lee el registro entero del tirón y luego subString a la cadena;
    public Cliente getClienteDadoIndice(int posicionClienteFicheroIndices) {
        byte[] bytesDatosCliente = new byte[getLongitudBytesCliente()];
        Cliente clienteRecogido = null;
        try(RandomAccessFile randomAccessFile = new RandomAccessFile(ficheroClientes, RANDOMACCESSFILE_MODO_LECTURA)){
            randomAccessFile.seek((long) (posicionClienteFicheroIndices-1) * getLongitudBytesCliente());
            randomAccessFile.readFully(bytesDatosCliente);
            String[] arrayCadenasDatosCliente = getArrayCadenasDatosCliente(bytesDatosCliente);
            clienteRecogido = new Cliente(arrayCadenasDatosCliente[0],arrayCadenasDatosCliente[1],arrayCadenasDatosCliente[2],
                    arrayCadenasDatosCliente[3],arrayCadenasDatosCliente[4]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clienteRecogido;
    }

    private String[] getArrayCadenasDatosCliente(byte[] bytesDatosCliente) {
        //TODO PREGUNTAR SI ESTÁ CORRECTO
        String[] arrayCadenasDatosCliente = new String[5];//TODO CAMBIAR NUMEROS MÁGICO
        String cadenaDatosCliente = new String(bytesDatosCliente);

        arrayCadenasDatosCliente[0] = cadenaDatosCliente.substring(Utilidades.NUMERO_SUBSTRING, 25);//OK
        arrayCadenasDatosCliente[1] = cadenaDatosCliente.substring(25, 50);
        arrayCadenasDatosCliente[2] = cadenaDatosCliente.substring(50, 59);
        arrayCadenasDatosCliente[3] = cadenaDatosCliente.substring(59, 68);
        arrayCadenasDatosCliente[4] = cadenaDatosCliente.substring(68,98);

        return arrayCadenasDatosCliente;
    }
}
