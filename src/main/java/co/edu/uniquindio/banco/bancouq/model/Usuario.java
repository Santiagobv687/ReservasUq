package co.edu.uniquindio.banco.bancouq.model;

import java.util.ArrayList;

public class Usuario extends Persona{
    private String contrasenia;
    private ArrayList<Reserva> listaReservas;

    public Usuario(String ID, String nombre, String correo, String contrasenia, ArrayList<Reserva> listaReservas) {
        super(ID, nombre, correo);
        this.contrasenia=contrasenia;
        this.listaReservas.addAll(listaReservas);
    }
}
