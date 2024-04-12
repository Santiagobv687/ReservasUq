package co.edu.uniquindio.reserva.reservauq.controller;

import co.edu.uniquindio.reserva.reservauq.mapping.dto.UsuarioDto;

import java.util.List;

public class UsuarioController {
    ModelFactoryController modelFactoryController;

    public UsuarioController(){
        System.out.println("Llamando al singleton desde EmpleadoServiceController");
        modelFactoryController = ModelFactoryController.getInstance();
    }

    public List<UsuarioDto> obtenerUsuario() {
        return modelFactoryController.obtenerUsuario();
    }
}
