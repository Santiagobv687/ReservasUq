package co.edu.uniquindio.reserva.reservauq.controller;

import co.edu.uniquindio.reserva.reservauq.mapping.dto.UsuarioDto;

public class EditarUsuarioController {

    UsuarioDto usuarioActivo;
    ModelFactoryController modelFactoryController;

    public EditarUsuarioController(){
        modelFactoryController = ModelFactoryController.getInstance();
    }

}
