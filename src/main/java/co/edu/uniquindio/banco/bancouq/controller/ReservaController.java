package co.edu.uniquindio.banco.bancouq.controller;

public class ReservaController {
    ModelFactoryController modelFactoryController;

    public ReservaController(){
        System.out.println("Llamando al singleton desde EmpleadoServiceController");
        modelFactoryController = ModelFactoryController.getInstance();
    }
}
