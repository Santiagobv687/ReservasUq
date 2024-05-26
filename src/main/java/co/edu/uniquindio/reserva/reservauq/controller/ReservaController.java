package co.edu.uniquindio.reserva.reservauq.controller;

import co.edu.uniquindio.reserva.reservauq.mapping.dto.EventoDto;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.ReservaDto;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.UsuarioDto;

import java.util.List;

public class ReservaController {

    ModelFactoryController modelFactoryController;

    public ReservaController(){
        modelFactoryController=ModelFactoryController.getInstance();
    }

    public List<ReservaDto> obtenerReservas() {
        return modelFactoryController.obtenerReservas();
    }

    public List<UsuarioDto> obtenerUsuarios(){
        return modelFactoryController.obtenerUsuario();
    }

    public List<EventoDto> obtenerEventos(){
        return modelFactoryController.obtenerEventos();
    }

    public boolean agregarReserva(ReservaDto reservaDto) {
        //modelFactoryController.producirMensaje("Fila de reservas",);
        return modelFactoryController.agregarReserva(reservaDto);
    }

    public boolean actualizarReserva(String idActual, ReservaDto reservaDto) {
        return modelFactoryController.actualizarReserva(idActual, reservaDto);
    }

    public boolean eliminarReserva(String IDReserva) {
        return modelFactoryController.eliminarReserva(IDReserva);
    }
}
