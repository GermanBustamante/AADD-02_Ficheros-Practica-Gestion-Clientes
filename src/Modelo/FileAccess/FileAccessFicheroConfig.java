package Modelo.FileAccess;

import Vista.Menu;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileAccessFicheroConfig {
    private final File fichero;

    public static final String RUTA_FICHERO_CONFIGURACION = "configuracion.bin";

    public FileAccessFicheroConfig() {
        this.fichero = new File(RUTA_FICHERO_CONFIGURACION);
    }

    //Escribe el formato pasado en bytes
    public void escribirFormatoFicheroConfiguracion(String formato) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(this.fichero);
             DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream)) {
            dataOutputStream.writeBytes(formato);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String getFormatoFicheroConfiguracion() {
        String formato = null;
        try (FileInputStream fileInputStream = new FileInputStream(this.fichero);
             DataInputStream dataInputStream = new DataInputStream(fileInputStream)) {
            formato = new String(dataInputStream.readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return formato;
        }
        return formato;
    }
}
