package co.edu.uniquindio.reserva.reservauq.model;

import java.util.ArrayList;

public class Usuario extends Persona{
    private ArrayList<Reserva> listaReservas;

    public Usuario(String ID, String nombre, String correo, String contrasenia, ArrayList<Reserva> listaReservas) {
        super(ID, nombre, correo,contrasenia);
        this.listaReservas=listaReservas;
    }

    public ArrayList<Reserva> getListaReservas() {
        return listaReservas;
    }

    public void setListaReservas(ArrayList<Reserva> listaReservas) {
        this.listaReservas = listaReservas;


    }
}
