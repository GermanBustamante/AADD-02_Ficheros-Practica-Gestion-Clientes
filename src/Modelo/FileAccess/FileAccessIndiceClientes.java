package Modelo.FileAccess;

import Modelo.Entidades.Cliente;
import Modelo.Utilidades.Utilidades;
import com.sun.source.tree.WhileLoopTree;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileAccessIndiceClientes {
    //Atributos
    private File ficheroIndiceClientes;
    //Constantes
    public static final int LONGITUD_INT_BYTES = 4;
    public static final int LONGITUD_DNI_STRING_BYTES = 9;
    public static final String RUTA_FICHERO_INDICE_CLIENTES = "indice_clientes.bin";
    //Constructores
    public File getFicheroIndiceClientes() {
        return ficheroIndiceClientes;
    }
    public FileAccessIndiceClientes() {
        this.ficheroIndiceClientes = new File(RUTA_FICHERO_INDICE_CLIENTES);
    }
    //Getters

    //Metodos publicos
    public void agregarIndiceYDNIFicheroIndiceClientes(String DNICliente) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(ficheroIndiceClientes, true);
             DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream)) {
            dataOutputStream.writeInt(getNumeroClientes() + 1);//TODO CAMBIAR NUMERO MÁGICO
            dataOutputStream.writeBytes(DNICliente);
        }
    }
    //Dado el dni de un cliente, recorre el fichero "indice_clientes.bin", leyendo los índices, devolviendo el indice del
    //dni si existe, o -1 si no existe ese dni en el fichero, o -2 si ha ocurrido algún error general, o 0 si
    //el fichero existe pero está vacío
    public int getPosicionClienteFichero(String dniCliente) throws IOException {
        int indiceCliente = 0;
        boolean dniEncontrado = false;
        try (FileInputStream fileInputStream = new FileInputStream(ficheroIndiceClientes);
             DataInputStream dataInputStream = new DataInputStream(fileInputStream)) {
            while (!dniEncontrado) {
                indiceCliente = dataInputStream.readInt();
                if (indiceCliente != -1) {
                    if (new String(dataInputStream.readNBytes(LONGITUD_DNI_STRING_BYTES)).equals(dniCliente)){
                        dniEncontrado = true;
                    }
                }else {
                    dataInputStream.skipBytes(LONGITUD_DNI_STRING_BYTES);
                }
            }
        }
        return indiceCliente;
    }

    //Salta al registro donde se encuentra el cliente a borrar, y sobreescribe su índice por -1
    public void borrarClienteFicheroIndices(int posicionClienteFicheroIndices) throws IOException {
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(ficheroIndiceClientes, FileAccessCliente.RANDOMACCESSFILE_MODO_LECTURA_ESCRITURA)) {
            randomAccessFile.seek((long) (posicionClienteFicheroIndices - 1) * getLongitudDniYNumeroBytes());
            randomAccessFile.writeInt(-1);
        }
    }

    //Lee todos los indices y dniS del fichero, metiendo en una lista de enteros aquellos dnis con índice -1
    public List<Integer> getListaIndicesClientesBorrados() throws IOException {
        List<Integer> listaIndicesClientesBorrados = new ArrayList<>();
        int indiceCliente = 0;
        int contadorNumeroClientesReales = 0;
        try (
                FileInputStream fileInputStream = new FileInputStream(ficheroIndiceClientes);
                DataInputStream dataInputStream = new DataInputStream(fileInputStream);) {
            while (contadorNumeroClientesReales != getNumeroClientes() + 1) {
                contadorNumeroClientesReales++;
                indiceCliente = dataInputStream.readInt();
                if (indiceCliente == -1) {
                    listaIndicesClientesBorrados.add(contadorNumeroClientesReales);
                }
                dataInputStream.skipBytes(LONGITUD_DNI_STRING_BYTES);
                //TODO MEJORAR MODURALIZACIÓN
            }
        } catch (EOFException e) {
            //No hace nada, ha llegado al final del fichero
        }
        return listaIndicesClientesBorrados;
    }

    //Metodos privados
    private int getNumeroClientes() {
        return (int) (ficheroIndiceClientes.length() / (getLongitudDniYNumeroBytes()));
    }

    private int getLongitudDniYNumeroBytes() {
        return LONGITUD_DNI_STRING_BYTES + LONGITUD_INT_BYTES;
    }


}
