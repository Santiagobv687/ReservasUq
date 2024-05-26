package co.edu.uniquindio.reserva.reservauq.controller;

import co.edu.uniquindio.reserva.reservauq.controller.service.IUsuarioControllerService;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.UsuarioDto;

import java.util.List;

public class UsuarioController implements IUsuarioControllerService {
    ModelFactoryController modelFactoryController;

    public UsuarioController(){
        modelFactoryController = ModelFactoryController.getInstance();
    }

    @Override
    public List<UsuarioDto> obtenerUsuarios() {
        return modelFactoryController.obtenerUsuario();
    }

    @Override
    public boolean agregarUsuario(UsuarioDto usuarioDto) {
        return modelFactoryController.agregarUsuario(usuarioDto);
    }

    @Override
    public boolean eliminarUsuario(String ID) {
        return modelFactoryController.eliminarUsuario(ID);
    }

    @Override
    public boolean actualizarUsuario(String IDActual, UsuarioDto usuarioDto) {
        return modelFactoryController.actualizarUsuario(IDActual, usuarioDto);
    }}
