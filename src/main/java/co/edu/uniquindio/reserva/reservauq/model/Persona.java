package co.edu.uniquindio.reserva.reservauq.model;

public class Persona {
    private String ID;
    private String nombre;
    private String correo;
    private String contrasenia;

    public Persona() {

    }

    public Persona(String ID, String nombre, String correo,String contrasenia) {
        this.ID = ID;
        this.nombre=nombre;
        this.correo=correo;
        this.contrasenia=contrasenia;
    }



    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
}
