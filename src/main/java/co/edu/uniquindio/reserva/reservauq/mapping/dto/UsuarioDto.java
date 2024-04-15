package co.edu.uniquindio.reserva.reservauq.mapping.dto;

import co.edu.uniquindio.reserva.reservauq.model.Reserva;

import java.util.ArrayList;

public record UsuarioDto(
        String ID,
        String nombre,

        String correo,

        String contrasenia,

        ArrayList<Reserva> listaReservas

) {

}
