package co.edu.uniquindio.reserva.reservauq.controller;

import co.edu.uniquindio.reserva.reservauq.mapping.dto.EventoDto;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.ReservaDto;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.UsuarioDto;
import co.edu.uniquindio.reserva.reservauq.model.Reserva;

import java.io.IOException;
import java.util.ArrayList;
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

    public boolean agregarReserva(ReservaDto reservaDto) throws IOException {
        boolean sePuedeAgregar=modelFactoryController.agregarReserva(reservaDto);
        if(sePuedeAgregar)
        {
            modelFactoryController.producirObjeto(modelFactoryController.FILA, reservaDto);
        }
        return sePuedeAgregar;
    }

    public boolean actualizarReserva(String idActual, ReservaDto reservaDto) throws IOException {
        boolean sePuedeActualizar=modelFactoryController.actualizarReserva(idActual, reservaDto);;
        if(sePuedeActualizar)
        {
            modelFactoryController.producirObjeto(modelFactoryController.FILA, reservaDto);
        }
        return sePuedeActualizar;
    }

    public boolean eliminarReserva(String IDReserva) {
        return modelFactoryController.eliminarReserva(IDReserva);
    }

    public ArrayList<ReservaDto> actualizarMensajesReservas() {
        modelFactoryController.consumirMensajesServicio1();
        return modelFactoryController.reservas;
    }

}
