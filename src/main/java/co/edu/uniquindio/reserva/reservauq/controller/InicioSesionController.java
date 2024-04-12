package co.edu.uniquindio.reserva.reservauq.controller;

import co.edu.uniquindio.reserva.reservauq.controller.service.IInicioSesionControllerService;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.EmpleadoDto;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.UsuarioDto;

public class InicioSesionController implements IInicioSesionControllerService {

    ModelFactoryController modelFactoryController;

    public InicioSesionController() {
        modelFactoryController=ModelFactoryController.getInstance();
    }

    @Override
    public boolean inicioSesion(UsuarioDto usuarioDto, EmpleadoDto empleadoDto) {
        return false;
    }
}
