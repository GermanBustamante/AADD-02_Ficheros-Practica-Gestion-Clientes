package Modelo.Utilidades;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class UtilidadesTest {

    public static final String DNI = "29565540Y";
    public static final int NUMEROS_DNI = 29565540;
    public static final String CADENA_MAYOR_25 = "Pablito clavo un clavito, que clavito clavó pablito";
    public static final String CADENA_MAYOR_25_CORTADA = "Pablito clavo un clavito,";
    public static final String NOMBRE_LONGITUD_MENOR_25 = "German";
    public static final String NOMBRE_LONGITUD_MENOR_25_FORMATEADA = "German                   ";
    public static final String FORMATO_CADENAS = "%-25s";
    public static final int LONGITUD_MAXIMA_CADENA = 25;
    public static final String RUTA_FICHERO_INEXISTENTE = "peodbjas.bin";
    public static final String RUTA_FICHERO_TEST = ".//Ficheros//utilidadesTest.txt";
    public static final String RUTA_FICHERO_EXISTENTE = "C:\\Program Files (x86)";
    public static final int LONGITUD_FICHERO_VACIO =0;

    @AfterAll
    static void eliminarFicheroTest(){
        File fichero = new File(RUTA_FICHERO_TEST);
        fichero.delete();
    }

    @Test
    void testCasoValidoGetDNICompleto() {
        assertEquals(DNI, Utilidades.getDNICompleto(NUMEROS_DNI));
    }

    @Test
    void testCasoValidoFormatearString() {
        assertEquals(CADENA_MAYOR_25_CORTADA, Utilidades.formatearString(LONGITUD_MAXIMA_CADENA, FORMATO_CADENAS, CADENA_MAYOR_25));
        assertEquals(NOMBRE_LONGITUD_MENOR_25_FORMATEADA, Utilidades.formatearString(LONGITUD_MAXIMA_CADENA, FORMATO_CADENAS, NOMBRE_LONGITUD_MENOR_25));
    }

    @Test
    void testCasoValidoExisteFichero() {
        assertTrue(Utilidades.existeFichero(new File(RUTA_FICHERO_EXISTENTE)));
    }

    @Test
    void testCasoNoValidoExisteFichero(){
        assertFalse(Utilidades.existeFichero(new File(RUTA_FICHERO_INEXISTENTE)));
    }

    @Test
    void testCasoValidoEstaVacioFichero() {
        assertFalse(Utilidades.estaVacioFichero(new File(RUTA_FICHERO_EXISTENTE)));
    }

    @Test
    void testCasoNoValidoEstaVacioFichero() {
        assertTrue(Utilidades.estaVacioFichero(new File(RUTA_FICHERO_INEXISTENTE)));
    }

    @Test
    void testCasoValidoVaciarFichero() {
        File fichero = new File(RUTA_FICHERO_TEST);
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fichero))){
            bufferedWriter.write(CADENA_MAYOR_25);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertNotEquals(LONGITUD_FICHERO_VACIO, fichero.length());
        Utilidades.vaciarFichero(fichero);
        assertEquals(LONGITUD_FICHERO_VACIO, fichero.length());
    }
}