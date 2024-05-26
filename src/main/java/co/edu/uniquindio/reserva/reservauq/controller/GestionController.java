package co.edu.uniquindio.reserva.reservauq.controller;

public class GestionController {

    ModelFactoryControllerServidor modelFactoryController;

    public GestionController(){
        System.out.println("Llamando al singleton desde GestionServiceController");
        modelFactoryController = ModelFactoryControllerServidor.getInstance();
    }
}
