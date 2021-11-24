package Modelo.FileAccess;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileAccessFicheroConfigTest {

    private static FileAccessFicheroConfig fileAccessFicheroConfig;
    public static final String RUTA_FICHERO_CONFIG_PRUEBA = "configuracion_prueba.bin";
    @BeforeAll
    static void init() {
        fileAccessFicheroConfig = new FileAccessFicheroConfig(RUTA_FICHERO_CONFIG_PRUEBA);
    }

    @AfterEach
    void tearDown() {
    }

    //TODO NO PUEDO HACER UN TEST NO VÁLIDO YA QUE DE LA VALIDACIÓN SE ENCARGA EL MENÚ
    @Test
    void TestCasoNoValidoEscribirFormatoFicheroConfiguracion() throws IOException {
        fileAccessFicheroConfig.escribirFormatoFicheroConfiguracion("MOMO");
        assertFalse(fileAccessFicheroConfig.getFormatoFicheroConfiguracion().equals("UTF-8"));
    }

    @Test
    void TestCasoValidaEscribirFormatoFicheroConfiguracion() throws IOException {
        fileAccessFicheroConfig.escribirFormatoFicheroConfiguracion("UTF-16");
        assertTrue(fileAccessFicheroConfig.getFormatoFicheroConfiguracion().equals("UTF-16"));
        assertFalse(fileAccessFicheroConfig.getFormatoFicheroConfiguracion().equals("UTF-8"));
    }

    @Test
    void getFormatoFicheroConfiguracion() {
    }
}