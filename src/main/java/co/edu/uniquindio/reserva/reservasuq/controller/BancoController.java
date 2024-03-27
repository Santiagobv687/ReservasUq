package co.edu.uniquindio.reserva.reservasuq.controller;

import co.edu.uniquindio.reserva.reservasuq.controller.service.IBancoControllerService;

public class BancoController implements IBancoControllerService {

    ModelFactoryController modelFactoryController;

    public BancoController(){
        System.out.println("Llamando al singleton desde BancoServiceController");
        modelFactoryController = ModelFactoryController.getInstance();
    }
}
