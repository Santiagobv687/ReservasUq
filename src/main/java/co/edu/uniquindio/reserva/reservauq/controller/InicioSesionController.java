package co.edu.uniquindio.reserva.reservauq.controller;

import co.edu.uniquindio.reserva.reservauq.controller.service.IInicioSesionControllerService;
import co.edu.uniquindio.reserva.reservauq.exceptions.CampoVacioException;
import co.edu.uniquindio.reserva.reservauq.exceptions.ContraseñaIncorrectaException;
import co.edu.uniquindio.reserva.reservauq.exceptions.UsuarioNoRegistradoException;

public class InicioSesionController implements IInicioSesionControllerService {

    ModelFactoryController modelFactoryController;

    public InicioSesionController() {
        modelFactoryController=ModelFactoryController.getInstance();
    }

    @Override
    public Object inicioSesion(String ID, String contrasenia) throws UsuarioNoRegistradoException, CampoVacioException, ContraseñaIncorrectaException {
        return modelFactoryController.iniciarSesion(ID,contrasenia);
    }
}
