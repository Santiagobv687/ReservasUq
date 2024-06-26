package co.edu.uniquindio.reserva.reservauq.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Empleado extends Persona implements Serializable {

    private static final long serialVersionUID=1L;
    private ArrayList<Evento> listaEventos;

    private RolEmpleado rolEmpleado;

    public Empleado(String ID, String nombre, String correo, String contrasenia, RolEmpleado rolEmpleado, ArrayList<Evento> listaEventos) {
        super(ID, nombre, correo, contrasenia);
        this.rolEmpleado=rolEmpleado;
        this.listaEventos.addAll(listaEventos);
    }

    public Empleado() {

    }



    public ArrayList<Evento> getListaEventos() {
        return listaEventos;
    }

    public void setListaEventos(ArrayList<Evento> listaEventos) {
        this.listaEventos = listaEventos;
    }

    public RolEmpleado getRolEmpleado() {
        return rolEmpleado;
    }

    public void setRolEmpleado(RolEmpleado rolEmpleado) {
        this.rolEmpleado = rolEmpleado;
    }
}
