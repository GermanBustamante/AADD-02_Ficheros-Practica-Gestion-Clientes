package Modelo.Entidades;

public class Cliente {
    private String nombre;
    private String apellidos;
    private String DNI;
    private String telefono;
    private String direccion;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Cliente(String nombre, String apellidos, String DNI, String telefono, String direccion) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.DNI = DNI;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "Nombre --> " + nombre.replace(" ", "") +//TODO CAMBIAR NUMERO MÁGICO
                "\t|Apellidos --> " + apellidos.replace(" ", "") +
                "\t|DNI --> " + DNI.replace(" ", "") +
                "\tTelefono --> "+ telefono.replace(" ", "") +
                "\t|Direccion --> " + direccion.replace(" ", "");
    }

    @Override
    public boolean equals(Object o) {
        boolean iguales = false;
        if (this == o){
            iguales= true;
        }else if (o instanceof Cliente && this.DNI.equals(((Cliente) o).DNI)){
            iguales = true;
        }
        return iguales;
    }

}
