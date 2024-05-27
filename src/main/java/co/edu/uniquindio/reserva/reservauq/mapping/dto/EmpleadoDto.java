package co.edu.uniquindio.reserva.reservauq.mapping.dto;

import co.edu.uniquindio.reserva.reservauq.model.Empleado;
import co.edu.uniquindio.reserva.reservauq.model.Evento;
import co.edu.uniquindio.reserva.reservauq.model.RolEmpleado;

import java.io.Serializable;
import java.util.ArrayList;

public record EmpleadoDto(
        String ID,
        String nombre,
        String correo,
        String contrasenia,
        RolEmpleado rolEmpleado,
        ArrayList<Evento> listaEventos
        ) implements Serializable {
}