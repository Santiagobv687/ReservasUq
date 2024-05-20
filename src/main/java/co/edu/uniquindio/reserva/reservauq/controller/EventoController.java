package co.edu.uniquindio.reserva.reservauq.controller;

import co.edu.uniquindio.reserva.reservauq.mapping.dto.EventoDto;

import java.util.List;

public class EventoController {

    ModelFactoryController modelFactoryController;

    public EventoController(){
        modelFactoryController=ModelFactoryController.getInstance();
    }

    public List<EventoDto> obtenerEventos() {
        return modelFactoryController.obtenerEventos();
    }

    public boolean agregarEvento(EventoDto eventoDto) {
        return modelFactoryController.agregarEvento(eventoDto);
    }
}
