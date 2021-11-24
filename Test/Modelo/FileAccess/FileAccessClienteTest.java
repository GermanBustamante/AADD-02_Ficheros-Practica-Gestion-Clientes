package Modelo.FileAccess;

import Modelo.Entidades.Cliente;
import Modelo.Utilidades.Utilidades;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileAccessClienteTest {
    private static FileAccessCliente fileAccessCliente;
    public static final String RUTA_FICHERO_CLIENTES_PRUEBA = "clientes_prueba.bin";

    @BeforeAll
    static void instanciarFileAccess() {
        fileAccessCliente = new FileAccessCliente(RUTA_FICHERO_CLIENTES_PRUEBA);
    }

    //Antes de cada método vaciamos el fichero de clientes de prueba
    @BeforeEach
    void vaciarFicheroClientesPrueba() {
        Utilidades.vaciarFichero(fileAccessCliente.getFichero());
    }

    //NOTA: El método agregarIndiceClienteFichero() y getClienteDadoIndice() son complementarios, es decir, se necesitan el uno
    //al otro para testearse, por eso lo pongo en un solo Test
    @Test
    void TestCasoValidoAgregarClienteFicheroYGetClienteDadoIndice() {
        Cliente oCliente = new Cliente("Pablo                    ", "Garcia                   ", "29548877X", "684755514", "Plaza de cuba                 ");
        try {
            fileAccessCliente.escribirClienteFicheroBinario(oCliente);
            assertEquals(oCliente, fileAccessCliente.getClienteFicheroBinario(1));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Ingresamos una lista de personas al fichero de clientes de prueba, le pedimos que nos devuelva toda la lista del fichero
    //excepto, el numero 2, simulando que es una persona borrada, devolviendo la lista de personas que no han sido borradas
    //Demostraremos que el método no funciona, ya que no ha devuelto la 2º persona, que se ha simulado que ha sido eliminada
    @Test
    void TestCasoValidoGetListaClientesNoBorradosFicheroClientes() throws IOException {
        List<Cliente> listaClientesCompleta = new ArrayList<>();
        List<Integer> listaIndicesClientesBorrados = new ArrayList<>();
        List<Cliente> listaClientesNoBorrados = new ArrayList<>();
        listaIndicesClientesBorrados.add(2);
        Cliente oCliente = new Cliente("Pablo                    ", "Garcia                   ", "29548877X", "684755514", "Plaza de cuba                 ");
        Cliente oCliente2Borrado = new Cliente("Sergio                   ", "Garcia                   ", "27548877X", "684755514", "Plaza de cuba                 ");
        listaClientesCompleta.add(oCliente);
        listaClientesCompleta.add(oCliente2Borrado);
        listaClientesNoBorrados.add(oCliente);
        fileAccessCliente.escribirClienteFicheroBinario(oCliente);
        fileAccessCliente.escribirClienteFicheroBinario(oCliente2Borrado);
        List<Cliente> listaClientesRecogida = fileAccessCliente.getListaClientesNoBorradosFicheroBinario(listaIndicesClientesBorrados);
        assertEquals(listaClientesNoBorrados, listaClientesRecogida);
        assertNotEquals(listaClientesCompleta, listaClientesRecogida);
    }

    //Esto ocurriría cuando le pasaramos a getListaClientesNoBorradosFicheroClientes una lista Integer nula
    @Test
    void TestCasoValidoGetListaClientesNoBorradosFicheroClientesListaIndicesNula(){
assertThrows(NullPointerException.class, () -> fileAccessCliente.getListaClientesNoBorradosFicheroBinario(null));
    }

    //NOTA: Este test nunca ocurriría ya que no se puede ingresar un valor nulo, lo controla el menú
    @Test
    void TestCasoNoValidoAgregarClienteFicheroPropiedadNullLanzaExcepcion() {
        Cliente oClienteNombreNull = new Cliente(null, "García", "29548877X", "684755514", "Plaza de cuba");
        assertThrows(NullPointerException.class, () -> fileAccessCliente.escribirClienteFicheroBinario(oClienteNombreNull));
    }

    //Ocurriría cuando le pasase por parámetro un número incorrecto, esto no ocurrirá, ya que el método que da
    //ese valor es correcta, pero así podremos ver como lanza la excepción
    @Test
    void TestCasoNoValidoGetClienteDadoIndiceNumeroIncorrecto() {
        assertThrows(IOException.class, () -> fileAccessCliente.getClienteFicheroBinario(-1));
    }
}