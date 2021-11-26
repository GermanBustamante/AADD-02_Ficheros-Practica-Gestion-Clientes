package Modelo.FileAccess;

import Modelo.Utilidades.Utilidades;
import Vista.Menu;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileAccessConfiguracionTest {

    private static FileAccessConfiguracion fileAccessConfiguracion;
    public static final String RUTA_FICHERO_CONFIG_PRUEBA = ".//Ficheros//configuracion_prueba.bin";
    public static final String CADENA_CHAPUZA = "MOMO";

    @BeforeAll
    static void instanciarFileAccess() {
        fileAccessConfiguracion = new FileAccessConfiguracion(RUTA_FICHERO_CONFIG_PRUEBA);
    }

    @AfterAll
    static void borrarFicheroPrueba(){
        fileAccessConfiguracion.getFichero().delete();
    }

    //Antes de cada método vaciamos el fichero de clientes de prueba
    @BeforeEach
    void vaciarFicheroClientesPrueba() {
        Utilidades.vaciarFichero(fileAccessConfiguracion.getFichero());

    }

    @Test
    void TestCasoValidoEscribirFormatoFicheroConfiguracionYGetFormatoFicheroConfiguracion() {
        try {
            fileAccessConfiguracion.escribirFormatoFicheroConfiguracion(Menu.UTF_16);
            assertTrue(fileAccessConfiguracion.getFormatoFicheroConfiguracion().equals(Menu.UTF_16));
            assertFalse(fileAccessConfiguracion.getFormatoFicheroConfiguracion().equals(Menu.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void TestCasoNoValidoEscribirFormatoFicheroConfiguracionYGetFormatoFicheroConfiguracion() throws IOException {
        fileAccessConfiguracion.escribirFormatoFicheroConfiguracion(CADENA_CHAPUZA);
        assertFalse(fileAccessConfiguracion.getFormatoFicheroConfiguracion().equals(Menu.UTF_8));
    }
}