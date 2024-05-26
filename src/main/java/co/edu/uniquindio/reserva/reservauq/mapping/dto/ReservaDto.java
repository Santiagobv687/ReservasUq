package co.edu.uniquindio.reserva.reservauq.mapping.dto;

import co.edu.uniquindio.reserva.reservauq.model.EstadoReserva;
import co.edu.uniquindio.reserva.reservauq.model.Evento;
import co.edu.uniquindio.reserva.reservauq.model.Usuario;

import java.time.LocalDate;

public record ReservaDto(
        String IDReserva,
        UsuarioDto usuarioReserva,
        EventoDto eventoReserva,
        LocalDate fechaSolicitud,
        EstadoReserva estado) {
}
