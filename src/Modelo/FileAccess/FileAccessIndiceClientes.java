package Modelo.FileAccess;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que nos servirá para gestionar el fichero "indice_clientes.bin" donde gestionaremos los indices de los clientes de nuestro programa
 * @author germanbustamante_
 * @version 1.0
 */
public class FileAccessIndiceClientes {
    //Atributos
    private final File fichero;

    //Constantes
    public static final int LONGITUD_INT_BYTES = 4;
    public static final int LONGITUD_DNI_STRING_BYTES = 9;
    public static final int SEMAFORO_DNI_NO_VALIDO = -1;
    public static final String RUTA_FICHERO_INDICE_CLIENTES = ".//Ficheros//indice_clientes.bin";

    //Constructores
    public FileAccessIndiceClientes() {
        this.fichero = new File(RUTA_FICHERO_INDICE_CLIENTES);
    }
    public FileAccessIndiceClientes(String rutaFichero) {
        this.fichero = new File(rutaFichero);
    }

    //Getters
    public File getFichero() {
        return fichero;
    }

    //Metodos publicos
    /**
     * <b>Cabecera:</b> public void escribirIndiceYDNIFicheroBinario(String dniCliente)<br/>
     * <b>Precondiciones:</b> ninguna<br/>
     * <b>Postcondiciones:</b> Abre un flujo de datos y escribe en el fichero "indice_clientes.bin" el indice correspondiente y el DNI del cliente<br/>
     * @param dniCliente dni del cliente a escribir
     * @throws IOException ha ocurrido algún error con el flujo de datos
     */
    public void escribirIndiceYDNIFicheroBinario(String dniCliente) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(fichero, true);
             DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream)) {
            dataOutputStream.writeInt(getNumeroClientes());
            dataOutputStream.writeBytes(dniCliente);
        }
    }

    /**
     * <b>Cabecera:</b> public int getPosicionClienteFicheroBinario(String dniCliente)<br/>
     * <b>Precondiciones:</b> ninguna<br/>
     * <b>Postcondiciones:</b> Dado un dni, devuelve su posición(índice) en el fichero "indice_clientes.bin"<br/>
     * @param dniCliente dni del cliente a buscar
     * @return int indiceCliente siendo su posición en el fichero
     * @throws IOException ha ocurrido algún error con el flujo de datos
     */
    public int getPosicionClienteFicheroBinario(String dniCliente) throws IOException {
        int indiceCliente = 0;
        boolean dniEncontrado = false;
        try (FileInputStream fileInputStream = new FileInputStream(fichero);
             DataInputStream dataInputStream = new DataInputStream(fileInputStream)) {
            while (!dniEncontrado) {
                indiceCliente = dataInputStream.readInt();
                if (indiceCliente != SEMAFORO_DNI_NO_VALIDO) {
                    if (new String(dataInputStream.readNBytes(LONGITUD_DNI_STRING_BYTES)).equals(dniCliente)){
                        dniEncontrado = true;
                    }
                }else {
                    dataInputStream.skipBytes(LONGITUD_DNI_STRING_BYTES);
                }
            }
        }
        return indiceCliente-1;
    }

    /**
     * <b>Cabecera:</b> public void borrarClienteFicheroBinario(int posicionClienteFicheroIndices)<br/>
     * <b>Precondiciones:</b> ninguna<br/>
     * <b>Postcondiciones:</b> Abre un flujo de datos, salta el registro dado por el parámetro, y sobreescribe el índice del puntero por -1<br/>
     * @param posicionClienteFicheroIndices posicion del registro a que debe saltar
     * @throws IOException ha ocurrido algún error con el flujo de datos
     */
    public void borrarClienteFicheroBinario(int posicionClienteFicheroIndices) throws IOException {
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(fichero, FileAccessCliente.RANDOMACCESSFILE_MODO_LECTURA_ESCRITURA)) {
            if(posicionClienteFicheroIndices != 1){
                randomAccessFile.seek((long) (posicionClienteFicheroIndices) * getLongitudIndiceYDniBytes());
            }
            randomAccessFile.writeInt(SEMAFORO_DNI_NO_VALIDO);
        }
    }

    /**
     * <b>Cabecera:</b> public List*Integer* getListaIndicesClientesBorradosFicheroBinario()<br/>
     * <b>Precondiciones:</b> ninguna<br/>
     * <b>Postcondiciones:</b> Abre un flujo de datos y lee todos los indices de los DNIs, luego, solo retorna aquellos
     * índices = -1, simulando que son los que deberían haber sido borrados<br/>
     * @return List*Integer* con los índices de los DNIs borrados
     * @throws IOException ha ocurrido algún error con el flujo de datos
     */
    public List<Integer> getListaIndicesClientesBorradosFicheroBinario() throws IOException {
        List<Integer> listaIndicesClientesBorrados = new ArrayList<>();
        int indiceCliente;
        int contador = 1;
        try (FileInputStream fileInputStream = new FileInputStream(fichero);
             DataInputStream dataInputStream = new DataInputStream(fileInputStream)) {
            while (contador != getNumeroClientes()) {
                indiceCliente = dataInputStream.readInt();
                if (indiceCliente == SEMAFORO_DNI_NO_VALIDO) {
                    listaIndicesClientesBorrados.add(contador);
                }
                dataInputStream.skipBytes(LONGITUD_DNI_STRING_BYTES);
                contador++;
            }
        } catch (EOFException e) {}
        return listaIndicesClientesBorrados;
    }

    //Metodos privados
    //Devuelve el numero de clientes de un fichero, teniendo en cuenta que todos los clientes ocupen lo mismo
    private int getNumeroClientes() {
        return (int) (fichero.length() / (getLongitudIndiceYDniBytes())+1);
    }

    //Devuelve la longitud en bytes de un índice (int) y la longitud de un dni
    private int getLongitudIndiceYDniBytes() {
        return LONGITUD_DNI_STRING_BYTES + LONGITUD_INT_BYTES;
    }


}
