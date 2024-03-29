package co.edu.uniquindio.banco.bancouq.controller;

import co.edu.uniquindio.banco.bancouq.controller.service.IBancoControllerService;

public class GestionController implements IBancoControllerService {

    ModelFactoryController modelFactoryController;

    public GestionController(){
        System.out.println("Llamando al singleton desde BancoServiceController");
        modelFactoryController = ModelFactoryController.getInstance();
    }
}
