package Modelo.FileAccess;

import java.io.*;

public class FileAccessFicheroConfig {
    private final File fichero;

    public static final String RUTA_FICHERO_CONFIGURACION = "configuracion.bin";
    public FileAccessFicheroConfig() {
        this.fichero = new File(RUTA_FICHERO_CONFIGURACION);
    }

    public void escribirFormatoFicheroConfiguracion(String formato) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(this.fichero);
            DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream)){
            dataOutputStream.writeBytes(formato);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
