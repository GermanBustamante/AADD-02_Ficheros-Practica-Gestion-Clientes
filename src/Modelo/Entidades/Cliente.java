package Modelo.Entidades;

/**
 * Clase que actuará como entidad cliente de nuestro programa<br/>
 * <b>Propiedades básicas:</b><br/>
 * nombre: tipo String, consultable y NO modificable<br/>
 * apellidos: tipo String, consultable y NO modificable<br/>
 * dni: tipo String, consultable y NO modificable<br/>
 * telefono: tipo String, consultable y NO modificable<br/>
 * direccion: tipo String, consultable y NO modificable<br/>
 * <b>Métodos sobreescritos:</b><br/>
 * String toString ()<br/>
 * boolean equals()
 *
 * @author germanbustamante_
 * @version 1.0
 */
public class Cliente {
    //Atributos
    private String nombre;
    private String apellidos;
    private String dni;
    private String telefono;
    private String direccion;

    //Constantes


    //Getters
    public String getNombre() {
        return nombre;
    }
    public String getApellidos() {
        return apellidos;
    }
    public String getDni() {
        return dni;
    }
    public String getTelefono() {
        return telefono;
    }
    public String getDireccion() {
        return direccion;
    }

    //Constructores
    public Cliente(String nombre, String apellidos, String dni, String telefono, String direccion) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    //Métodos sobreescritos
    @Override
    public String toString() {
        return String.valueOf(new StringBuilder().append("Nombre --> ").append(nombre.replace(" ", "")).append(
                "\t|Apellidos --> ").append(apellidos.replace(" ", "")).append(
                "\t|DNI --> ").append(dni.replace(" ", "")).append(
                "\tTelefono --> ").append(telefono.replace(" ", "")).append(
                "\t|Direccion --> ").append(direccion.replace(" ", "")));
    }

    @Override
    public boolean equals(Object o) {
        boolean iguales = false;
        if (this == o) {
            iguales = true;
        } else if (o instanceof Cliente && this.dni.equals(((Cliente) o).dni)) {
            iguales = true;
        }
        return iguales;
    }

}
