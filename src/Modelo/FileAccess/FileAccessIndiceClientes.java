package Modelo.FileAccess;

import Modelo.Entidades.Cliente;

import java.io.*;

public class FileAccessIndiceClientes {
    private File ficheroIndiceClientes;
    private static int numeroClientes;
    public static final int LONGITUD_INT_BYTES = 4;//TODO 5
    public static final int LONGITUD_DNI_STRING_BYTES = 9;//TODO 6

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
            dataOutputStream.write(DNICliente.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getLongitudDniYNumeroBytes() {
        return LONGITUD_DNI_STRING_BYTES + LONGITUD_INT_BYTES;
    }


    public int getPosicionClienteFichero(String dniCliente) {

        int indiceCliente = 0;
        boolean dniEncontrado = false;
        try (FileInputStream fileInputStream = new FileInputStream(ficheroIndiceClientes);
             DataInputStream dataInputStream = new DataInputStream(fileInputStream)) {
            while (!dniEncontrado) {
                indiceCliente = dataInputStream.readInt();
                if (new String(dataInputStream.readNBytes(LONGITUD_DNI_STRING_BYTES)).equals(dniCliente)) {
                    dniEncontrado = true;
                }
            }
        } catch (EOFException e) {
            indiceCliente=-1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return indiceCliente;
    }
}
