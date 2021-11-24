package Modelo.FileAccess;

import Modelo.Entidades.Cliente;
import Modelo.Utilidades.Utilidades;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class FileAccessCliente {
    //Atributos
    private File ficheroClientes;

    //Constantes
    public static final String RUTA_FICHERO_CLIENTES= "clientes.bin";
    public static final String RANDOMACCESSFILE_MODO_LECTURA_ESCRITURA = "rw";
    public static final String RANDOMACCESSFILE_MODO_LECTURA = "r";
    public static final int LONGITUD_NOMBRE_CLIENTE_BYTES= 25;
    public static final int LONGITUD_APELLIDOS_CLIENTE_BYTES= 25;
    public static final int LONGITUD_DNI_CLIENTE_BYTES= 9;
    public static final int LONGITUD_TELEFONO_CLIENTE_BYTES= 9;
    public static final int LONGITUD_DIRECCION_CLIENTE_BYTES= 30;
    public static final int LONGITUD_ARRAY_CADENAS_DATOS_CLIENTE = 5;

    //Constructores
    public FileAccessCliente() {
        this.ficheroClientes = new File(RUTA_FICHERO_CLIENTES);
    }
    public FileAccessCliente(String ruta) {
        this.ficheroClientes = new File(ruta);
    }

    //Getters
    public File getFicheroClientes() {
        return ficheroClientes;
    }

    //Metodos públicos

    public void agregarClienteFichero(Cliente cliente) throws IOException {
       FileOutputStream fileOutputStream = new FileOutputStream(ficheroClientes,true);
        try (DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream)) {
            dataOutputStream.writeBytes(cliente.getNombre());
            dataOutputStream.writeBytes(cliente.getApellidos());
            dataOutputStream.writeBytes(cliente.getDNI());
            dataOutputStream.writeBytes(cliente.getTelefono());
            dataOutputStream.writeBytes(cliente.getDireccion());
        }
    }

    //Recorre y recoge todos los clientes del fichero menos aquellos que deberían ser borrados
    public List<Cliente> getListaFicheroClientes(List<Integer> listaIndicesClientesBorrados) throws IOException {
        int finalContador = 1;
        List<Cliente> listaClientes = new ArrayList<>();
        if (!listaIndicesClientesBorrados.contains(-1)){//Si hay algún cliente a añadir

            try(FileInputStream fileInputStream = new FileInputStream(ficheroClientes);
                DataInputStream dataInputStream = new DataInputStream(fileInputStream);){
                while(finalContador != (ficheroClientes.length()/getLongitudBytesCliente()+1)){
                    if (!listaIndicesClientesBorrados.contains(finalContador)){
                        String[] arrayCadenasDatosCliente = getArrayCadenasDatosCliente(dataInputStream.readNBytes(getLongitudBytesCliente()));
                        listaClientes.add(new Cliente(arrayCadenasDatosCliente[0],arrayCadenasDatosCliente[1],arrayCadenasDatosCliente[2],
                                arrayCadenasDatosCliente[3],arrayCadenasDatosCliente[4]));
                    }else {//Si es un cliente a eliminar
                        dataInputStream.skipBytes(getLongitudBytesCliente());
                    }
                    finalContador++;
                }
            }catch (EOFException e){
                //No hace nada porq ha terminado de leer el fichero entero
            }
        }
        return  listaClientes;
    }

    //Dado un índice del cliente, salta hacia el regsitro donde se encuentra dicho índice, recoge los campos
    //y crea un objeto tipo Cliente con los datos recogidos y se envía de vuelta
    public Cliente getClienteDadoIndice(int posicionClienteFicheroIndices) throws IOException {
        byte[] bytesDatosCliente = new byte[getLongitudBytesCliente()];
        Cliente clienteRecogido = null;
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(ficheroClientes, RANDOMACCESSFILE_MODO_LECTURA)) {
            randomAccessFile.seek((long) (posicionClienteFicheroIndices - 1) * getLongitudBytesCliente());
            randomAccessFile.readFully(bytesDatosCliente);
            String[] arrayCadenasDatosCliente = getArrayCadenasDatosCliente(bytesDatosCliente);
            clienteRecogido = new Cliente(arrayCadenasDatosCliente[0], arrayCadenasDatosCliente[1], arrayCadenasDatosCliente[2],
                    arrayCadenasDatosCliente[3], arrayCadenasDatosCliente[4]);
            Utilidades.cerrarFlujo(randomAccessFile);
        }
        return clienteRecogido;
    }

    //Métodos privados
    private int getLongitudBytesCliente (){
        return LONGITUD_NOMBRE_CLIENTE_BYTES+LONGITUD_APELLIDOS_CLIENTE_BYTES+LONGITUD_DIRECCION_CLIENTE_BYTES+LONGITUD_DNI_CLIENTE_BYTES+LONGITUD_TELEFONO_CLIENTE_BYTES;
    }

    private String[] getArrayCadenasDatosCliente(byte[] bytesDatosCliente) {
        String[] arrayCadenasDatosCliente = new String[5];//TODO CAMBIAR NUMEROS MÁGICO
        String cadenaDatosCliente = new String(bytesDatosCliente);
        arrayCadenasDatosCliente[0] = cadenaDatosCliente.substring(0, 25);
        arrayCadenasDatosCliente[1] = cadenaDatosCliente.substring(25, 50);
        arrayCadenasDatosCliente[2] = cadenaDatosCliente.substring(50, 59);
        arrayCadenasDatosCliente[3] = cadenaDatosCliente.substring(59, 68);
        arrayCadenasDatosCliente[4] = cadenaDatosCliente.substring(68,98);
        return arrayCadenasDatosCliente;
    }


}
