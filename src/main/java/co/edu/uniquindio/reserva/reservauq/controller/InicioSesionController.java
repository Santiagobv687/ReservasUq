package co.edu.uniquindio.reserva.reservauq.controller;

import co.edu.uniquindio.reserva.reservauq.controller.service.IInicioSesionControllerService;
import co.edu.uniquindio.reserva.reservauq.exceptions.CampoVacioException;
import co.edu.uniquindio.reserva.reservauq.exceptions.ContraseniaIncorrectaException;
import co.edu.uniquindio.reserva.reservauq.exceptions.UsuarioNoRegistradoException;

public class InicioSesionController implements IInicioSesionControllerService {

    ModelFactoryControllerServidor modelFactoryController;

    public InicioSesionController() {
        modelFactoryController= ModelFactoryControllerServidor.getInstance();
    }

    @Override
    public Object inicioSesion(String ID, String contrasenia) throws UsuarioNoRegistradoException, CampoVacioException, ContraseniaIncorrectaException {
        return modelFactoryController.iniciarSesion(ID,contrasenia);
    }
}
