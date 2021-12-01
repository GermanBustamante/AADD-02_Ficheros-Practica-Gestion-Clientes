package Vista;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidacionesTest {

    public static final String CADENA_INUTIL = "Soy una cadena inutil";
    public static final String CADENA_VACIA = "";
    public static final String CADENA_ESPACIOS_BLANCO= "     ";
    public static final String CADENA_NUMERICA_DECIMAL = "3.2";
    public static final String CADENA_NUMERICA_ENTERO = "3";

    @Test
    void testCasoNoValidoEsEntero() {
        assertFalse(Validaciones.esEntero(CADENA_INUTIL));
        assertThrows(NullPointerException.class, () -> Validaciones.esEntero(null));
        assertFalse(Validaciones.esEntero(CADENA_NUMERICA_DECIMAL));
    }

    @Test
    void testCasoValidoEsEntero() {
        assertTrue(Validaciones.esEntero("3"));
    }

    @Test
    void testCasoNoValidoEsNumeroEnRango() {
        assertFalse(Validaciones.esNumeroEnRango(CADENA_INUTIL, 0, 0));
        assertThrows(NullPointerException.class, () ->Validaciones.esNumeroEnRango(null, 0, 0));
        assertFalse(Validaciones.esNumeroEnRango(CADENA_NUMERICA_ENTERO, 0, 2));
        assertTrue(Validaciones.esNumeroEnRango(CADENA_NUMERICA_ENTERO, 2));
    }

    @Test
    void testCasoValidoEsNumeroEnRango() {
        assertTrue(Validaciones.esNumeroEnRango(CADENA_NUMERICA_ENTERO, 1, 8));
        assertFalse(Validaciones.esNumeroEnRango(CADENA_NUMERICA_ENTERO, 1));
    }

    @Test
    void testCasoValidoValidarMenuFormato() {
        assertTrue(Validaciones.validarMenuFormato(Menu.UTF_8));
        assertTrue(Validaciones.validarMenuFormato(Menu.UTF_16));
        assertTrue(Validaciones.validarMenuFormato(Menu.UTF_16BE));
        assertTrue(Validaciones.validarMenuFormato(Menu.UTF_16LE));
        assertTrue(Validaciones.validarMenuFormato(Menu.ISO_8859_1));
        assertTrue(Validaciones.validarMenuFormato(Menu.US_ASCII));

    }

    @Test
    void testCasoNoValidoValidarMenuFormato() {
        assertFalse(Validaciones.validarMenuFormato(CADENA_INUTIL));
        assertThrows(NullPointerException.class, () ->Validaciones.validarMenuFormato(null));
    }

    @Test
    void testCasoValidoEsCadenaVacia() {
        assertTrue(Validaciones.esCadenaVacia(CADENA_VACIA));
        assertTrue(Validaciones.esCadenaVacia(CADENA_ESPACIOS_BLANCO));
    }

    @Test
    void testCasoNoValidoEsCadenaVacia(){
        assertFalse(Validaciones.esCadenaVacia(CADENA_INUTIL));
        assertThrows(NullPointerException.class, () ->Validaciones.esCadenaVacia(null));
    }
}