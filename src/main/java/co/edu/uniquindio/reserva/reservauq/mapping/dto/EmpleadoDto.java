package co.edu.uniquindio.reserva.reservauq.mapping.dto;

import co.edu.uniquindio.reserva.reservauq.model.Evento;

import java.util.ArrayList;

public record EmpleadoDto(
        String ID,
        String nombre,
        String correo
        ) {
}