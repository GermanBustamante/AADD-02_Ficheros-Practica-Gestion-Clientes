package Vista;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidacionesTest {

    public static final String CADENA_INUTIL = "Soy una cadena inutil";
    public static final String CADENA_VACIA = "";
    public static final String CADENA_ESPACIOS_BLANCO= "     ";

    @Test
    void testCasoValidoEsEntero() {
        assertFalse(Validaciones.esEntero("X"));
        assertThrows(NullPointerException.class, () -> Validaciones.esEntero(null));
        assertFalse(Validaciones.esEntero("3.2"));
    }

    @Test
    void testCasoNoValidoEsEntero() {
        assertTrue(Validaciones.esEntero("3"));
    }

    @Test
    void testCasoValidoEsNumeroEnRango() {
        assertFalse(Validaciones.esNumeroEnRango("X", 0, 0));
        assertThrows(NullPointerException.class, () ->Validaciones.esNumeroEnRango(null, 0, 0));
        assertFalse(Validaciones.esNumeroEnRango("3", 0, 2));
        assertTrue(Validaciones.esNumeroEnRango("3", 2));
    }

    @Test
    void testEsNumeroEnRango() {
        assertTrue(Validaciones.esNumeroEnRango("3", 1, 8));
        assertFalse(Validaciones.esNumeroEnRango("3", 1));
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