package co.edu.uniquindio.reserva.reservauq.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Evento {
    private String ID;
    private String nombre;
    private String descripcion;
    private LocalDate fecha;
    private int capacidadMax;
    private Empleado empleadoEncargado;
    private ArrayList<Reserva> listaReservas;

}
