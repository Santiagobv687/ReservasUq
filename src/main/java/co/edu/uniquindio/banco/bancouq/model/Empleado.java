package co.edu.uniquindio.banco.bancouq.model;

import java.util.ArrayList;

public class Empleado extends Persona {
    private ArrayList<Evento> listaEventos;

    public Empleado(String ID, String nombre, String correo, ArrayList<Evento> listaEventos) {
        super(ID, nombre, correo);
        this.listaEventos.addAll(listaEventos);
    }

    public Empleado() {
        super();
    }

    public ArrayList<Evento> getListaEventos() {
        return listaEventos;
    }

    public void setListaEventos(ArrayList<Evento> listaEventos) {
        this.listaEventos = listaEventos;
    }
}
