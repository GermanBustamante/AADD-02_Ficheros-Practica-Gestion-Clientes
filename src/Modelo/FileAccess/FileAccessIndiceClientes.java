package Modelo.FileAccess;

import Modelo.Entidades.Cliente;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileAccessIndiceClientes {
    private File ficheroIndiceClientes;
    public static final int LONGITUD_INT_BYTES = 4;
    public static final int LONGITUD_DNI_STRING_BYTES = 9;

    public static final String RUTA_FICHERO_INDICE_CLIENTES = "indice_clientes.bin";

    public FileAccessIndiceClientes() {
        this.ficheroIndiceClientes = new File(RUTA_FICHERO_INDICE_CLIENTES);
    }

    private int getNumeroClientes() {
        return (int) (ficheroIndiceClientes.length() / (getLongitudDniYNumeroBytes()));
    }

    public void agregarIndiceYDNIFicheroIndiceClientes(String DNICliente) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(ficheroIndiceClientes, true);
             DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream)) {
            dataOutputStream.writeInt(getNumeroClientes() + 1);//TODO CAMBIAR NUMERO MÁGICO
            dataOutputStream.writeBytes(DNICliente);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getLongitudDniYNumeroBytes() {
        return LONGITUD_DNI_STRING_BYTES + LONGITUD_INT_BYTES;
    }

    //Dado el dni de un cliente, recorre el fichero "indice_clientes.bin", leyendo los índices, devolviendo el indice del
    //dni si existe, o -1 si no existe ese dni en el fichero, o -2 si ha ocurrido algún error general, o 0 si
    //el fichero existe pero está vacío
    public int getPosicionClienteFichero(String dniCliente) {
        int indiceCliente = 0;
        boolean dniEncontrado = false;
        try (FileInputStream fileInputStream = new FileInputStream(ficheroIndiceClientes);
             DataInputStream dataInputStream = new DataInputStream(fileInputStream)) {
            while (!dniEncontrado) {
                indiceCliente = dataInputStream.readInt();
                if (indiceCliente != -1 && new String(dataInputStream.readNBytes(LONGITUD_DNI_STRING_BYTES)).equals(dniCliente)) {
                    dniEncontrado = true;
                }
            }
        } catch (EOFException e) {
            indiceCliente = -1;
        } catch (IOException e) {
            indiceCliente = -2;//TODO MENSAJE HA OCURRIDO ALGUN ERROR
        }
        return indiceCliente;
    }

    //Salta al registro donde se encuentra el cliente a borrar, y sobreescribe su índice por -1
    public void borrarClienteFicheroIndices(int posicionClienteFicheroIndices) {
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(ficheroIndiceClientes, FileAccessCliente.RANDOMACCESSFILE_MODO_LECTURA_ESCRITURA)) {
            randomAccessFile.seek((long) (posicionClienteFicheroIndices - 1) * getLongitudDniYNumeroBytes());
            randomAccessFile.writeInt(-1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Lee todos los indices y dniS del fichero, metiendo en una lista de enteros aquellos dnis con índice -1
    public List<Integer> getListaIndicesClientesBorrados() {
        List<Integer> listaIndicesClientesBorrados = new ArrayList<>();
        int indiceCliente = 0;
        int contadorNumeroClientesReales = 0;
        try (FileInputStream fileInputStream = new FileInputStream(ficheroIndiceClientes);
             DataInputStream dataInputStream = new DataInputStream(fileInputStream)) {
            contadorNumeroClientesReales++;
            indiceCliente = dataInputStream.readInt();
            if (indiceCliente == -1) {
                listaIndicesClientesBorrados.add(contadorNumeroClientesReales);
            }
        } catch (EOFException e) {
            //no hace nada, llega al final y sale del método
        } catch (IOException e) {
            indiceCliente = -3;//TODO MENSAJE HA OCURRIDO ALGUN ERROR
        }
        return listaIndicesClientesBorrados;
    }
}
