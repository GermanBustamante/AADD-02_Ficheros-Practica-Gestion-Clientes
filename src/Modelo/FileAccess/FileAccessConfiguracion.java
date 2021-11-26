package Modelo.FileAccess;

import java.io.*;
/**
 * Clase que nos servirá para gestionar el fichero "configuracion.bin" donde gestionaremos la forma de configurar la exportación de cliente de nuestro programa
 * @author germanbustamante_
 * @version 1.0
 */
public class FileAccessConfiguracion {
    //Atributos
    private final File fichero;

    //Constantes
    public static final String RUTA_FICHERO_CONFIGURACION = ".//Ficheros//configuracion.bin";

    //Constructores
    public FileAccessConfiguracion() {
        this.fichero = new File(RUTA_FICHERO_CONFIGURACION);
    }
    public FileAccessConfiguracion(String rutaFichero) {
        this.fichero = new File(rutaFichero);
    }

    //Getters
    public File getFichero() {
        return fichero;
    }

    //Metodos publicos
    /**
     * <b>Cabecera:</b> public void escribirFormatoFicheroConfiguracion(String formato)<br/>
     * <b>Precondiciones:</b> ninguna<br/>
     * <b>Postcondiciones:</b> escribe en el fichero correspondiente de bytes el formato pasado por parámetro<br/>
     * @param formato formato a escribir en binario
     * @throws IOException ha ocurrido un error con el flujo de datos
     */
    public void escribirFormatoFicheroConfiguracion(String formato) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(this.fichero);
             DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream)) {
            dataOutputStream.writeBytes(formato);
        }
    }

    /**
     * <b>Cabecera:</b> public String getFormatoFicheroConfiguracion()<br/>
     * <b>Precondiciones:</b> ninguna<br/>
     * <b>Postcondiciones:</b> Lee todos los bytes de un fichero y lo devuelve en cadena<br/>
     * @return String formato, el formato en el que se va a escribir el fichero de exportación
     * @throws IOException ha ocurrido un error con el flujo de datos
     */
    public String getFormatoFicheroConfiguracion() throws IOException {
        String formato;
        try (FileInputStream fileInputStream = new FileInputStream(this.fichero);
             DataInputStream dataInputStream = new DataInputStream(fileInputStream)) {
            formato = new String(dataInputStream.readAllBytes());
        }
        return formato;
    }
}
