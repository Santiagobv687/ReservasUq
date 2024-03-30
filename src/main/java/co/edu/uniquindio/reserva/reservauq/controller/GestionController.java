package co.edu.uniquindio.reserva.reservauq.controller;

import co.edu.uniquindio.reserva.reservauq.controller.service.IGestionControllerService;

public class GestionController implements IGestionControllerService {

    ModelFactoryController modelFactoryController;

    public GestionController(){
        System.out.println("Llamando al singleton desde GestionServiceController");
        modelFactoryController = ModelFactoryController.getInstance();
    }
}
