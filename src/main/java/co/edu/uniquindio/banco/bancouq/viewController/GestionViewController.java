package co.edu.uniquindio.banco.bancouq.viewController;

import co.edu.uniquindio.banco.bancouq.controller.GestionController;
import co.edu.uniquindio.banco.bancouq.controller.service.IGestionControllerService;
import javafx.fxml.FXML;

public class GestionViewController {
    IGestionControllerService bancoControllerService;

    @FXML
    void initialize() {
        bancoControllerService = new GestionController();
    }
}
