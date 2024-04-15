package co.edu.uniquindio.reserva.reservauq.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Evento implements Serializable {

    private static final long serialVersionUID=1L;
    private String IDEvento;
    private String nombreEvento;
    private String descripcion;
    private LocalDate fecha;
    private int capacidadMax;
    private Empleado empleadoEncargado;
    private ArrayList<Reserva> listaReservas;

    public Evento(){

    }

    public String getIDEvento() {
        return IDEvento;
    }

    public void setIDEvento(String IDEvento) {
        this.IDEvento = IDEvento;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getCapacidadMax() {
        return capacidadMax;
    }

    public void setCapacidadMax(int capacidadMax) {
        this.capacidadMax = capacidadMax;
    }

    public Empleado getEmpleadoEncargado() {
        return empleadoEncargado;
    }

    public void setEmpleadoEncargado(Empleado empleadoEncargado) {
        this.empleadoEncargado = empleadoEncargado;
    }

    public ArrayList<Reserva> getListaReservas() {
        return listaReservas;
    }

    public void setListaReservas(ArrayList<Reserva> listaReservas) {
        this.listaReservas = listaReservas;
    }
}
