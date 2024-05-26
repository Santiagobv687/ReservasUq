package co.edu.uniquindio.reserva.reservauq.controller;

import co.edu.uniquindio.reserva.reservauq.mapping.dto.UsuarioDto;

public class EditarUsuarioController {
    ModelFactoryController modelFactoryController;

    public EditarUsuarioController(){
        modelFactoryController = ModelFactoryController.getInstance();
    }

    public UsuarioDto obtenerUsuarioDto(){
        return modelFactoryController.obtenerUsuarioDto();
    }

    public boolean actualizarUsuario(String IDActual, UsuarioDto usuarioDto) {
        return modelFactoryController.actualizarUsuario(IDActual, usuarioDto);
    }

    public boolean eliminarUsuario(String idActual){
        return modelFactoryController.eliminarUsuario(idActual);
    }

}

