package co.edu.uniquindio.reserva.reservauq.mapping.dto;

import co.edu.uniquindio.reserva.reservauq.model.Usuario;

import java.time.LocalDate;

public record EventoDto (
        String IDEvento,
        String nombreEvento,
        String descripcion,
        LocalDate fecha,
        int capacidadMax
     //   Usuario usuario
){


}
