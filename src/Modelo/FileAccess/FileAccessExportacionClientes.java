package Modelo.FileAccess;

import java.io.*;

public class FileAccessExportacionClientes {
    private final File fichero;

    public static final String RUTA_FICHERO_CLIENTES_TEXTO = "configuracion.bin";
    public FileAccessExportacionClientes() {
        this.fichero = new File(RUTA_FICHERO_CLIENTES_TEXTO);
    }

    public void getFicheroTextoFormato(String formato) {
        String linea = null;
        try (InputStreamReader fileInputStream= new InputStreamReader(new FileInputStream(this.fichero), formato);
                BufferedReader bufferedReader = new BufferedReader(fileInputStream)
        ) {//TODO TERMINAR
            //while (linea==bufferedReader.readLine();)
            linea = bufferedReader.readLine();
            //TODO NUVEA LINEA
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void escribirFormatoExportacionFichero(String formatoFichero) {
        try(FileOutputStream fileOutputStream = new FileOutputStream(fichero);
        DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream)){
            dataOutputStream.writeBytes(formatoFichero);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
