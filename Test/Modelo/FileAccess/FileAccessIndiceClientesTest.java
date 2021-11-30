package Modelo.FileAccess;

import Modelo.Utilidades.Utilidades;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.EOFException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileAccessIndiceClientesTest {

    private static FileAccessIndiceClientes fileAccessIndiceClientes;
    public static final String RUTA_FICHERO_INDICE_CLIENTES_PRUEBA = ".//Ficheros//indices_clientes_prueba.bin";
    public static final String DNI_PRUEBA = "29565540Y";

    @BeforeAll
    static void instanciarFileAccess() {
        fileAccessIndiceClientes = new FileAccessIndiceClientes(RUTA_FICHERO_INDICE_CLIENTES_PRUEBA);
    }

    @AfterAll
    static void borrarFicheroPrueba(){
        fileAccessIndiceClientes.getFichero().delete();
    }

    //Antes de cada m�todo vaciamos el fichero de clientes de prueba
    @BeforeEach
    void vaciarFicheroClientesPrueba() {
        Utilidades.vaciarFichero(fileAccessIndiceClientes.getFichero());
    }

    //NOTA: estos 2 m�todos se necesitan para testear que funcionan correctamente, por eso los meto
    //en el mismo test
    @Test
    void TestCasoValidoEscribirIndiceYDNIFicheroIndiceClientesYGetPosicionClienteFicheroIndices() {
        try {
            fileAccessIndiceClientes.escribirIndiceYDNIFicheroBinario(DNI_PRUEBA);
            assertEquals(0, fileAccessIndiceClientes.getPosicionClienteFicheroBinario(DNI_PRUEBA));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Al no encontrar un DNI, lanza una EOFException, el programa principal se encarga de gestionar esa excepci�n
    @Test
    void TestCasoNoValidoGetPosicionClienteFicheroIndicesDNINoEncontrado() {
        assertThrows(EOFException.class, () -> fileAccessIndiceClientes.getPosicionClienteFicheroBinario("2871146" +
                "N"));
    }

    //Agregar� un DNI de un cliente, luego lo borrar� y al buscarlo lanzar� EOFException ya que no se encuentra,
    //NOTA: Este test es una ampliaci�n del test anterior, ya que demuestra que se ha borrado lanzado EOFException al buscarlo
    @Test
    void TestCasoValidoBorrarClienteFicheroIndices() {
        TestCasoValidoEscribirIndiceYDNIFicheroIndiceClientesYGetPosicionClienteFicheroIndices();
        try {
            fileAccessIndiceClientes.borrarClienteFicheroBinario(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertThrows(EOFException.class, () -> fileAccessIndiceClientes.getPosicionClienteFicheroBinario(DNI_PRUEBA));
    }

    //A�adimos una persona, luego, la borramos y veremos que el m�todo getListaIndicesClientesBorradosFicheroIndiceClientes()
    //nos devolver� 1 simulando el cliente borrado
    @Test
    void TestCasoValidoGetListaIndicesClientesBorradosFicheroIndiceClientes(){
        TestCasoValidoBorrarClienteFicheroIndices();
        List<Integer> listaIndicesClientesBorrados = List.of(1);
        try {
            assertEquals(listaIndicesClientesBorrados, fileAccessIndiceClientes.getListaIndicesClientesBorradosFicheroBinario());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Lanza EOFExcepcion cuando el fichero de indice de clientes est� vac�o
    @Test
    void TestCasoNoValidoGetListaIndicesClientesBorradosFicheroIndiceClientesFicheroVacio(){
        assertThrows(EOFException.class, () -> fileAccessIndiceClientes.getPosicionClienteFicheroBinario(DNI_PRUEBA));
    }
}