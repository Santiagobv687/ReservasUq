package co.edu.uniquindio.reserva.reservauq.controller;

import co.edu.uniquindio.reserva.reservauq.controller.service.IEmpleadoControllerService;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.EmpleadoDto;

import java.util.List;

public class EmpleadoController implements IEmpleadoControllerService {
    ModelFactoryControllerServidor modelFactoryController;

    public EmpleadoController(){
        modelFactoryController = ModelFactoryControllerServidor.getInstance();
    }

    public List<EmpleadoDto> obtenerEmpleados() {
        return modelFactoryController.obtenerEmpleados();
    }

    @Override
    public boolean agregarEmpleado(EmpleadoDto empleadoDto) {
        return modelFactoryController.agregarEmpleado(empleadoDto);
    }

    @Override
    public boolean eliminarEmpleado(String ID) {
        return modelFactoryController.eliminarEmpleado(ID);
    }

    @Override
    public boolean actualizarEmpleado(String IDActual, EmpleadoDto empleadoDto) {
        return modelFactoryController.actualizarEmpleado(IDActual, empleadoDto);
    }
}
