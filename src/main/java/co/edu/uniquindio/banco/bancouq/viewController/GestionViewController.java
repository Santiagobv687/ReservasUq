package co.edu.uniquindio.banco.bancouq.viewController;

import co.edu.uniquindio.banco.bancouq.controller.GestionController;
import co.edu.uniquindio.banco.bancouq.controller.service.IBancoControllerService;
import javafx.fxml.FXML;

public class GestionViewController {
    IBancoControllerService bancoControllerService;

    @FXML
    void initialize() {
        bancoControllerService = new GestionController();
    }
}
