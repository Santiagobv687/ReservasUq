package co.edu.uniquindio.reserva.reservauq.controller;

public class UsuarioController {
    ModelFactoryController modelFactoryController;

    public UsuarioController(){
        System.out.println("Llamando al singleton desde EmpleadoServiceController");
        modelFactoryController = ModelFactoryController.getInstance();
    }
}
