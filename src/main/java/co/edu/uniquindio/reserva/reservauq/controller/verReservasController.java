package co.edu.uniquindio.reserva.reservauq.controller;

import co.edu.uniquindio.reserva.reservauq.mapping.dto.EventoDto;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.ReservaDto;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.UsuarioDto;

import java.io.IOException;
import java.util.List;

public class verReservasController {
    ModelFactoryController modelFactoryController;

    public UsuarioDto obtenerUsuarioDto(){
        return modelFactoryController.obtenerUsuarioDto();
    }

    public verReservasController(){
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

    public boolean agregarReserva(ReservaDto reservaDto) throws IOException {
        boolean sePuedeAgregar=modelFactoryController.agregarReserva(reservaDto);
        if(sePuedeAgregar)
        {
            modelFactoryController.producirObjeto(modelFactoryController.FILAAGREGARRESERVA, reservaDto);
        }
        return sePuedeAgregar;
    }

    public boolean actualizarReserva(String idActual, ReservaDto reservaDto) throws IOException {
        boolean sePuedeActualizar=modelFactoryController.actualizarReserva(idActual, reservaDto);
        if(sePuedeActualizar)
        {
            modelFactoryController.producirObjeto(modelFactoryController.FILAACTUALIZARRESERVA, reservaDto);
        }
        return sePuedeActualizar;
    }

    public boolean eliminarReserva(String IDReserva) {
        return modelFactoryController.eliminarReserva(IDReserva);
    }
}
