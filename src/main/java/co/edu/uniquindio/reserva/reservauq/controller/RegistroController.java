package co.edu.uniquindio.reserva.reservauq.controller;

import co.edu.uniquindio.reserva.reservauq.controller.service.IRegistroControllerService;
import co.edu.uniquindio.reserva.reservauq.exceptions.CampoVacioException;
import co.edu.uniquindio.reserva.reservauq.exceptions.ContraseñaIncorrectaException;
import co.edu.uniquindio.reserva.reservauq.exceptions.UsuarioExistenteException;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.UsuarioDto;
import co.edu.uniquindio.reserva.reservauq.model.Reserva;

import java.util.ArrayList;

public class RegistroController implements IRegistroControllerService {

    ModelFactoryController modelFactoryController;

    public RegistroController() {
        modelFactoryController = ModelFactoryController.getInstance();
    }
    @Override
    public void registrarUsuario(UsuarioDto usuarioDto) throws UsuarioExistenteException, CampoVacioException, ContraseñaIncorrectaException {
        modelFactoryController.registraUsuario(usuarioDto);
    }
}
