package co.edu.uniquindio.banco.bancouq.model;

import java.time.LocalDate;

public class Reserva {
    private String ID;
    private Usuario usuario;
    private Evento evento;
    private LocalDate fechaSolicitud;
    private EstadoReserva estado;
}
