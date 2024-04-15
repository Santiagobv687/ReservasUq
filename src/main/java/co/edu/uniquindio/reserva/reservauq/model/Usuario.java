package co.edu.uniquindio.reserva.reservauq.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Usuario extends Persona implements Serializable {
    private static final long serialVersionUID=1L;
    private ArrayList<Reserva> listaReservas;

    public Usuario(String ID, String nombre, String correo, String contrasenia, ArrayList<Reserva> listaReservas) {
        super(ID, nombre, correo,contrasenia);
        this.listaReservas=listaReservas;
    }

    public Usuario(){

    }

    public ArrayList<Reserva> getListaReservas() {
        return listaReservas;
    }

    public void setListaReservas(ArrayList<Reserva> listaReservas) {
        this.listaReservas = listaReservas;


    }
}
