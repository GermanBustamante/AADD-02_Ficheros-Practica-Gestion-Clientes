package Modelo.FileAccess;

import Modelo.Entidades.Cliente;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileAccessClienteTest {

    private static FileAccessCliente fileAccessCliente;
public static final String RUTA_FICHERO_CLIENTES_PRUEBA = "clientes_prueba.bin";
    @BeforeAll
    static void init() {
        fileAccessCliente = new FileAccessCliente(RUTA_FICHERO_CLIENTES_PRUEBA);
    }

    //Antes de cada método vaciamos el fichero de clientes de prueba
    @BeforeEach
    void beforeMethod() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_FICHERO_CLIENTES_PRUEBA));
        bw.write("");
        bw.close();
    }

    @AfterEach
    void tearDown() {
    }

    //El método agregarIndiceClienteFichero() y getClienteDadoIndice() son complementarios, es decir, se necesitan el uno
    //al otro para testearse, por eso lo pongo en un solo Test
    @Test
    void TestCasoValidoAgregarClienteFicheroYGetClienteDadoIndice() throws IOException {
        Cliente oCliente = new Cliente("Pablo                    ", "Garcia                   ",  "29548877X", "684755514", "Plaza de cuba                 ");
        fileAccessCliente.agregarClienteFichero(oCliente);
        assertEquals(oCliente , fileAccessCliente.getClienteDadoIndice(1));
    }

    //Este test nunca ocurriría ya que no se puede ingresar un valor nulo
    @Test
    void TestCasoNoValidoAgregarClienteFicheroPropiedadNullLanzaExcepcion() {
        Cliente oClienteNombreNull = new Cliente(null, "García", "29548877X", "684755514", "Plaza de cuba");
        assertThrows(NullPointerException.class, () -> fileAccessCliente.agregarClienteFichero(oClienteNombreNull));
    }

    @Test
    void TestCasoNoValidoGetClienteDadoIndice() {

    }

    //Ingresamos una lista de personas al fichero de clientes, le decimos que nos devuelva toda la lista del fichero
    //excepto, el numero 2, simulando que es una persona borrada, devolviendo la lista de personas que no han sido borradas
    @Test
    void TestCasoValidoGetListaClientesNoBorrados() throws IOException {
        List<Cliente> listaClientesCompleta = new ArrayList<>();
        List<Integer> listaIndicesClientesBorrados = new ArrayList<>();
        List<Cliente> listaClientesNoBorrados = new ArrayList<>();
        listaIndicesClientesBorrados.add(2);

        Cliente oCliente = new Cliente("Pablo                    ", "Garcia                   ",  "29548877X", "684755514", "Plaza de cuba                 ");
        Cliente oCliente2Borrado = new Cliente("Sergio                   ", "Garcia                   ",  "27548877X", "684755514", "Plaza de cuba                 ");
        Cliente oCliente3 = new Cliente("Pepito                   ", "Garcia                   ",  "26548877X", "684755514", "Plaza de cuba                 ");

        listaClientesCompleta.add(oCliente);
        listaClientesCompleta.add(oCliente2Borrado);
        listaClientesCompleta.add(oCliente3);

        listaClientesNoBorrados.add(oCliente);
        listaClientesNoBorrados.add(oCliente3);

        fileAccessCliente.agregarClienteFichero(oCliente);
        fileAccessCliente.agregarClienteFichero(oCliente2Borrado);
        fileAccessCliente.agregarClienteFichero(oCliente3);

        List<Cliente> listaClientesRecogida = fileAccessCliente.getListaFicheroClientes(listaIndicesClientesBorrados);
        assertEquals(listaClientesNoBorrados, listaClientesRecogida);
    }

}