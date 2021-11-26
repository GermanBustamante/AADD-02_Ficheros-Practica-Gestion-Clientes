package Modelo.FileAccess;

import Modelo.Entidades.Cliente;
import Modelo.Utilidades.Utilidades;
import Vista.Menu;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileAccessExportacionClientesTest {
    private static FileAccessExportacionClientes fileAccessExportacionClientes;
    public static final String RUTA_FICHERO_CLIENTES_TXT_PRUEBA = ".//Ficheros//clientes_prueba.txt";
    public static final String CADENA_CHORRA = "MOMO";

    @BeforeAll
    static void instanciarFileAccess() {
        fileAccessExportacionClientes = new FileAccessExportacionClientes(RUTA_FICHERO_CLIENTES_TXT_PRUEBA);
    }

    @AfterAll
    static void borrarFicheroPrueba(){
        fileAccessExportacionClientes.getFichero().delete();
    }
    //Antes de cada método vaciamos el fichero de clientes de prueba
    @BeforeEach
    void vaciarFicheroClientesPrueba() {
        Utilidades.vaciarFichero(fileAccessExportacionClientes.getFichero());
    }

    //Le metemos varias personas al fichero, escribimos en el fichero
    @Test
    void TestCasoValidoescribirClientesFicheroTexto() {
        Cliente oCliente = new Cliente("Pablo                    ", "Garcia                   ", "29548877X", "684755514", "Plaza de cuba                 ");
        Cliente oCliente2 = new Cliente("Sergio                   ", "Garcia                   ", "27548877X", "684755514", "Plaza de cuba                 ");
        String cadenaPersona;
        List<Cliente> listaClientes = List.of(oCliente, oCliente2);
        int contador = 0;
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(fileAccessExportacionClientes.getFichero()))){
            FileAccessCliente fileAccessCliente = new FileAccessCliente(FileAccessClienteTest.RUTA_FICHERO_CLIENTES_PRUEBA);
            fileAccessCliente.escribirClienteFicheroBinario(oCliente);
            fileAccessCliente.escribirClienteFicheroBinario(oCliente2);
            fileAccessExportacionClientes.escribirClientesFicheroTexto(listaClientes, Menu.UTF_8);
            while ((cadenaPersona = bufferedReader.readLine()) !=null){
                assertEquals(cadenaPersona, listaClientes.get(contador).toString());
                contador++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Lanza excepciones debido a darle un formato incorrecta en el que escribir, o darle a alguno de los parámetros nulo
    @Test
    void TestCasoNoValidoEscribirClientesFicheroTextoListaClientesNulaOFormatoFicheroIncorrecto() {
        List<Cliente> listaClientesCompleta = new ArrayList<>();
        Cliente oCliente = new Cliente("Pablo                    ", "Garcia                   ", "29548877X", "684755514", "Plaza de cuba                 ");
        Cliente oCliente2Borrado = new Cliente("Sergio                   ", "Garcia                   ", "27548877X", "684755514", "Plaza de cuba                 ");
        listaClientesCompleta.add(oCliente);
        listaClientesCompleta.add(oCliente2Borrado);
        assertThrows(UnsupportedEncodingException.class, () -> fileAccessExportacionClientes.escribirClientesFicheroTexto(listaClientesCompleta, CADENA_CHORRA));
        assertThrows(NullPointerException.class, () -> fileAccessExportacionClientes.escribirClientesFicheroTexto(listaClientesCompleta, null));
        assertThrows(NullPointerException.class, () -> fileAccessExportacionClientes.escribirClientesFicheroTexto(null, Menu.UTF_8));
    }
}