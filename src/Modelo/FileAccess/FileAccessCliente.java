package Modelo.FileAccess;

import Modelo.Entidades.Cliente;
import Modelo.Utilidades.Utilidades;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class FileAccessCliente {
    public static final String RANDOMACCESSFILE_MODO_LECTURA_ESCRITURA = "rw";
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
            dataOutputStream.writeBytes(cliente.getNombre());
            dataOutputStream.writeBytes(cliente.getApellidos());
            dataOutputStream.writeBytes(cliente.getDNI());
            dataOutputStream.writeBytes(cliente.getTelefono());
            dataOutputStream.writeBytes(cliente.getDireccion());
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private int getLongitudBytesCliente (){
        return LONGITUD_NOMBRE_CLIENTE_BYTES+LONGITUD_APELLIDOS_CLIENTE_BYTES+LONGITUD_DIRECCION_CLIENTE_BYTES+LONGITUD_DNI_CLIENTE_BYTES+LONGITUD_TELEFONO_CLIENTE_BYTES;
    }

    //Dado un índice del cliente, salta hacia el regsitro donde se encuentra dicho índice, recoge los campos
    //y crea un objeto tipo Cliente con los datos recogidos y se envía de vuelta
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

        String[] arrayCadenasDatosCliente = new String[5];//TODO CAMBIAR NUMEROS MÁGICO
        String cadenaDatosCliente = new String(bytesDatosCliente);

        arrayCadenasDatosCliente[0] = cadenaDatosCliente.substring(Utilidades.NUMERO_SUBSTRING, 25);
        arrayCadenasDatosCliente[1] = cadenaDatosCliente.substring(25, 50);
        arrayCadenasDatosCliente[2] = cadenaDatosCliente.substring(50, 59);
        arrayCadenasDatosCliente[3] = cadenaDatosCliente.substring(59, 68);
        arrayCadenasDatosCliente[4] = cadenaDatosCliente.substring(68,98);

        return arrayCadenasDatosCliente;
    }

    //Recorre y recoge todos los clientes del fichero menos aquellos que deberían ser borrados
    public List<Cliente> getListaFicheroClientes(List<Integer> listaIndicesClientesBorrados) {
        byte[] bytesDatosCliente = null;
        int finalContador = 1;
        List<Cliente> listaClientes = new ArrayList<>();
        if (!listaIndicesClientesBorrados.contains(-1)){//Si el fichero existe
            try (FileInputStream fileInputStream = new FileInputStream(ficheroClientes);
                 DataInputStream dataInputStream = new DataInputStream(fileInputStream)) {
                if (!listaIndicesClientesBorrados.contains(finalContador)){
                    String[] arrayCadenasDatosCliente = getArrayCadenasDatosCliente(dataInputStream.readNBytes(getLongitudBytesCliente()));
                    listaClientes.add(new Cliente(arrayCadenasDatosCliente[0],arrayCadenasDatosCliente[1],arrayCadenasDatosCliente[2],
                            arrayCadenasDatosCliente[3],arrayCadenasDatosCliente[4]));
                }
                finalContador++;
            } catch (EOFException e) {
                //Ha llegado al final
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return  listaClientes;
    }

    //listaIndicesClientesBorrados.stream().anyMatch(i -> i.intValue()==finalContador)

}
