package co.edu.uniquindio.banco.bancouq.controller;

import co.edu.uniquindio.banco.bancouq.controller.service.IGestionControllerService;

public class GestionController implements IGestionControllerService {

    ModelFactoryController modelFactoryController;

    public GestionController(){
        System.out.println("Llamando al singleton desde GestionServiceController");
        modelFactoryController = ModelFactoryController.getInstance();
    }
}
