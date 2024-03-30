package co.edu.uniquindio.banco.bancouq.mapping.dto;

import co.edu.uniquindio.banco.bancouq.model.Evento;

public record EmpleadoDto(
        String ID,
        String nombre,
        String correo
        ) {
}