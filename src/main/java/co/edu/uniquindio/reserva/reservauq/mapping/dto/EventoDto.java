package co.edu.uniquindio.reserva.reservauq.mapping.dto;

import co.edu.uniquindio.reserva.reservauq.model.Empleado;
import co.edu.uniquindio.reserva.reservauq.model.Reserva;
import co.edu.uniquindio.reserva.reservauq.model.Usuario;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public record EventoDto (
        String IDEvento,
        String nombreEvento,
        String descripcion,
        LocalDate fecha,
        int capacidadMax,
        EmpleadoDto empleado,
        ArrayList<Reserva> listaReservas

) implements Serializable {


}
