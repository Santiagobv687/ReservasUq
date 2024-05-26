package co.edu.uniquindio.reserva.reservauq.controller;

public class GestionController {

    ModelFactoryController modelFactoryController;

    public GestionController(){
        System.out.println("Llamando al singleton desde GestionServiceController");
        modelFactoryController = ModelFactoryController.getInstance();
    }
}
