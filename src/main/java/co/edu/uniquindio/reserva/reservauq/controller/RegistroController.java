package co.edu.uniquindio.reserva.reservauq.controller;

import co.edu.uniquindio.reserva.reservauq.controller.service.IRegistroControllerService;
import co.edu.uniquindio.reserva.reservauq.exceptions.CampoVacioException;
import co.edu.uniquindio.reserva.reservauq.exceptions.ContraseniaIncorrectaException;
import co.edu.uniquindio.reserva.reservauq.exceptions.UsuarioExistenteException;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.UsuarioDto;

public class RegistroController implements IRegistroControllerService {

    ModelFactoryController modelFactoryController;

    public RegistroController() {
        modelFactoryController = ModelFactoryController.getInstance();
    }
    @Override
    public void registrarUsuario(UsuarioDto usuarioDto) throws UsuarioExistenteException, CampoVacioException, ContraseniaIncorrectaException {
        modelFactoryController.registraUsuario(usuarioDto);
    }
}
