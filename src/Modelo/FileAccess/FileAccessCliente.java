package Modelo.FileAccess;

import Modelo.Entidades.Cliente;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <b>Clase que nos servirá para gestionar el fichero "clientes.bin" donde gestionaremos los clientes de nuestro programa</b>
 * @author germanbustamante_
 * @version 1.0
 */
public class FileAccessCliente {
    //Atributos
    private final File fichero;

    //Constantes
    public static final String RUTA_FICHERO_CLIENTES = "clientes.bin";
    public static final String RANDOMACCESSFILE_MODO_LECTURA_ESCRITURA = "rw";
    public static final String RANDOMACCESSFILE_MODO_LECTURA = "r";
    public static final int LONGITUD_NOMBRE_CLIENTE_BYTES = 25;
    public static final int LONGITUD_APELLIDOS_CLIENTE_BYTES = 25;
    public static final int LONGITUD_DNI_CLIENTE_BYTES = 9;
    public static final int LONGITUD_TELEFONO_CLIENTE_BYTES = 9;
    public static final int LONGITUD_DIRECCION_CLIENTE_BYTES = 30;
    public static final int LONGITUD_ARRAY_CADENAS_DATOS_CLIENTE = 5;

    //Constructores
    public FileAccessCliente() {
        this.fichero = new File(RUTA_FICHERO_CLIENTES);
    }

    public FileAccessCliente(String ruta) {
        this.fichero = new File(ruta);
    }

    //Getters
    public File getFichero() {
        return fichero;
    }

    //Metodos públicos
    /**
     * <b>Cabecera:</b> public void escribirClienteFicheroBinario(Cliente oCliente)<br/>
     * <b>Precondiciones:</b> ninguna<br/>
     * <b>Postcondiciones:</b> Abre un flujo de datos y escribe en el fichero binario "clientes.bin" al cliente correspondiente
     * en bytes<br/>
     * @param oCliente oCliente a escribir
     * @throws IOException ha ocurrido algún error con el flujo de datos
     */
    public void escribirClienteFicheroBinario(Cliente oCliente) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(fichero, true);
        try (DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream)) {
            dataOutputStream.writeBytes(oCliente.getNombre());
            dataOutputStream.writeBytes(oCliente.getApellidos());
            dataOutputStream.writeBytes(oCliente.getDNI());
            dataOutputStream.writeBytes(oCliente.getTelefono());
            dataOutputStream.writeBytes(oCliente.getDireccion());
        }
    }

    /**
     * <b>Cabecera:</b> public List<Cliente> getListaClientesNoBorradosFicheroBinario(List<Integer> listaIndicesClientesBorrados)<br/>
     * <b>Precondiciones:</b> ninguna<br/>
     * <b>Postcondiciones:</b> Recorre con un contador tantas veces como clientes haya en el fichero, si no es un cliente a eliminar,
     * lo lee y lo añade a una lista de clientes, en caso contrario, salta los bytes necesarios.<br/>
     * @param listaIndicesClientesBorrados lista de índices de los clientes que han sido borrados y el lector no debe tomar en cuenta a la hora de leer los clientes
     * @return List de tipo Cliente con los clientes leídos y recogidos que no han sido borrados
     * @throws IOException ha ocurrido un error con el flujo de datos
     */
    public List<Cliente> getListaClientesNoBorradosFicheroBinario(List<Integer> listaIndicesClientesBorrados) throws IOException {
        int contadorIndice = 1;
        List<Cliente> listaClientesRecogida = new ArrayList<>();
        try (FileInputStream fileInputStream = new FileInputStream(fichero);
             DataInputStream dataInputStream = new DataInputStream(fileInputStream)) {
            while (contadorIndice != (getNumeroClientes() + 1)) {//TODO NUMERO MÁGICO
                if (!listaIndicesClientesBorrados.contains(contadorIndice)) {
                    String[] arrayCadenasDatosCliente = getArrayCadenasDatosCliente(dataInputStream.readNBytes(getLongitudBytesCliente()));
                    listaClientesRecogida.add(new Cliente(arrayCadenasDatosCliente[0], arrayCadenasDatosCliente[1], arrayCadenasDatosCliente[2], arrayCadenasDatosCliente[3], arrayCadenasDatosCliente[4]));
                } else {
                    dataInputStream.skipBytes(getLongitudBytesCliente());
                }
                contadorIndice++;
            }
        } catch (EOFException e) {}
        return listaClientesRecogida;
    }

    /**
     * <b>Cabecera:</b> public Cliente getClienteFicheroBinario(int posicionClienteFicheroIndices)<br/>
     * <b>Precondiciones:</b> ninguna<br/>
     * <b>Postcondiciones:</b> Abre un flujo de datos y salta hasta el registro donde se encuentra el cliente que queremos
     * recoger, lee dicho cliente, lo reconstruye y lo devuelve<br/>
     * @param posicionClienteFicheroIndices posición del cliente en el fichero de índices, para saltar hasta dicho registro
     * @return objeto tipo Cliente, simulando el cliente recogido del fichero
     * @throws IOException ha ocurrido un error con el flujo de datos
     */
    public Cliente getClienteFicheroBinario(int posicionClienteFicheroIndices) throws IOException {
        Cliente clienteRecogido = null;
        byte[] bytesDatosCliente = new byte[getLongitudBytesCliente()];
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(fichero, RANDOMACCESSFILE_MODO_LECTURA)) {
            randomAccessFile.seek((long) (posicionClienteFicheroIndices- 1) * getLongitudBytesCliente());//TODO NUMERO MÁGICO
            randomAccessFile.readFully(bytesDatosCliente);
            String[] arrayCadenasDatosCliente = getArrayCadenasDatosCliente(bytesDatosCliente);
            clienteRecogido = new Cliente(arrayCadenasDatosCliente[0], arrayCadenasDatosCliente[1], arrayCadenasDatosCliente[2], arrayCadenasDatosCliente[3], arrayCadenasDatosCliente[4]);
        }
        return clienteRecogido;
    }

    //Métodos privados
    private int getLongitudBytesCliente() {
        return LONGITUD_NOMBRE_CLIENTE_BYTES + LONGITUD_APELLIDOS_CLIENTE_BYTES + LONGITUD_DIRECCION_CLIENTE_BYTES + LONGITUD_DNI_CLIENTE_BYTES + LONGITUD_TELEFONO_CLIENTE_BYTES;
    }

    private int getNumeroClientes() {
        return (int) (fichero.length() / getLongitudBytesCliente());
    }

    private String[] getArrayCadenasDatosCliente(byte[] bytesDatosCliente) {
        String[] arrayCadenasDatosCliente = new String[LONGITUD_ARRAY_CADENAS_DATOS_CLIENTE];
        String cadenaDatosCliente = new String(bytesDatosCliente);
        arrayCadenasDatosCliente[0] = cadenaDatosCliente.substring(0, 25);
        arrayCadenasDatosCliente[1] = cadenaDatosCliente.substring(25, 50);
        arrayCadenasDatosCliente[2] = cadenaDatosCliente.substring(50, 59);
        arrayCadenasDatosCliente[3] = cadenaDatosCliente.substring(59, 68);
        arrayCadenasDatosCliente[4] = cadenaDatosCliente.substring(68, 98);
        return arrayCadenasDatosCliente;
    }
}
