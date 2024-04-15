package co.edu.uniquindio.reserva.reservauq.model;

import java.time.LocalDate;

public class Reserva {
    private String IDReserva;
    private Usuario usuario;
    private Evento evento;
    private LocalDate fechaSolicitud;
    private EstadoReserva estado;

    public String getIDReserva() {
        return IDReserva;
    }

    public void setIDReserva(String IDReserva) {
        this.IDReserva = IDReserva;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public LocalDate getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDate fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public EstadoReserva getEstado() {
        return estado;
    }

    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }
}
