package co.edu.uniquindio.reserva.reservauq.model;

import java.util.ArrayList;

public class Usuario extends Persona{
    private String contrasenia;
    private ArrayList<Reserva> listaReservas;

    public Usuario(String ID, String nombre, String correo, String contrasenia, ArrayList<Reserva> listaReservas) {
        super(ID, nombre, correo);
        this.contrasenia=contrasenia;
        this.listaReservas.addAll(listaReservas);
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public ArrayList<Reserva> getListaReservas() {
        return listaReservas;
    }

    public void setListaReservas(ArrayList<Reserva> listaReservas) {
        this.listaReservas = listaReservas;
    }
}
