package Modelo.FileAccess;

import Modelo.Utilidades.Utilidades;
import Vista.Menu;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileAccessFicheroConfig {
    //Atributos
    private final File fichero;
    //Constantes
    public static final String RUTA_FICHERO_CONFIGURACION = "configuracion.bin";
    //Constructores
    public FileAccessFicheroConfig() {
        this.fichero = new File(RUTA_FICHERO_CONFIGURACION);
    }

    public FileAccessFicheroConfig(String rutaFicheroConfigPrueba) {
        this.fichero = new File(rutaFicheroConfigPrueba);
    }
    //Getters
    public File getFichero() {
        return fichero;
    }

    //Metodos publicos
    //Escribe el formato pasado en bytes
    public void escribirFormatoFicheroConfiguracion(String formato) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(this.fichero);
             DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream)) {
            dataOutputStream.writeBytes(formato);
        }
    }


    public String getFormatoFicheroConfiguracion() throws IOException {
        String formato = null;
        try (FileInputStream fileInputStream = new FileInputStream(this.fichero);
             DataInputStream dataInputStream = new DataInputStream(fileInputStream)) {
            formato = new String(dataInputStream.readAllBytes());
        }
        return formato;
    }
}
