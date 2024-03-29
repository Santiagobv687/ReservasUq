package co.edu.uniquindio.banco.bancouq.controller;

public class UsuarioController {
    ModelFactoryController modelFactoryController;

    public UsuarioController(){
        System.out.println("Llamando al singleton desde EmpleadoServiceController");
        modelFactoryController = ModelFactoryController.getInstance();
    }
}
